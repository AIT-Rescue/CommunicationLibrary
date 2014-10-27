package comlib.adk.team.tactics.straight.event;

import comlib.adk.team.tactics.straight.StraightFire;
import comlib.adk.util.target.BuildingSelector;
import comlib.event.information.BuildingMessageEvent;
import comlib.message.information.BuildingMessage;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardWorldModel;

public class StraightBuildingEvent implements BuildingMessageEvent{

    private StraightFire tactics;

    public StraightBuildingEvent(StraightFire straightFire) {
        this.tactics = straightFire;
    }

    @Override
    public void receivedRadio(BuildingMessage msg) {
        this.tactics.buildingSelector.add(this.reflectedMessage(this.tactics.model, msg));
    }

    @Override
    public void receivedVoice(BuildingMessage msg) {
        this.receivedRadio(msg);
    }

    public Building reflectedMessage(StandardWorldModel swm, BuildingMessage msg) {
        Building building = (Building) swm.getEntity(msg.getBuildingID());
        building.isFierynessDefined();
        building.isBrokennessDefined();
        building.setFieryness(msg.getFieryness());
        building.setBrokenness(msg.getBrokenness());

        return building;
    }
}
