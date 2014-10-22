package comlib.adk.util.target.sample;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.route.RouteUtil;
import comlib.adk.util.target.BuildingManager;
import rescuecore2.standard.entities.Building;
import rescuecore2.worldmodel.EntityID;

public class SampleBuildingManager extends BuildingManager{

    public SampleBuildingManager(Tactics user) {
        super(user);
    }

    @Override
    public void add(Building building) {
        this.buildingList.add(building.getID());
    }

    @Override
    public void remove(Building building) {
        this.buildingList.remove(building.getID());
    }

    @Override
    public EntityID getTarget(int time) {
        EntityID result = null;
        int minDistance = Integer.MAX_VALUE;
        for (EntityID id : this.buildingList) {
            int d = RouteUtil.distance(this.tactics.model, this.tactics.me, this.tactics.model.getEntity(id));
            if (minDistance >= d) {
                minDistance = d;
                result = id;
            }
        }
        return result;
    }
}
