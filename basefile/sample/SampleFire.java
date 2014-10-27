package sample;

import comlib.adk.team.tactics.straight.StraightFire;
import comlib.adk.team.tactics.straight.event.StraightBuildingEvent;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.BuildingSelector;
import comlib.adk.util.target.sample.SampleBuildingSelector;
import comlib.manager.MessageManager;

public class SampleFire extends StraightFire {

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new StraightBuildingEvent(this));
    }

    @Override
    public BuildingSelector getBuildingSelector() {
        return new SampleBuildingSelector(this);
    }

    @Override
    public RouteSearcher getRouteSearcher() {
        return new SampleRouteSearcher(this);
    }
}
