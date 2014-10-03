package comlib.message;


import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.util.BitOutputStream;

public abstract class CommunicationMessage {
    
    protected int messageID;
    
    protected int time;
    
    protected int ttl;
    
    private CommunicationMessage(){}
    
    public CommunicationMessage(int id, int t) {
        this(id, t, -1);
    }
    
    public CommunicationMessage(int id, int t, int l) {
        this.messageID = id;
        this.time = t;
        this.ttl = l;
    }
    
    public int getMessageID() {
        return this.messageID;
    }
    
    public int getTime() {
        return this.time;
    }
    
    public int getLimit() {
        return this.ttl;
    }
    
    public abstract void createSendMessage(RadioConfig config, BitOutputStream bos);
    
    public void create(RadioConfig config, BitOutputStream bos)
	{
		bos.writeBits(this.messageID, config.getSizeOfMessageID());
		bos.writeBits(this.time, config.getSizeOfTime());
		this.createSendMessage(config, bos);
	}

    public abstract void createSendMessage(VoiceConfig config, StringBuilder sb);

    public void create(VoiceConfig config, StringBuilder sb)
    {
        if(this.ttl == 0)
            return;

		config.appendMessageID(sb, this.messageID);
		config.appendData(sb, String.valueOf(this.time));
		if(this.ttl < 0)
            config.appendLimit(sb);
        else
            config.appendData(sb, String.valueOf(this.ttl - 1));
		this.createSendMessage(config, sb);
		config.appendMessageSeparator(sb);
	}
}
