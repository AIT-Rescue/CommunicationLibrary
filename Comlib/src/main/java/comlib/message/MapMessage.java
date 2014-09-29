package comlib.message;

import comlib.message.InformationMessage;

public class MapMessage extends InformationMessage
{
	public MapMessage(int id, int t)
	{
		super(id, t);
	}
	
	public MapMessage(int id, int t, int l)
	{
		super(id, t, l);
	}
}