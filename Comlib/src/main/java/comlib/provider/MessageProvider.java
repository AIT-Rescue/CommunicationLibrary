package comlib.provider;


import comlib.event.MessageEvent;
import comlib.manager.MessageManeger;
import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.CommunicationMessage;
import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;

public abstract class MessageProvider<M extends CommunicationMessage, E extends MessageEvent> {

	protected int messageID;

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

	protected abstract void writeMessageRadio(RadioConfig config, BitOutputStream bos, M msg);

	protected abstract void writeMessageVoice(VoiceConfig config, StringBuilder sb, M msg);

	protected abstract M createMessageRadio(RadioConfig config, int time, BitStreamReader bsr);

	protected abstract M createMessageVoice(VoiceConfig config, int time, int ttl, String[] datas, int next);
	
	public void write(MessageManager manager, BitOutputStream bos, M msg)
	{
		//TODO: Think about argument order
		RadioConfig config = manager.getRadioConfig();
		bos.writeBits(this.messageID, config.getSizeOfMessageID());
		bos.writeBits(manager.getTime(), config.getSizeOfTime());
		this.writeMessageRadio(config, bos, msg);
	}
	
	public void write(MessageManager manager, StringBuilder sb, M msg)
	{
		if(msg.getTTL() == 0)
		{ return; }
	
		VoiceConfig config = manager.getVoiceConfig();
		config.appendMessageID(sb, this.messageID);
		config.appendData(sb, String.valueOf(manager.getTime()));
		if(msg.getTTL() < 0)
		{ config.appendLimit(sb); }
		else
		{ config.appendData(sb, String.valueOf(msg.getTTL() - 1)); }
		this.writeMessageVoice(config, sb, msg);
		config.appendMessageSeparator(sb);
	}

	public CommunicationMessage create(MessageManager manager, BitStreamReader bsr) {
		RadioConfig config = manager.getRadioConfig();
		int time = bsr.getBits(config.getSizeOfTime());
		M msg = this.createMessageRadio(config, time, bsr);
		this.event.receivedRadio(msg);
		return msg;
	}

	public CommunicationMessage create(MessageManager manager, String[] datas) {
		VoiceConfig config = manager.getVoiceConfig();
		int time = Integer.parseInt(datas[0]);
		int ttl  = Integer.parseInt(datas[1]);
		M msg = this.createMessageVoice(config, time, ttl, datas, 2);
		this.event.receivedVoice(msg);
		return msg;
	}

	public void trySetEvent(MessageEvent ev) {
		//if (ev instanceof E) //こうかけないからクソ
		if (ev != null)
		{ this.event = (E) ev; }
	}
}
