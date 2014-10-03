package comlib.manager;


import comlib.creator.MessageCreator;
import comlib.message.CommunicationMessage;
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
    
    private int time;
    
    private MessageCreator[] creatorList;
    private List<Object> eventList;
    
    private List<CommunicationMessage> receivedMessages;
    private List<CommunicationMessage> sendMessages;
    
    public MessageManager(Config config) {
        this.init(config);
    }
    
    private void init(Config config) {
        this.developerMode = config.getBooleanValue("comlib.develop.developerMode", false);
        this.radioConfig = new RadioConfig(config);
        this.voiceConfig = new VoiceConfig(config);
        this.useRadio = this.searchRadio(config);
        this.time = -1;
        this.creatorList = new MessageCreator[config.getIntValue("comlib.default.messageID", 16)];
        this.eventList = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
        this.sendMessages = new ArrayList<>();
        
        this.initCreator();
    }
    
    private boolean searchRadio(Config config) {
        boolean speakComm = config.getValue(Constants.COMMUNICATION_MODEL_KEY).equals(ChannelCommunicationModel.class.getName());
        int   numChannels = config.getIntValue("comms.channels.count");
        return speakComm && (numChannels > 1);
    }
    
    public boolean canUseRadio() {
        return this.useRadio;
    }

    public void setLimit(int l) {
        this.voiceConfig.setLimit(l);
    }
    
    public void receiveMessage(int count, Collection<Command> heard) {
        this.time = count;
        
        for(Command command : heard)
        {
            if(command instanceof AKSpeak)
            {
                byte[] data = ((AKSpeak)command).getContent();
                String voice = new String(data);
                if("Help".equalsIgnoreCase(voice) || "Ouch".equalsIgnoreCase(voice))
                    continue;
                
                String[] voiceData = voice.split(this.voiceConfig.getMessageSeparator());
                if(this.voiceConfig.getKeyword().equals(voiceData[0]))
                    this.receiveVoiceMessage(Arrays.copyOfRange(voiceData, 1, voiceData.length - 1), this.receivedMessages);
                else
                    this.receiveRadioMessage(data, this.receivedMessages);
            }
        }
    }
    
    //noise??
    public void receiveRadioMessage(byte[] datas, List<CommunicationMessage> list) {
        if(datas == null || list == null)
            return;
        BitStreamReader bsr = new BitStreamReader(data);
        int border = this.radioConfig.getSizeOfMessageID() + this.radioConfig.getSizeOfTime();
        while(bsr.getRemainBuffer() >= border)
        {
            try(CommunicationMessage msg = this.creatorList[bsr.getBits(this.radioConfig.getSizeOfMessageID())].create(this.radioConfig, bsr))
            {
                list.add(msg);
            }
            catch(Exception e)
            {
                //System.err.println("Received message is corrupt or format is different.");
                e.printStackTrace();
                return;
            }
        }
    }
    
    public void receiveVoiceMessage(String[] datas, List<CommunicationMessage> list)
    {
        if(datas == null || (datas.length & 0x01) == 1 || list == null)
            return;
        for(int count = 0; count < datas.length; count += 2) {
            int id = Integer.parseInt(datas[count]);
            String[] messageData = datas[count + 1].split(this.voiceConfig.getDataSeparator());
            list.add(this.creatorList[id].create(this.voiceConfig, messageData));
        }
    }
    
    //public List<Message> createSendMessage(){return null;}
    public Message createSendMessage() {
        if(this.useRadio) {
            BitOutputStream bos = null;
            for(CommunicationMessage msg : this.sendMessages)
                msg.create(this.radioConfig, bos);
            return null;
        }
        else {
            StringBuilder sb = new StringBuilder();
            for(CommunicationMessage msg : this.sendMessages)
                msg.create(this.voiceConfig, sb);
            return null;
        }
    }
    public List<CommunicationMessage> getReceivedMessage() {
        return this.receivedMessages;
    }
    
    public void addSendMessage(CommunicationMessage msg) {
        this.sendMessages.add(msg);
    }
    
    private void initCreator() {
        //this.register(CommunicationMessage.buildingMessageID, new BuildingMessageCreator(this.event));
        //this.register(CommunicationMessage.blockadeMessageID, new BlockadeMessageCreator(this.event));
        //this.register(CommunicationMessage.victimMessageID,   new VictimMessageCreator());
        //this.register(CommunicationMessage.positionMessageID, new PositionMessageCreator(this.event));
    }
    
    private void register(int id, MessageCreator creator) {
        creator.setMessageID(id);
        this.creatorList[id] = creator;
    }
    
    public boolean registerCreator(int id, MessageCreator creator) {
        if(!this.developerMode || this.time != -1 || creator == null || id < 0)
            return false;
        
        if(id >= this.creatorList.length)
            this.creatorList = Arrays.copyOf(this.creatorList, id + 1);
        else if(this.creatorList[id] != null)
            return false;
        
        this.register(id, creator);
        this.radioConfig.updateMessageIDSize(id);
        this.searchEvent(this.creatorList[id]);
        return true;
    }
    
    //public boolean registerEvent(int id, Object event){return true;}
    public boolean registerEvent(Object event) {
        if(event == null)
            return false;
        
        this.eventList.add(event);
        this.searchCreator(event);
        return true;
    }
    
    private void searchCreator(Object event) {
        for(MessageCreator creator : this.creatorList)
            creator.setEvent(event);
    }
    
    private void searchEvent(MessageCreator creator) {
        if(this.eventList.size() < 1)
            return;
        for(ReceiveEvent event : this.eventList)
            creator.setEvent(event);
    }
}