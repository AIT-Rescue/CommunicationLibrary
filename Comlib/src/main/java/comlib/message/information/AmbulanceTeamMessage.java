package comlib.message.information;

import comlib.message.HumanMessage;
import comlib.message.MessageID;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;


public class AmbulanceTeamMessage extends HumanMessage
{
	public AmbulanceTeamMessage(AmbulanceTeam ambulanceTeam)
	{
		super(MessageID.ambulanceTeamMessage, (Human)ambulanceTeam);
	}

	public AmbulanceTeamMessage(int time, int ttl, EntityID id, int hp, int buriedness, int damage, EntityID position)
	{
		super(MessageID.ambulanceTeamMessage, time, ttl, id, hp, buriedness, damage, position);
	}
}

