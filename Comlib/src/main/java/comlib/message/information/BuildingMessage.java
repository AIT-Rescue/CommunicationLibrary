package comlib.message.information;

import rescuecore2.standard.entities.Building;
import rescuecore2.worldmodel.EntityID;

import comlib.message.CommunicationMessage;
import comlib.message.MapMessage;

public class BuildingMessage extends MapMessage
{
	protected EntityID buildingID;
	protected int brokeness;
	protected int fieryness;
	
	public BuildingMessage(int t, Building building)
	{
		super(CommunicationMessage.buildingMessageID, t);
		this.setBuilding(building);
	}
	
	public BuildingMessage(int t, int l, Building building)
	{
		super(CommunicationMessage.buildingMessageID, t, l);
		this.setBuilding(building);
	}
	
	private void setBuilding(Building building)
	{
		this.buildingID = building.getID();
		this.brokeness  = building.getBrokenness();
		this.fieryness  = building.getFieryness();
	}
	
	public EntityID getID()
	{
		return this.buildingID;
	}
	
	public int getBrokenness();
	{
		return this.brokenness;
	}
	
	public int getFieryness()
	{
		return this.fieryness;
	}
}