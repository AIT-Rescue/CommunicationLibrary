package comlib.adk.util.target;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.standard.entities.Building;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.List;

public abstract class BuildingSelector extends TargetSelector {

    public List<EntityID> buildingList;

    public BuildingSelector(Tactics user) {
        super(user);
        this.buildingList = new ArrayList<>();
    }

    public abstract void add(Building building);

    public abstract void remove(Building building);
}