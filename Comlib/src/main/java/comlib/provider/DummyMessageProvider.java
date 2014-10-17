package comlib.provider;


import comlib.event.DummyMessageEvent;
import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.DummyMessage;
import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;


public class DummyMessageProvider extends MessageProvider<DummyMessage, DummyMessageEvent>
{

	public DummyMessageProvider(int id)
	{
		super(id);
	}

	protected void writeMessage(RadioConfig config, BitOutputStream bos, DummyMessage msg)
	{
	}

	protected void writeMessage(VoiceConfig config, StringBuilder sb, DummyMessage msg)
	{
	}

	protected DummyMessage createMessage(RadioConfig config, int time, BitStreamReader bsr)
	{
		return new DummyMessage(time, -1, -1);
	}

	protected DummyMessage createMessage(VoiceConfig config, int time, int ttl, String[] data, int next)
	{
			return new DummyMessage(
					time, ttl,
					Integer.parseInt(data[next])
					);
	}

	// public CommunicationMessage create(RadioConfig config, BitStreamReader bsr) {
	//     int time = bsr.getBits(config.getSizeOfTime());
	//     DummyMessage msg = this.createMessage(config, time, bsr);
	//     //CommunicationMessage msg = this.createMessage(config, time, bsr);
	//     this.event.receivedRadio(msg);
	//     return msg;
	// }
	//
}
