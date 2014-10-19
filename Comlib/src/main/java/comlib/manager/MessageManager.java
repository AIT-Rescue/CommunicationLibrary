package comlib.manager;

import comlib.provider.DummyMessageProvider;
import comlib.provider.MessageProvider;
import comlib.event.MessageEvent;
import comlib.message.CommunicationMessage;
import comlib.message.MessageID;
import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;

import rescuecore2.Constants;
import rescuecore2.config.Config;
import rescuecore2.messages.Command;
import rescuecore2.messages.Message;
import rescuecore2.standard.kernel.comms.ChannelCommunicationModel;
import rescuecore2.standard.messages.AKSpeak;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class MessageManager
{
	private boolean developerMode;

	private RadioConfig radioConfig;
	private VoiceConfig voiceConfig;

	private boolean useRadio;
	private int numRadio;
	private int numVoice;

	private int kernelTime;

	private MessageProvider[] providerList;
	private List<MessageEvent> eventList;

	private List<CommunicationMessage> receivedMessages; // FOR-COMPATIBLE
	private List<CommunicationMessage> sendMessages;
	private BitOutputStream[] bitOutputStreamList;

	public MessageManager(Config config)
	{
		this.init(config);
	}

	private void init(Config config)
	{
		this.developerMode = config.getBooleanValue("comlib.develop.developerMode", false);
		this.radioConfig = new RadioConfig(config);
		this.voiceConfig = new VoiceConfig(config);
		this.kernelTime = -1;
		this.providerList = new MessageProvider[config.getIntValue("comlib.default.messageID", 16)];
		this.bitOutputStreamList = new BitOutputStream[config.getIntValue("comlib.default.messageID", 16)];
		this.eventList = new ArrayList<>();
		this.receivedMessages = new ArrayList<>();
		this.sendMessages = new ArrayList<>();

		this.initCommunicationMode(config);
		this.initLoadProvider();
	}

	private void initCommunicationMode(Config config)
	{
		//boolean speakComm = config.getValue(Constants.COMMUNICATION_MODEL_KEY).equals(ChannelCommunicationModel.class.getName());
		//int numChannels = config.getIntValue("comms.channels.count");

		this.numRadio = config.getIntValue("comms.channels.max.platoon");
		this.numVoice = ((config.getValue("comms.channels.0.type").equals("voice")) ? 1 : 0);
		this.useRadio = ( this.numRadio >= 1 );
	}

	public boolean canUseRadio()
	{ return this.useRadio; }

	public RadioConfig getRadioConfig()
	{ return this.radioConfig; }

	public VoiceConfig getVoiceConfig()
	{ return this.voiceConfig; }

	public int getTime()
	{ return this.kernelTime; }

	public void receiveMessage(int time, Collection<Command> heard)
	{
		this.kernelTime = time;
		this.receivedMessages.clear();

		for (BitOutputStream bos : bitOutputStreamList)
		{ bos.reset(); }

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

	private void receiveRadioMessage(byte[] data, List<CommunicationMessage> list)
	{
		// TODO: ノイズ対策をするべき?
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

	public List<Message> createSendMessage(EntityID agentID)
	{
		// TODO: return data
		List<Message> messages = new ArrayList<Message>();

		for (int ch = 1; ch <= numRadio; ch++)
		{
			for (BitOutputStream bos : bitOutputStreamList)
			{
				if (bos.size() <= 0)
				{ continue; }

				messages.add(new AKSpeak(agentID, this.getTime(), ch, bos.toByteArray()));

				if (ch == numRadio)
				{
				}
			}
		}
		// if (this.useRadio)
		// {
		// 	BitOutputStream bos = new BitOutputStream();
		// 	for (CommunicationMessage msg : this.sendMessages)
		// 	{ this.providerList[msg.getMessageID()].write(this, bos, msg); }
		// }

		StringBuilder sb = new StringBuilder();
		for (CommunicationMessage msg : this.sendMessages)
		{ this.providerList[msg.getMessageID()].write(this, sb, msg); }

		return messages;
	}

	public List<CommunicationMessage> getReceivedMessage() // FOR-COMPATIBLE
	{
		return this.receivedMessages;
	}

	public <M extends CommunicationMessage> void addSendMessage(M msg)
	{
		this.sendMessages.add(msg);
		int msgID = msg.getMessageID();
		// TODO: need cutting data
		this.providerList[msgID].write(this, bitOutputStreamList[msgID], msg);
	}

	// public void old_addSendMessage(CommunicationMessage msg)
	// {
	// 	this.sendMessages.add(msg);
	// }

	public void addVoiceSendMessage(CommunicationMessage msg)
	{
		// TODO: NFCのリストを用意して．．．いろいろ
		this.sendMessages.add(msg);
	}

	private void initLoadProvider()
	{
		// TODO: Load provider
		this.registerStandardProvider(new DummyMessageProvider(MessageID.dummyMessage));
		//this.register(CommunicationMessage.buildingMessageID, new BuildingMessageProvider(this.event));
		//this.register(CommunicationMessage.blockadeMessageID, new BlockadeMessageProvider(this.event));
		//this.register(CommunicationMessage.victimMessageID,   new VictimMessageProvider());
		//this.register(CommunicationMessage.positionMessageID, new PositionMessageProvider(this.event));
	}

	private void registerStandardProvider(MessageProvider provider)
	{
		this.providerList[provider.getMessageID()] = provider;
	}

	public boolean registerProvider(MessageProvider provider)
	{
		int messageID = provider.getMessageID();
		if (!this.developerMode || this.kernelTime != -1 || provider == null || messageID < 0)
		{ return false; }

		if (messageID >= this.providerList.length)
		{
			this.providerList = Arrays.copyOf(this.providerList, messageID +1);
			this.bitOutputStreamList = Arrays.copyOf(this.bitOutputStreamList, messageID +1);
		}
		else if (this.providerList[messageID] != null)
		{ return false; }

		this.registerStandardProvider(provider);
		this.radioConfig.updateMessageIDSize(messageID);
		this.searchEvent(this.providerList[messageID]);
		return true;
	}

	public boolean registerEvent(MessageEvent event)
	{
		if (event == null)
		{ return false; }

		this.eventList.add(event);
		this.searchProvider(event);
		return true;
	}

	private void searchProvider(MessageEvent event)
	{
		for (MessageProvider provider : this.providerList)
		{ provider.trySetEvent(event); }
	}

	private void searchEvent(MessageProvider provider)
	{
		// if (this.eventList.size() < 1)
		// {	return; }
		for (MessageEvent event : this.eventList)
		{ provider.trySetEvent(event); }
	}
}
