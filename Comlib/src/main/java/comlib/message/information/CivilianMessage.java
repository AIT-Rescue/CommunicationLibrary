package comlib.message.information;

import comlib.message.HumanMessage;

import comlib.message.MessageID;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;


public class CivilianMessage extends HumanMessage
{
	public CivilianMessage(Civilian civilian)
	{
		super(MessageID.civilianMessage, (Human)civilian);
	}

	public CivilianMessage(int time, int ttl, int hp, int buriedness, int damage, EntityID position, EntityID id)
	{
		super(MessageID.civilianMessage, time, ttl, hp, buriedness, damage, position, id);
	}
}

