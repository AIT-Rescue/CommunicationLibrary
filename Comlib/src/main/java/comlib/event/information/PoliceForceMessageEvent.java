package comlib.event;

import comlib.message.PoliceForceMessage;


public abstract class PoliceForceMessageEvent extends MessageEvent<PoliceForceMessage>
{
	public void receivedRadio(PoliceForceMessage msg)
	{
		// You cannot write here.
	}

	public void receivedVoice(PoliceForceMessage msg)
	{
		// You cannot write here.
		// This code is default handler.
		this.receivedRadio(msg);
	}
}
