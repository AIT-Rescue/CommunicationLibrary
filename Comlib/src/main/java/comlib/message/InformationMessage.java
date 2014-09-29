package comlib.message;

import comlib.manager.MessageManager;

public abstract class InformationMessage extends CommunicationMessage
{
	public CommunicationMessage(int id, int t)
	{
		super(id, t);
	}
	
	public CommunicationMessage(int id, int t, int l)
	{
		super(id, t, l);
	}
}