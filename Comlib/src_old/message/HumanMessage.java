package comlib.message;

import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;

public abstract class HumanMessage extends InformationMessage
{
	protected EntityID humanID;
	protected int hp;
	protected int buriedness;
	protected int damage;
	protected EntityID position;
	
	public HumanMessage(int id, int t, Human human)
	{
		super(id, t);
		this.setHuman(human);
	}
	
	public HumanMessage(int id, int t, int l, Human human)
	{
		super(id, t, l);
		this.setHuman(human);
	}
	
	public HumanMessage(int id, int t, EntityID e, int h, int b, int d, EntityID p)
	{
		super(id, t);
		this.humanID    = e;
		this.hp         = h;
		this.buriedness = b;
		this.damage     = d;
		this.position   = p;
	}
	
	public HumanMessage(int id, int t, int l, EntityID e, int h, int b, int d, EntityID p)
	{
		super(id, t, l);
		this.humanID    = e;
		this.hp         = h;
		this.buriedness = b;
		this.damage     = d;
		this.position   = p;
	}
	
	private void setHuman(Human human)
	{
		this.humanID    = human.getID();
		this.hp         = human.getHP();
		this.buriedness = human.getBuriedness();
		this.damage     = human.getDamage();
		this.position   = human.getPosition();
	}
	
	public abstract Class<? extends Human> getHumanClass();
	
	public EntityID getID()
	{
		return this.humanID;
	}
	
	public int getHP()
	{
		return this.hp;
	}
	
	public int getBuriedness()
	{
		return this.buriedness;
	}
	
	public int getDamage()
	{
		return this.damage;
	}
	
	public EntityID getPosition()
	{
		return this.position;
	}
}
