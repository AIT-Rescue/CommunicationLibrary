package comlib.event;

import comlib.message.AmbulanceTeamMessage;

public abstract class AmbulanceTeamMessageEvent extends MessageEvent<CivilianMessage> {

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
