package sample;

import comlib.adk.team.tactics.straight.StraightPolice;
import comlib.adk.team.tactics.straight.event.StraightRoadEvent;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.BlockadeSelector;
import comlib.adk.util.target.sample.SampleBlockadeSelector;
import comlib.manager.MessageManager;

public class SamplePolice extends StraightPolice {

    @Override
    public BlockadeSelector getBlockadeSelector() {
        return new SampleBlockadeSelector(this);
    }

    @Override
    public RouteSearcher getRouteSearcher() {
        return new SampleRouteSearcher(this);
    }

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new StraightRoadEvent(this));
    }
}
