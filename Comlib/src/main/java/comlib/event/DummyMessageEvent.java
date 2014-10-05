package comlib.event;


public abstract class DummyMessageEvent<DummyMessage> {

	public void receivedRadio(DummyMessage msg)
	{
        /*DummyMessage a;
        a = new DummyMessage(-1);
        System.out.println(a);
        */
	}

	public void receivedVoice(DummyMessage msg)
	{
		this.receivedRadio(msg);
	}
}
