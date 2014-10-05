package comlib.event;


public abstract class DummyMessageEvent<DummyMessage> {

	public void receivedRadio(DummyMessage msg)
	{
	}

	public void receivedVoice(DummyMessage msg)
	{
		this.receivedRadio(msg);
	}
}
