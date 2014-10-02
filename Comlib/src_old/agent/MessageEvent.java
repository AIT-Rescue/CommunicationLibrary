package comlib.agent;

import comlib.message.information.AmbulanceMessage;
import comlib.message.information.BlockadeMessage;
import comlib.message.information.BuildingMessage;
import comlib.message.information.FireBrigadeMessage;
import comlib.message.information.PoliceForceMessage;
import comlib.message.information.VictimMessage;

public interface MessageEvent
{
	//World Message
	public void receiveMessage(BuildingMessage msg);
	public void receiveMessage(BlockadeMessage msg);
	
	//Human Message
	public void receiveMessage(AmbulanceMessage msg);
	public void receiveMessage(FireBrigadeMessage msg);
	public void receiveMessage(PoliceForceMessage msg);
	public void receiveMessage(VictimMessage msg);
}