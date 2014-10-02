package comlib.manager;

import comlib.agent.MessageEvent;
import comlib.creator.MessageCreator;
import comlib.creator.VictimMessageCreator;
import comlib.util.BitStreamReader;
import comlib.message.CommunicationMessage;
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

public class MessageManager
{
	private boolean developerMode;
	
	private RadioConfig radioConfig;
	private VoiceConfig voiceConfig;
	
	private boolean useRadio;
	
	private int time;
	
	//private MessageEvent event;
	
	private MessageCreator[] creatorList;
	
	private List<CommunicationMessage> receivedMessages;
	private List<CommunicationMessage> sendMessages;
	
	/*public MessageManager(Config config, MessageEvent messageEvent)
	{
		this.event = messageEvent;
		this.init(config);
	}*/

    public MessageManager(Config config) {
        this.init(config);
    }

    private void init(Config config)
	{
		this.developerMode    = config.getBooleanValue("comlib.develop.developerMode", false);
		this.radioConfig      = new RadioConfig(config);
		this.voiceConfig      = new VoiceConfig(config);
		this.useRadio         = this.searchRadio(config);
		this.time             = -1;
		this.creatorList      = new MessageCreator[config.getIntValue("comlib.default.messageID", 16)];
		this.receivedMessages = new ArrayList<>();
		this.sendMessages     = new ArrayList<>();
		
		this.registerCreator();
	}
	
	private boolean searchRadio(Config config)
	{
		boolean speakComm = config.getValue(Constants.COMMUNICATION_MODEL_KEY).equals(ChannelCommunicationModel.class.getName());
		int   numChannels = config.getIntValue("comms.channels.count");
		
		return speakComm && (numChannels > 1);
	}
	
	public boolean canUseRadio()
	{
		return this.useRadio;
	}
	
	public List<CommunicationMessage> getReceivedMessage()
	{
		return this.receivedMessages;
	}
	
	public List<CommunicationMessage> getSendMessage()
	{
		return this.sendMessages;
	}

    public void addMessage(CommunicationMessage msg)
    {
        this.sendMessages.add(msg);
    }
	
	public List<Message> createSendMessage()
	{
		List<Message> list = new ArrayList<Message>();
		if(this.useRadio)
		{
		}
		return list;
	}
	
	public void receiveMessage(int count, Collection<Command> heard)
	{
		this.time = count;
		
		for(Command c : heard)
		{
			if(c instanceof AKSpeak)
			{
				byte[] data = ((AKSpeak)c).getContent();
				String voice = new String(data);
				
				if("Help".equals(voice) || "Ouch".equals(voice))
					continue;
				
				String[] voiceData = voice.split(this.voiceConfig.getVoiceSeparator());
				
				if("Voice".equals(voiceData[0]))
					this.receivedMessages.addAll(this.receiveVoiceMessage(Arrays.copyOfRange(voiceData, 1, voiceData.length - 1)));
				else
					this.receivedMessages.addAll(this.receiveRadioMessage(data));
			}
		}
	}
	
	public List<CommunicationMessage> receiveRadioMessage(byte[] data)
	{
		if(data == null)
			return  null;
		
		List<CommunicationMessage> list = new ArrayList<CommunicationMessage>();
		BitStreamReader bsr = new BitStreamReader(data);
		int border = this.radioConfig.getSizeOfMessageID() + this.radioConfig.getSizeOfTime();
		
		while(bsr.getRemainBuffer() >= border)
		{
			try
			{
				int id = bsr.getBits(this.radioConfig.getSizeOfMessageID());
				list.add(this.creatorList[id].create(this.radioConfig, bsr));
			}
			catch(Exception e)
			{
				System.err.println("Received message is corrupt or format is different.");
				return new ArrayList<CommunicationMessage>();
			}
		}
		return list;
	}
	
	public List<CommunicationMessage> receiveVoiceMessage(String[] datas)
	{
		if(datas == null || (datas.length & 0x01) == 1)
			return null;
		
		List<CommunicationMessage> list = new ArrayList<CommunicationMessage>();
		int count = 0;
		
		while(count < datas.length)
		{
			int id = Integer.parseInt(datas[count]);
			list.add(this.creatorList[id].create(this.voiceConfig, datas[count + 1].split(this.voiceConfig.getDataSeparator())));
			count += 2;
		}
		
		return list;
	}
	
	private void registerCreator()
	{
		//this.registerCreator(CommunicationMessage.buildingMessageID, new BuildingMessageCreator(this.event));
		//this.registerCreator(CommunicationMessage.blockadeMessageID, new BlockadeMessageCreator(this.event));
		this.registerCreator(CommunicationMessage.victimMessageID,   new VictimMessageCreator());
		//this.registerCreator(CommunicationMessage.positionMessageID, new PositionMessageCreator(this.event));
	}
	
	private void registerCreator(int id, MessageCreator creator)
	{
		if(id < 0 || id >= this.creatorList.length)
			throw new RuntimeException("Error : Bad MessageID");
		
		if(this.creatorList[id] != null)
			throw new RuntimeException("Error : MessageID is already registered.");
		
		if(creator != null)
		{
			creator.setMessageID(id);
			this.creatorList[id] = creator;
		}
	}
	
	public boolean registerCustomCreator(int id, MessageCreator creator)
	{
		if(!this.developerMode || this.time != -1 || creator == null || id < 0)
			return false;
		
		if(id < this.creatorList.length)
		{
			if(this.creatorList[id] != null)
				return false;
			
			this.registerCreator(id, creator);
			return true;
		}
		
		this.creatorList = Arrays.copyOf(this.creatorList, id + 1);
		creator.setMessageID(id);
		this.creatorList[id] = creator;
		this.radioConfig.updateSize(id);
		return true;
	}
}