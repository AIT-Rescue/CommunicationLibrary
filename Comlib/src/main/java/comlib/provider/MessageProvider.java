package comlib.provider;


import comlib.event.MessageEvent;
import comlib.manager.MessageManager;
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

//<<<<<<< HEAD
	protected abstract M createMessageRadio(RadioConfig config, int time, BitStreamReader bsr);

	protected abstract M createMessageVoice(VoiceConfig config, int time, int ttl, String[] datas, int next);
	
	public void write(MessageManager manager, BitOutputStream bos, M msg)
	{
		//TODO: Think about argument order
		RadioConfig config = manager.getRadioConfig();
		bos.writeBits(this.messageID, config.getSizeOfMessageID());
		bos.writeBits(manager.getTime(), config.getSizeOfTime());
		this.writeMessageRadio(config, bos, msg);
// =======
// 	//public abstract <C extends CommunicationMessage> C createMessage(RadioConfig config, int time, BitStreamReader bsr);
//     public abstract M createMessage(RadioConfig config, int time, BitStreamReader bsr);
//     //public abstract CommunicationMessage createMessage(RadioConfig config, int time, BitStreamReader bsr);
//
// 	public abstract M createMessage(VoiceConfig config, int time, int ttl, String[] datas, int next);
//     //public abstract CommunicationMessage createMessage(VoiceConfig config, int time, int ttl, String[] datas, int next);
//
//     //TODO: 送信処理
// 	public void write(RadioConfig config, BitOutputStream bos)
// 	{
// 		/*bos.writeBits(this.messageID, config.getSizeOfMessageID());
// 		bos.writeBits(this.time, config.getSizeOfTime());
// 		this.writeMessage(config, bos);
// 		*/
// >>>>>>> 8ddf4120c27cedcf9376284bdd193507f021b4e3
	}
	
	public void write(MessageManager manager, StringBuilder sb, M msg)
	{
// <<<<<<< HEAD
		if(msg.getTTL() == 0)
		{ return; }
// =======
// 		/*if(this.ttl == 0)
// 			return;
// >>>>>>> 8ddf4120c27cedcf9376284bdd193507f021b4e3
	
		VoiceConfig config = manager.getVoiceConfig();
		config.appendMessageID(sb, this.messageID);
		config.appendData(sb, String.valueOf(manager.getTime()));
		if(msg.getTTL() < 0)
		{ config.appendLimit(sb); }
		else
// <<<<<<< HEAD
		{ config.appendData(sb, String.valueOf(msg.getTTL() - 1)); }
		this.writeMessageVoice(config, sb, msg);
// =======
// 			config.appendData(sb, String.valueOf(this.ttl - 1));
// 		this.writeMessage(config, sb);
// >>>>>>> 8ddf4120c27cedcf9376284bdd193507f021b4e3
		config.appendMessageSeparator(sb);
		// */
	}

	public CommunicationMessage create(MessageManager manager, BitStreamReader bsr) {
		RadioConfig config = manager.getRadioConfig();
		int time = bsr.getBits(config.getSizeOfTime());
// <<<<<<< HEAD
		M msg = this.createMessageRadio(config, time, bsr);
		this.event.receivedRadio(msg);
// =======
// 		M msg = this.createMessage(config, time, bsr);
//         //CommunicationMessage msg = this.createMessage(config, time, bsr);
// 		this.event.receivedRadio((M)msg);
// >>>>>>> 8ddf4120c27cedcf9376284bdd193507f021b4e3
		return msg;
	}

	public CommunicationMessage create(MessageManager manager, String[] datas) {
		VoiceConfig config = manager.getVoiceConfig();
		int time = Integer.parseInt(datas[0]);
		int ttl  = Integer.parseInt(datas[1]);
// <<<<<<< HEAD
		M msg = this.createMessageVoice(config, time, ttl, datas, 2);
		this.event.receivedVoice(msg);
// =======
// 		M msg = this.createMessage(config, time, ttl, datas, 2);
//         //CommunicationMessage msg = this.createMessage(config, time, ttl, datas, 2);
// 		this.event.receivedVoice((M)msg);
// >>>>>>> 8ddf4120c27cedcf9376284bdd193507f021b4e3
		return msg;
	}

	public void trySetEvent(MessageEvent ev) {
		//if (ev instanceof E) //こうかけないからクソ
		if (ev != null)
		{ this.event = (E) ev; }
	}
}
