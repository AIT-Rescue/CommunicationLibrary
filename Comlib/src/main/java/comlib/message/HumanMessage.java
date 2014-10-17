package comlib.message;

import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;

abstract public class HumanMessage extends CommunicationMessage {

	protected EntityID humanID;
	protected int HP;
	protected int humanBuriedness;
	protected int humanDamage;
	protected EntityID humanPosition;

	public DummyMessage(int messageID, int test) {
		super(messageID);
		dummyTest = test;
	}

	public DummyMessage(int messageID, int time, int ttl, int test) {
		super(messageID, time, ttl);
		dummyTest = test;
	}

	public int getValue() {
		return this.dummyTest;
	}
}

