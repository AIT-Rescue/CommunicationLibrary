package comlib.InformationMessages;

import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;

import comlib.message.BitStreamReader;
import comlib.message.EntityType;
import comlib.message.MessagePolicy;
import comlib.message.MessageType;

public class BlockadeInformationMessage extends InformationMessage implements WorldInformationMessage {
	private EntityID id;
	private EntityID position;
	private int repaircost;

	private final int SIZE_OF_RepairCost = 32;

	public BlockadeInformationMessage(MessagePolicy policy, BitStreamReader bsr) throws Exception{
		super(MessageType.BlockadeInformationMessage, policy, bsr);
		this.id = policy.decordeID(EntityType.Blockade, bsr.getBits(policy.SIZE_OF_EntityID));
		this.position = policy.decordeID(EntityType.Position, bsr.getBits(policy.SIZE_OF_EntityID));
		this.repaircost = bsr.getBits(this.SIZE_OF_RepairCost);
	}

	public BlockadeInformationMessage(MessagePolicy policy, int time, Blockade blockade) {
		super(MessageType.BlockadeInformationMessage, policy, time);
		this.id = blockade.getID();
		this.position = blockade.getPosition();
		this.repaircost = blockade.getRepairCost();
	}

	public EntityID getID() {
		return this.id;
	}

	public EntityID getPosition() {
		return this.position;
	}

	public int getRepairCost() {
		return this.repaircost;
	}

	@Override
	public void createMessage() {
		super.createMessage();
		this.bos.writeBits(policy.encordeID(EntityType.Blockade, id), policy.SIZE_OF_EntityID);
		this.bos.writeBits(policy.encordeID(EntityType.Position, id), policy.SIZE_OF_EntityID);
		this.bos.writeBits(repaircost, this.SIZE_OF_RepairCost);
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "BlockadeID;" + this.id + " ";
		str += "Position:" + this.position + " ";
		str += "RepairCost:" + this.repaircost + " ";
		return str;
	}
	
	@Override
	public void reflectedMessage(StandardWorldModel world) {
		Blockade blockade = (Blockade) world.getEntity(this.getID());
		if (blockade == null) {
			world.addEntity(new Blockade(this.getID()));
			blockade = (Blockade) world.getEntity(this.getID());
		}
		blockade.isPositionDefined();
		blockade.isRepairCostDefined();
		blockade.setPosition(this.getID());
		blockade.setRepairCost(this.getRepairCost());
	}

}
