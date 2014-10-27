package comlib.adk.util.target.sample;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.route.RouteUtil;
import comlib.adk.util.target.BuildingSelector;
import rescuecore2.standard.entities.Building;
import rescuecore2.worldmodel.EntityID;

import java.util.HashSet;
import java.util.Set;

public class SampleBuildingSelector extends BuildingSelector {

    public Set<EntityID> buildingList;

    public SampleBuildingSelector(Tactics user) {
        super(user);
        this.buildingList = new HashSet<>();
    }

    @Override
    public void add(Building building) {
        if (building.isOnFire()) {
            this.buildingList.add(building.getID());
        }
        else {
            this.buildingList.remove(building.getID());
        }
        //if(building.getFieryness() > 0) {
        //}
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
