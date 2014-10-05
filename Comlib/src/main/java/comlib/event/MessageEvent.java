package comlib.event;


public abstract class MessageEvent<M extends CommunicationMessage> {

	public abstract void receivedRadio(M msg);	

	public abstract void receivedVoice(M msg);	
}
