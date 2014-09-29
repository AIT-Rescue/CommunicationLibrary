package comlib.creator;

import comlib.message.CommunicationMessage;

import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;
import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;

public abstract class MessageCreator<M extends CommunicationMessage>
{
	protected int messageID = -1;
	
	public MessageCreator()
	{
	}
	
	public void setMessageID(int id)
	{
		if(id >= 0 && this.messageID == -1)
		{
			this.messageID = id;
		}
	}
	
	public int getMessageID()
	{
		return this.messageID;
	}
	
	public abstract CommunicationMessage createMessage(RadioConfig config, int time, BitStreamReader bsr);
	
	public abstract CommunicationMessage createMessage(VoiceConfig config, int time, int ttl, String[] datas);
	
	public CommunicationMessage create(RadioConfig config, BitStreamReader bsr)
	{
		int time = bsr.getBits(config.getSizeOfTime());
		return this.createMessage(config, time, bsr);
	}
	
	public CommunicationMessage create(VoiceConfig config, String[] datas)
	{
		int time = Integer.parseInt(datas[0]);
		int ttl  = Integer.parseInt(datas[1]);
		return this.createMessage(config, time, ttl, datas);
	}
	
	public abstract void addData(RadioConfig config, BitOutputStream bos, M msg);
	
	public abstract void addData(VoiceConfig config, StringBuilder builder, M msg);
	
	public void add(RadioConfig config, BitOutputStream bos, CommunicationMessage msg)
	{
		bos.writeBits(this.getMessageID(), config.getSizeOfMessageID());
		bos.writeBits(msg.getTime(), config.getSizeOfTime());
		this.addData(config, bos, (M)msg);
	}
	
	public void add(VoiceConfig config, StringBuilder builder, CommunicationMessage msg)
	{
		int ttl = msg.getLimit();
		if(ttl == 0)
			return;
		if(ttl < 0)
			ttl = config.getLimit();
		
		config.appendMessageID(builder, String.valueOf(this.getMessageID()));
		config.appendData(builder, String.valueOf(msg.getTime()));
		config.appendData(builder, String.valueOf(ttl - 1));
		this.addData(config, builder, (M)msg);
		config.appendVoiceSeparator(builder);
	}
	
	//public abstract M copy(M msg);
}