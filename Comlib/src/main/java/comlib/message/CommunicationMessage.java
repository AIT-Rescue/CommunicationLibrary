package comlib.message;


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
		if(this.ttl < 0)
			this.ttl = config.getLimit();
		
		config.appendMessageID(sb, String.valueOf(this.messageID));
		config.appendData(sb, String.valueOf(this.time));
		config.appendData(sb, String.valueOf(this.ttl - 1));
		this.createSendMessage(config, sb);
		config.appendVoiceSeparator(sb);
	}
}
