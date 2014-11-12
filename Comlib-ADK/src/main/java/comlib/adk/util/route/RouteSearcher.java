package comlib.adk.util.route;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.Human;
import rescuecore2.worldmodel.EntityID;

import java.util.List;

public abstract class RouteSearcher implements IRouteSearcher {
    protected Tactics tactics;

    public RouteSearcher(Tactics user) {
        this.tactics = user;
    }

    @Override
    public abstract List<EntityID> noTargetWalk();

    @Override
    public abstract List<EntityID> getPath(int time, EntityID from, EntityID to);

    public void a() {
        Area area = (Area)this.tactics.model.getEntity(((Human)this.tactics.me()).getPosition());
        List<EntityID> path = this.getPath(1, this.tactics.agentID, area);
    }
}
