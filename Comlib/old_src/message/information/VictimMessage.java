package comlib.message.information;

import comlib.message.CommunicationMessage;
import comlib.message.HumanMessage;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;

public class VictimMessage extends HumanMessage
{
	public VictimMessage(int t, Civilian victim)
	{
		super(CommunicationMessage.victimMessageID, t, victim);
	}
	
	public VictimMessage(int t, int l, Civilian victim)
	{
		super(CommunicationMessage.victimMessageID, t, l, victim);
	}
	
	public VictimMessage(int t, EntityID e, int h, int b, int d, EntityID p)
	{
		super(CommunicationMessage.victimMessageID, t, e, h, b, d, p);
	}
	
	public VictimMessage(int t, int l, EntityID e, int h, int b, int d, EntityID p)
	{
		super(CommunicationMessage.victimMessageID, t, l, e, h, b, d, p);
	}

    @Override
    public Class<? extends Human> getHumanClass() {
        return Civilian.class;
    }
}
