package comlib.message;

import comlib.message.HumanMessage;

import rescuecore2.standard.entities.Victim;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;


abstract public class VictimMessage extends HumanMessage
{
	public VictimMessage(Victim victim)
	{
		super(MessageID.victimMessage, (Human)victim);
	}

	public VictimMessage(int time, int ttl, EntityID id, int hp, int buriedness, int damage, EntityID position)
	{
		super(MessageID.victimMessage, time, ttl, id, hp, buriedness, damage, position);
	}
}

