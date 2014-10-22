package comlib.adk.util.target;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.standard.entities.Building;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.List;

public abstract class BuildingManager extends TargetManager {

    public List<EntityID> buildingList;

    public BuildingManager(Tactics user) {
        super(user);
        this.buildingList = new ArrayList<>();
    }

    public abstract void add(Building building);

    public abstract void remove(Building building);
}