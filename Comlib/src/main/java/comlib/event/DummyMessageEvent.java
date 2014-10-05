package comlib.event;


public abstract class DummyMessageEvent<DummyMessage> {

	public void receivedRadio(M msg)
	{
	}

	public void receivedVoice(M msg)
	{
		this.receivedRadio(msg);
	}
}
