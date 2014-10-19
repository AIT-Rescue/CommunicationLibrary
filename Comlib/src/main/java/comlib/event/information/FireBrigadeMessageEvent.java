package comlib.event.information;

import comlib.event.MessageEvent;
import comlib.message.information.FireBrigadeMessage;


public abstract class FireBrigadeMessageEvent extends MessageEvent<FireBrigadeMessage>
{
	public void receivedRadio(FireBrigadeMessage msg)
	{
		// You cannot write here.
	}

	public void receivedVoice(FireBrigadeMessage msg)
	{
		// You cannot write here.
		// This code is default handler.
		this.receivedRadio(msg);
	}
}
