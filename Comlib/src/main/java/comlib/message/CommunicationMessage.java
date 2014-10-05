package comlib.message;


import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.util.BitOutputStream;

public abstract class CommunicationMessage {

	protected int messageID;

	protected int kernelTime;

	protected int messageTTL;

	private CommunicationMessage(){}

	public CommunicationMessage(int id) {
		CommunicationMessage(id, -1, -1);
	}

	// private CommunicationMessage(){}
	//
	public CommunicationMessage(int id,int time, int ttl) {
		this.kernelTime = time;
		this.messageTTL = ttl;
	}
	//
	// public CommunicationMessage(int id, int t, int l) {
	// 	this.messageID = id;
	// 	this.time = t;
	// 	this.ttl = l;
	// }

	public int getMessageID() {
		return messageID;
	}

	public int getTime() {
		return this.time;
	}

	public int getTTL() {
		return this.messageTTL;
	}

}
