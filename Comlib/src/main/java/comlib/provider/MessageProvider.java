package comlib.provider;


import comlib.manager.MessageManager;
import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.CommunicationMessage;
import comlib.util.BitStreamReader;

public abstract class MessageProvider<M extends CommunicationMessage, E extends ReceiveEvent> {

    protected int messageID;
    
    protected E event;
    
    public MessageProvider(MessageManager manager) {
        this.messageID = -1;
        this.event = this.getDefaultEvent(manager);
    }
    
    public void setMessageID(int id) {
        if(id >= 0 && this.messageID == -1)
            this.messageID = id;
    }
    
    public int getMessageID() {
        return this.messageID;
    }
    
    public abstract E getDefaultEvent(MessageManager manager);
    
    public abstract M createMessage(RadioConfig config, int time, BitStreamReader bsr);
    
    public abstract M createMessage(VoiceConfig config, int time, int ttl, String[] datas, int next);
    
    public CommunicationMessage create(RadioConfig config, BitStreamReader bsr) {
        int time = bsr.getBits(config.getSizeOfTime());
        M msg = this.createMessage(config, time, bsr);
        this.event.receiveRadio(msg);
        return msg;
    }
    
    public CommunicationMessage create(VoiceConfig config, String[] datas) {
        int time = Integer.parseInt(datas[0]);
        int ttl  = Integer.parseInt(datas[1]);
        M msg = this.createMessage(config, time, ttl, datas, 2);
        this.event.receiveVoice(msg);
        return msg;
    }
}
