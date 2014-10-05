package comlib.provider;


public class DummyMessageProvider<DummyMessage, DummyMessageEvent> extends MessageProvider {

	// public DummyEvent getDefaultEvent(MessageManager manager)
	// {
	// }

	@Override
	protected void writeMessage(RadioConfig config, BitOutputStream bos)
	{
	}

	@Override
	protected void writeMessage(VoiceConfig config, StringBuilder sb)
	{
	}

	@Override
	protected M createMessage(RadioConfig config, int time, BitStreamReader bsr)
	{
	}

	@Override
	protected M createMessage(VoiceConfig config, int time, int ttl, String[] datas, int next)
	{
	}
}
