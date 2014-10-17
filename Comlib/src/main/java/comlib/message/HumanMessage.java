package comlib.message;

import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;

abstract public class HumanMessage extends CommunicationMessage {

	protected EntityID humanID;
	protected int humanHP;
	protected int humanBuriedness;
	protected int humanDamage;
	protected EntityID humanPosition;

	public DummyMessage(int messageID, Human human) {
		super(messageID);
		humanID = human.getID();
		humanHP = human.getHP();
		humanBuriedness = humanBuriedness();
		humanDamage = humanDamage();
		humanPosition = human.getPosition();
	}

	public DummyMessage(int messageID, int time, int ttl, EntityID id, int hp, int buriedness, int damage, EntityID position) {
		super(messageID, time, ttl);
		humanID = id;
		humanHP = hp;
		humanBuriedness = buriedness;
		humanDamage = damage;
		humanPosition = position;
	}

	public int getValue() {
		return this.dummyTest;
	}

}

