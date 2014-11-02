package comlib.adk.team.tactics.straight.event;

import comlib.adk.team.tactics.straight.StraightPolice;
import comlib.event.information.RoadMessageEvent;
import comlib.message.information.RoadMessage;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.StandardWorldModel;

public class StraightRoadEvent implements RoadMessageEvent {

    private StraightPolice tactics;

    public StraightRoadEvent(StraightPolice straightPolice) {
        this.tactics = straightPolice;
    }

    @Override
    public void receivedRadio(RoadMessage msg) {
        Blockade blockade = this.reflectedMessage(this.tactics.model, msg);
        this.tactics.blockadeSelector.add(blockade);
    }

    @Override
    public void receivedVoice(RoadMessage msg) {
        this.receivedRadio(msg);
    }

    public Blockade  reflectedMessage(StandardWorldModel world, RoadMessage msg) {
        Blockade blockade = (Blockade) world.getEntity(msg.getID());
        if (blockade == null) {
            world.addEntity(new Blockade(msg.getID()));
            blockade = (Blockade) world.getEntity(msg.getID());
        }
        blockade.isPositionDefined();
        blockade.isRepairCostDefined();
        blockade.setPosition(msg.getPosition());
        blockade.setRepairCost(msg.getRepairCost());

        return blockade;
    }
}
