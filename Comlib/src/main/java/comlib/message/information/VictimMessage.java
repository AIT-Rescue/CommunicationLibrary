package comlib.message.information;

import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.Human;

import comlib.message.CommunicationMessage;

public class VictimMessage extends HumanMessage
{
	public VictimMessage(int t, Civilian victim)
	{
		super(CommunicationMessage.victimMessageID, t, victim);
	}
	
	public VictimMesage(int t, int l, Civilian victim)
	{
		super(CommunicationMessage.victimMessageID, t, l, victim);
	}
	
	public VictimMesage(int t, EntityID e, int h, int b, int d, EntityID p)
	{
		super(CommunicationMessage.victimMessageID, t, e, h, b, d, p);
	}
	
	public VictimMesage(int t, int l, EntityID e, int h, int b, int d, EntityID p)
	{
		super(CommunicationMessage.victimMessageID, t, l, e, h, b, d, p);
	}
}
