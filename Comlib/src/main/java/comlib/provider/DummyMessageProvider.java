package comlib.provider;


import comlib.event.DummyMessageEvent;
import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.DummyMessage;
import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;

public class DummyMessageProvider extends MessageProvider<DummyMessage, DummyMessageEvent>{

	// public DummyEvent getDefaultEvent(MessageManager manager)
	// {
	// }

<<<<<<< HEAD
	protected void writeMessage(RadioConfig config, BitOutputStream bos, DummyMessage msg)
=======
    protected void writeMessageRadio(RadioConfig config, BitOutputStream bos, DummyMessage msg)
>>>>>>> 5bcad29141f28be83b9b478aaededb52d6f4a0b7
	{
	}

	protected void writeMessage(VoiceConfig config, StringBuilder sb, DummyMessage msg)
	{
	}

	protected DummyMessage createMessage(RadioConfig config, int time, BitStreamReader bsr)
	{
		/*DummyMessage a;
			a = new DummyMessage(time, -1, -1);
			return a;*/
		// return DummyMessage.getInstance(-1);
		return new DummyMessage(time, -1, -1);
	}

	protected DummyMessage createMessage(VoiceConfig config, int time, int ttl, String[] datas, int next)
	{
		try {
			// return null;//new DummyMessage(time, ttl, -1);
			return new DummyMessage(time, ttl, -1);
		}
		catch (Exception e) {
			return null;
		}
	}

	// public CommunicationMessage create(RadioConfig config, BitStreamReader bsr) {
	//     int time = bsr.getBits(config.getSizeOfTime());
	//     DummyMessage msg = this.createMessage(config, time, bsr);
	//     //CommunicationMessage msg = this.createMessage(config, time, bsr);
	//     this.event.receivedRadio(msg);
	//     return msg;
	// }
	//
	// public CommunicationMessage create(VoiceConfig config, String[] datas) {
	//     int time = Integer.parseInt(datas[0]);
	//     int ttl  = Integer.parseInt(datas[1]);
	//     DummyMessage msg = this.createMessage(config, time, ttl, datas, 2);
	//     //CommunicationMessage msg = this.createMessage(config, time, ttl, datas, 2);
	//     this.event.receivedVoice(msg);
	//     return msg;
	// }
}
