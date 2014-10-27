package comlib.adk.team.tactics.straight;

import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.util.action.FireAction;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.target.BuildingSelector;
import comlib.manager.MessageManager;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public abstract class StraightFire extends FireBrigadeTactics {

    public BuildingSelector buildingSelector;

    public RouteSearcher routeSearcher;

    @Override
    public void preparation() {
        this.buildingSelector = this.getBuildingSelector();
        this.routeSearcher = this.getRouteSearcher();
    }

    public abstract BuildingSelector getBuildingSelector();

    public abstract RouteSearcher getRouteSearcher();

    @Override
    public void registerEvent(MessageManager manager) {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        return FireAction.rest(this, time);
    }
}
