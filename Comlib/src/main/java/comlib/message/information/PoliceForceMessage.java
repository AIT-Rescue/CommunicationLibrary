package comlib.message.information;

import comlib.message.HumanMessage;
import comlib.message.MessageID;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.worldmodel.EntityID;


abstract public class PoliceForceMessage extends HumanMessage
{
	public PoliceForceMessage(PoliceForce policeForce)
	{
		super(MessageID.policeForceMessage, (Human)policeForce);
	}

	public PoliceForceMessage(int time, int ttl, EntityID id, int hp, int buriedness, int damage, EntityID position)
	{
		super(MessageID.policeForceMessage, time, ttl, id, hp, buriedness, damage, position);
	}
}

