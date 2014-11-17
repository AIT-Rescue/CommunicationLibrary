package comlib.adk.launcher.dummy;

import comlib.adk.team.tactics.basic.BasicFire;
import comlib.adk.team.tactics.basic.event.BasicBuildingEvent;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.serversample.ServerSampleRouteSearcher;
import comlib.adk.util.target.BuildingSelector;
import comlib.adk.util.target.sample.SampleBuildingSelector;
import comlib.manager.MessageManager;

public class DummyFire extends BasicFire {

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new BasicBuildingEvent(this));
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
            this.routeSearcher = new ServerSampleRouteSearcher(this);
        }
        return this.routeSearcher;
    }
}
