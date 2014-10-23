package comlib.event.information;

import comlib.event.MessageEvent;
import comlib.message.information.BuildingMessage;


public abstract class BuildingMessageEvent extends MessageEvent<BuildingMessage>
{
	public void receivedRadio(BuildingMessage msg)
	{
		// You cannot write here.
	}

	public void receivedVoice(BuildingMessage msg)
	{
		// You cannot write here.
		// This code is default handler.
		this.receivedRadio(msg);
	}
}
