package comlib.message.information;

import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.Human;

import comlib.message.CommunicationMessage;
import comlib.message.HumanMessage;

public class FireBrigadeMessage
{
	protected int water;
	
	public FireBrigadeMessage(int t, FireBrigade fire)
	{
		super(CommunicationMessage.fireBrigadeMessageID, t, (Human)fire);
		this.water = fire.getWater();
	}
	
	public FireBrigadeMessage(int t, l, FireBrigade fire)
	{
		super(CommunicationMessage.fireBrigadeMessageID, t, l, (Human)fire);
		this.water = fire.getWater();
	}
	
	public int getWater()
	{
		return this.water;
	}
}