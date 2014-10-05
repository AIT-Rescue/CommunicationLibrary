package comlib.event;


import comlib.message.CommunicationMessage;

public abstract class MessageEvent<M extends CommunicationMessage> {

	public abstract void receivedRadio(M msg);	

	public abstract void receivedVoice(M msg);	
}
