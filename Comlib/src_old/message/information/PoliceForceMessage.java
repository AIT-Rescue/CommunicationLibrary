package comlib.message.information;

import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.PoliceForce;

import comlib.message.CommunicationMessage;
import comlib.message.HumanMessage;

public class PoliceForceMessage extends HumanMessage
{
	public PoliceForceMessage(int t, PiliceForce police)
	{
		super(CommunicationMessage.policeForceMessageID, t, (Human)police);
	}
	
	public PoliceForceMessage(int t, int l, PoliceForce police)
	{
		super(CommunicationMessage.policeForceMessageID, t, l, (Human)police);
	}
}
