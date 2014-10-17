package comlib.message;


abstract public class InformationMessage extends CommunicationMessage {

	public DummyMessage(int messageID) {
		super(messageID);
	}

	public DummyMessage(int messageID, int time, int ttl) {
		super(messageID, time, ttl);
	}

}
