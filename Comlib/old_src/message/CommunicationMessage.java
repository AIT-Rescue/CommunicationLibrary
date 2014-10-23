package comlib.message;



public class CommunicationMessage
{
	public static final int buildingMessageID = 0;
	public static final int blockadeMessageID = 1;
	public static final int victimMessageID   = 2;
	public static final int positionMessageID = 3;
	
	protected int messageID;
	
	protected int time;
	
	protected int ttl;
	
	private CommunicationMessage() {}
	
	public CommunicationMessage(int id, int t)
	{
		this(id, t, -1);
	}
	
	public CommunicationMessage(int id, int t, int l)
	{
		this.messageID = id;
		this.time = t;
		this.ttl = l;
	}
	
	public int getMessageID()
	{
		return this.messageID;
	}
	
	public int getTime()
	{
		return this.time;
	}
	
	public int getLimit()
	{
		return this.ttl;
	}
}