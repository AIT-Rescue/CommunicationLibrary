package comlib.event.information;

import comlib.event.MessageEvent;
import comlib.message.information.AmbulanceTeamMessage;


public abstract class AmbulanceTeamMessageEvent extends MessageEvent<AmbulanceTeamMessage>
{
	public void receivedRadio(AmbulanceTeamMessage msg)
	{
		// You cannot write here.
	}

	public void receivedVoice(AmbulanceTeamMessage msg)
	{
		// You cannot write here.
		// This code is default handler.
		this.receivedRadio(msg);
	}
}
