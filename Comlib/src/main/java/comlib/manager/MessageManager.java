package comlib.manager;


import comlib.provider.MessageProvider;
import comlib.provider.DummyMessageProvider;
import comlib.event.MessageEvent;
import comlib.message.MessageID;
import comlib.message.CommunicationMessage;
import comlib.message.MessageID;
import comlib.provider.DummyMessageProvider;
import comlib.provider.MessageProvider;
import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;
import rescuecore2.Constants;
import rescuecore2.config.Config;
import rescuecore2.messages.Command;
import rescuecore2.messages.Message;
import rescuecore2.standard.kernel.comms.ChannelCommunicationModel;
import rescuecore2.standard.messages.AKSpeak;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MessageManager {

	private boolean developerMode;

	private RadioConfig radioConfig;
	private VoiceConfig voiceConfig;

	private boolean useRadio;

	private int kernelTime;

	private MessageProvider[] providerList;
	private List<MessageEvent> eventList;

	private List<CommunicationMessage> receivedMessages; // For compatible
	private List<CommunicationMessage> sendMessages;

	public MessageManager(Config config) {
		this.init(config);
	}

	private void init(Config config) {
		this.developerMode = config.getBooleanValue("comlib.develop.developerMode", false);
		this.radioConfig = new RadioConfig(config);
		this.voiceConfig = new VoiceConfig(config);
		this.useRadio = this.searchRadio(config);
		this.kernelTime = -1;
		this.providerList = new MessageProvider[config.getIntValue("comlib.default.messageID", 16)];
		this.eventList = new ArrayList<>();
		this.receivedMessages = new ArrayList<>();
		this.sendMessages = new ArrayList<>();

		this.initLoadProvider();
	}

	private boolean searchRadio(Config config) {
		boolean speakComm = config.getValue(Constants.COMMUNICATION_MODEL_KEY).equals(ChannelCommunicationModel.class.getName());
		int numChannels = config.getIntValue("comms.channels.count");
		return speakComm && (numChannels > 1);
	}

	public boolean canUseRadio() {
		return this.useRadio;
	}

	public RadioConfig getRadioConfig() {
		return this.radioConfig;
	}

	public VoiceConfig getVoiceConfig() {
		return this.voiceConfig;
	}

	public int getTime() {
		return this.kernelTime;
	}

	public void receiveMessage(int time, Collection<Command> heard) {
		this.kernelTime = time;
		this.receivedMessages.clear();

		for (Command command : heard)
		{
			if (command instanceof AKSpeak)
			{
				byte[] data = ((AKSpeak)command).getContent();
				String voice = new String(data);
				if ("Help".equalsIgnoreCase(voice) || "Ouch".equalsIgnoreCase(voice))
				{ continue; }

				String[] voiceData = voice.split(this.voiceConfig.getMessageSeparator());
				if (this.voiceConfig.getKeyword().equals(voiceData[0]))
				{ this.receiveVoiceMessage(Arrays.copyOfRange(voiceData, 1, voiceData.length - 1), this.receivedMessages); }
				else
				{ this.receiveRadioMessage(data, this.receivedMessages); }
			}
		}
	}

	private void receiveRadioMessage(byte[] data, List<CommunicationMessage> list) {
		// TODO:ノイズ対策をするべき
		if (data == null || list == null)
		{ return; }
		BitStreamReader bsr = new BitStreamReader(data);
		int border = this.radioConfig.getSizeOfMessageID() + this.radioConfig.getSizeOfTime();
		while(bsr.getRemainBuffer() >= border)
		{
			try
			{
				CommunicationMessage msg = this.providerList[bsr.getBits(this.radioConfig.getSizeOfMessageID())].create(this, bsr);
				list.add(msg);
			} catch(Exception e) {
				//System.err.println("Received message is corrupt or format is different.");
				e.printStackTrace();
				return;
			}
		}
	}

	private void receiveVoiceMessage(String[] data, List<CommunicationMessage> list)
	{
		if (data == null || (data.length & 0x01) == 1 || list == null)
		{ return; }
		for (int count = 0; count < data.length; count += 2)
		{
			int id = Integer.parseInt(data[count]);
			String[] messageData = data[count + 1].split(this.voiceConfig.getDataSeparator());
			list.add(this.providerList[id].create(this, messageData));
		}
	}

	public Message createSendMessage() {
		// TODO: return data
		if (this.useRadio)
		{
			BitOutputStream bos = null;
			for (CommunicationMessage msg : this.sendMessages)
			{ this.providerList[msg.getMessageID()].write(this, bos, msg); }
			return null;
		}
		else
		{
			StringBuilder sb = new StringBuilder();
			for (CommunicationMessage msg : this.sendMessages)
			{ this.providerList[msg.getMessageID()].write(this, sb, msg); }
			return null;
		}
	}

	public List<CommunicationMessage> getReceivedMessage() {
		// MEMO:For compatible
		return this.receivedMessages;
	}

	public void addSendMessage(CommunicationMessage msg) {
		this.sendMessages.add(msg);
	}

	private void initLoadProvider() {
		this.registerStanderdProvider(MessageID.dummyMessage, new DummyMessageProvider());
		//this.register(CommunicationMessage.buildingMessageID, new BuildingMessageProvider(this.event));
		//this.register(CommunicationMessage.blockadeMessageID, new BlockadeMessageProvider(this.event));
		//this.register(CommunicationMessage.victimMessageID,   new VictimMessageProvider());
		//this.register(CommunicationMessage.positionMessageID, new PositionMessageProvider(this.event));
	}

	private void registerStanderdProvider(int messageID, MessageProvider provider) {
		provider.setMessageID(messageID);
		this.providerList[messageID] = provider;
	}

	public boolean registerProvider(int messageID, MessageProvider provider) {
		if (!this.developerMode || this.kernelTime != -1 || provider == null || messageID < 0)
		{ return false; }

		if (messageID >= this.providerList.length)
		{ this.providerList = Arrays.copyOf(this.providerList, messageID +1); }
		else if (this.providerList[messageID] != null)
		{ return false; }

		this.registerProvider(messageID, provider);
		this.radioConfig.updateMessageIDSize(messageID);
		this.searchEvent(this.providerList[messageID]);
		return true;
	}

	public boolean registerEvent(MessageEvent event) {
		if (event == null)
		{ return false; }

		this.eventList.add(event);
		this.searchProvider(event);
		return true;
	}

	private void searchProvider(MessageEvent event) {
		for (MessageProvider provider : this.providerList)
		{ provider.trySetEvent(event); }
	}

	private void searchEvent(MessageProvider provider) {
		// if (this.eventList.size() < 1)
		// {	return; }
		for (MessageEvent event : this.eventList)
		{ provider.trySetEvent(event); }
	}
}
