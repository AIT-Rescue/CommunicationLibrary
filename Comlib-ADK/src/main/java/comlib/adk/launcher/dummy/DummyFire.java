package comlib.adk.launcher.dummy;

import comlib.adk.team.tactics.straight.StraightFire;
import comlib.adk.team.tactics.straight.event.StraightBuildingEvent;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.BuildingSelector;
import comlib.adk.util.target.sample.SampleBuildingSelector;
import comlib.manager.MessageManager;

public class DummyFire extends StraightFire {

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new StraightBuildingEvent(this));
    }

    @Override
    public BuildingSelector getBuildingSelector() {
        if(this.buildingSelector == null) {
            this.buildingSelector = new SampleBuildingSelector(this);
        }
        return this.buildingSelector;
    }

    @Override
    public RouteSearcher getRouteSearcher() {
        if(this.routeSearcher == null) {
            this.routeSearcher = new SampleRouteSearcher(this);
        }
        return this.routeSearcher;
    }
}
