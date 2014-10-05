package comlib.provider;


public class DummyMessageProvider<DummyMessage, DummyMessageEvent> extends MessageProvider {

	// public DummyEvent getDefaultEvent(MessageManager manager)
	// {
	// }

	protected void writeMessageRadio(RadioConfig config, BitOutputStream bos, M msg)
	{
	}

	protected void writeMessageVoice(VoiceConfig config, StringBuilder sb, M msg)
	{
	}

	protected DummyMessage createMessageRadio(RadioConfig config, int time, BitStreamReader bsr)
	{
	}

	protected DummyMessage createMessageVoice(VoiceConfig config, int time, int ttl, String[] datas, int next)
	{
	}
}
