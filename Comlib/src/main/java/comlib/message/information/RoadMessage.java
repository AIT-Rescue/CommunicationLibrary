package comlib.message.information;

import comlib.message.MapMessage;
import comlib.message.MessageID;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.worldmodel.EntityID;

public class RoadMessage extends MapMessage {

    private EntityID id;
    private EntityID position;
    private int repairCost;
    private int[] apexes;
    private int x;
    private int y;

    public RoadMessage(Blockade blockade) {
        super(MessageID.roadMessage);
        this.id = blockade.getID();
        this.position = blockade.getPosition();
        this.repairCost = blockade.getRepairCost();
        this.apexes = blockade.getApexes();
        this.x = blockade.getX();
        this.y = blockade.getY();

    }

    public RoadMessage() {
        super(MessageID.roadMessage);
    }

    public EntityID getId() {
        return this.id;
    }

    public EntityID getPosition() {
        return this.position;
    }

    public int getRepairCost() {
        return this.repairCost;
    }

    public int[] getApexes() {
        return this.apexes;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
