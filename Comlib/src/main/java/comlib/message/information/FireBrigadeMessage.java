package comlib.message;

import comlib.message.HumanMessage;

import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;


abstract public class FireBrigadeMessage extends HumanMessage
{
	private int fireBrigadeWater;

	public FireBrigadeMessage(FireBrigade fireBrigade)
	{
		super(MessageID.fireBrigadeMessage, (Human)fireBrigade);
		fireBrigadeWater = fireBrigade.getWater();
	}

	public FireBrigadeMessage(int time, int ttl, EntityID id, int hp, int buriedness, int damage, EntityID positioni, int water)
	{
		super(MessageID.fireBrigadeMessage, time, ttl, id, hp, buriedness, damage, position);
		this.fireBrigadeWater = water;
	}
}

