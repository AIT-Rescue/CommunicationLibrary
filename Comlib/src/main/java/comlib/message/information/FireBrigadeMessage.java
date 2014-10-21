package comlib.message.information;

import comlib.message.HumanMessage;
import comlib.message.MessageID;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;


public class FireBrigadeMessage extends HumanMessage
{
	private int fireBrigadeWater;

	public FireBrigadeMessage(FireBrigade fireBrigade)
	{
		super(MessageID.fireBrigadeMessage, (Human)fireBrigade);
		fireBrigadeWater = fireBrigade.getWater();
	}

	public FireBrigadeMessage(int time, int ttl, EntityID id, int hp, int buriedness, int damage, EntityID position, int water)
	{
		super(MessageID.fireBrigadeMessage, time, ttl, hp, buriedness, damage, position, id);
		this.fireBrigadeWater = water;
	}

	public int getWater() { return this.fireBrigadeWater; }
}

