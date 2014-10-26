package sample.event;

import comlib.adk.util.target.BuildingManager;
import comlib.event.information.BuildingMessageEvent;
import comlib.message.information.BuildingMessage;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardWorldModel;

public class SampleBuildingEvent implements BuildingMessageEvent{

    private StandardWorldModel model;
    private BuildingManager buildingManager;

    public SampleBuildingEvent(StandardWorldModel swm, BuildingManager bm) {
        this.model = swm;
        this.buildingManager = bm;
    }

    @Override
    public void receivedRadio(BuildingMessage msg) {
        this.buildingManager.add(this.reflectedMessage(this.model, msg));
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
