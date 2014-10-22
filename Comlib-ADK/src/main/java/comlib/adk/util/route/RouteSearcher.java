package comlib.adk.util.route;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;

import java.util.List;

public abstract class RouteSearcher {
    protected Tactics tactics;

    public RouteSearcher(Tactics user) {
        this.tactics = user;
    }

    public abstract List<EntityID> randomWalk();

    public abstract List<EntityID> getPath(int time, EntityID from, EntityID to);

    public List<EntityID> getPath(int time, Human from, EntityID to) {
        return this.getPath(time, from.getPosition(), to);
    }

    public List<EntityID> getPath(int time, Human from, Area to) {
        return this.getPath(time, from.getPosition(), to.getID());
    }

    public List<EntityID> getPath(int time, Human from, Blockade blockade) {
        return this.getPath(time, from.getPosition(), blockade.getPosition());
    }

    //public abstract List<EntityID> getPath(int time, EntityID from, List<EntityID> to);
}
