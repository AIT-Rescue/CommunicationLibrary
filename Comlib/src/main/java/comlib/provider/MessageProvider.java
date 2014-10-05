package comlib.provider;


import comlib.manager.MessageManager;
import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.CommunicationMessage;
import comlib.util.BitStreamReader;

public abstract class MessageProvider<M extends CommunicationMessage, E extends MessageEvent> {

	protected int messageID;
	// final int messageTypeID = -1;

	protected E event;

	public MessageProvider() {
		// this.event = this.getDefaultEvent(manager);
	}

	public void setMessageID(int id) {
		if(id >= 0 && this.messageID == -1)
			this.messageID = id;
	}

	public int getMessageID() {
		return this.messageID;
	}

	// public abstract E getDefaultEvent(MessageManager manager);

	public abstract void writeMessage(RadioConfig config, BitOutputStream bos);

	public abstract void writeMessage(VoiceConfig config, StringBuilder sb);

	public abstract M createMessage(RadioConfig config, int time, BitStreamReader bsr);

	public abstract M createMessage(VoiceConfig config, int time, int ttl, String[] datas, int next);
	
	public void write(RadioConfig config, BitOutputStream bos)
	{
		bos.writeBits(this.messageID, config.getSizeOfMessageID());
		bos.writeBits(this.time, config.getSizeOfTime());
		this.createSendMessage(config, bos);
	}
	
	public void write(VoiceConfig config, StringBuilder sb)
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

	public CommunicationMessage create(RadioConfig config, BitStreamReader bsr) {
		int time = bsr.getBits(config.getSizeOfTime());
		M msg = this.createMessage(config, time, bsr);
		this.event.receivedRadio(msg);
		return msg;
	}

	public CommunicationMessage create(VoiceConfig config, String[] datas) {
		int time = Integer.parseInt(datas[0]);
		int ttl  = Integer.parseInt(datas[1]);
		M msg = this.createMessage(config, time, ttl, datas, 2);
		this.event.receivedVoice(msg);
		return msg;
	}

	public void trySetEvent(MessageEvent ev) {
		if (ev instanceof E)
		{
			this event = ev;
		}
	}
}
