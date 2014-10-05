package comlib.provider;


import comlib.manager.RadioConfig;
import comlib.manager.VoiceConfig;
import comlib.message.CommunicationMessage;
import comlib.message.DummyMessage;
import comlib.message.MessageID;
import comlib.util.BitOutputStream;
import comlib.util.BitStreamReader;

public class DummyMessageProvider<DummyMessage extends CommunicationMessage, DummyMessageEvent> extends MessageProvider {

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
        /*DummyMessage a;
        a = new DummyMessage(time, -1, -1);
        return a;*/
        return DummyMessage.getInstance(-1);
	}

	protected DummyMessage createMessageVoice(VoiceConfig config, int time, int ttl, String[] datas, int next)
	{
        try {
            return null;//new DummyMessage(time, ttl, -1);
        }
        catch (Exception e) {
            return null;
        }
	}

    public CommunicationMessage create(RadioConfig config, BitStreamReader bsr) {
        int time = bsr.getBits(config.getSizeOfTime());
        DummyMessage msg = this.createMessage(config, time, bsr);
        //CommunicationMessage msg = this.createMessage(config, time, bsr);
        this.event.receivedRadio(msg);
        return msg;
    }

    public CommunicationMessage create(VoiceConfig config, String[] datas) {
        int time = Integer.parseInt(datas[0]);
        int ttl  = Integer.parseInt(datas[1]);
        DummyMessage msg = this.createMessage(config, time, ttl, datas, 2);
        //CommunicationMessage msg = this.createMessage(config, time, ttl, datas, 2);
        this.event.receivedVoice(msg);
        return msg;
    }
}
