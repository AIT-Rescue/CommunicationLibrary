package comlib.provider;


public class DummyMessageProvider<DummyMessage, DummyMessageEvent> extends MessageProvider {

	// public DummyEvent getDefaultEvent(MessageManager manager)
	// {
	// }

	public void writeMessage(RadioConfig config, BitOutputStream bos)
	{
	}

	public void writeMessage(VoiceConfig config, StringBuilder sb)
	{
	}

	public DummyMessage createMessage(RadioConfig config, int time, BitStreamReader bsr)
	{
	}

	public DummyMessage createMessage(VoiceConfig config, int time, int ttl, String[] datas, int next)
	{
	}
}
