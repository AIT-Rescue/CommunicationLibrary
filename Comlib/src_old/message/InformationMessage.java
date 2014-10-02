package comlib.message;

public class InformationMessage extends CommunicationMessage
{
	public InformationMessage(int id, int t)
	{
		super(id, t);
	}
	
	public InformationMessage(int id, int t, int l)
	{
		super(id, t, l);
	}
}