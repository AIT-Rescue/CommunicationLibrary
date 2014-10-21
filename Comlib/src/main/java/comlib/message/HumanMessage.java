package comlib.message;

import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;


public abstract class HumanMessage extends InformationMessage
{

	protected EntityID humanID;
	protected int humanHP;
	protected int humanBuriedness;
	protected int humanDamage;
	protected EntityID humanPosition;

	public HumanMessage(int messageID, Human human)
	{
		super(messageID);
		humanID = human.getID();
		humanHP = human.getHP();
		humanBuriedness = human.getBuriedness();
		humanDamage = human.getDamage();
		humanPosition = human.getPosition();
	}

	public HumanMessage(int messageID, int time, int ttl, int hp, int buriedness, int damage, EntityID position, EntityID id)
	{
		super(messageID, time, ttl);
		humanHP = hp;
		humanBuriedness = buriedness;
		humanDamage = damage;
		humanPosition = position;
		humanID = id;
	}

	public EntityID getHumanID() { return this.humanID; }

	public int getHP() { return this.humanHP; }

	public int getBuriedness() { return this.humanBuriedness; }

	public int getDamage() { return this.humanDamage; }

	public EntityID getPosition() { return this.humanPosition; }
}

