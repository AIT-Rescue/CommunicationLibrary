package comlib.message.information;

import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Human;

import comlib.message.CommunicationMessage;
import comlib.message.HumanMessage;

public class AmbulanceTeamMessage extends HumanMessage
{
	public AmbulanceTeamMessage(int t, AmbulanceTeam ambulance)
	{
		super(CommunicationMessage.ambulanceTeamMessageID, t, (Human)ambulance);
	}
	
	public AmbulanceTeamMessage(int t, int l, AmbulanceTeam ambulance)
	{
		super(CommunicationMessage.ambulanceTeamMessageID, t, l, (Human)ambulance);
	}
}