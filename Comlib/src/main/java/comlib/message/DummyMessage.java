package comlib.message;


import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.util.BitOutputStream;

public abstract class DummyMessage extends CommunicationMessage {

	private int dummyTest;

	public DummyMessage(int test) {
		super(MessageID.dummyMessage);
		dummyTest = test;
	}

	public DummyMessage(int time, int ttl, int test) {
		super(MessageID.dummyMessage, time, ttl);
		dummyTest = test;
	}


	// public abstract void createSendMessage(RadioConfig config, BitOutputStream bos);
	//
	// public void create(RadioConfig config, BitOutputStream bos)
	// {
	// 	bos.writeBits(this.messageID, config.getSizeOfMessageID());
	// 	bos.writeBits(this.time, config.getSizeOfTime());
	// 	this.createSendMessage(config, bos);
	// }
	//
	// public abstract void createSendMessage(VoiceConfig config, StringBuilder sb);
	//
	// public void create(VoiceConfig config, StringBuilder sb)
	// {
	// 	if(this.ttl == 0)
	// 		return;
	//
	// 	config.appendMessageID(sb, this.messageID);
	// 	config.appendData(sb, String.valueOf(this.time));
	// 	if(this.ttl < 0)
	// 		config.appendLimit(sb);
	// 	else
	// 		config.appendData(sb, String.valueOf(this.ttl - 1));
	// 	this.createSendMessage(config, sb);
	// 	config.appendMessageSeparator(sb);
	// }
}
