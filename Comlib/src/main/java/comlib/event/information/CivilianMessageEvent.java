package comlib.event.information;

import comlib.event.MessageEvent;
import comlib.message.information.CivilianMessage;


public abstract class CivilianMessageEvent extends MessageEvent<CivilianMessage>
{
	public void receivedRadio(CivilianMessage msg)
	{
		// You cannot write here.
	}

	public void receivedVoice(CivilianMessage msg)
	{
		// You cannot write here.
		// This code is default handler.
		this.receivedRadio(msg);
	}
}
