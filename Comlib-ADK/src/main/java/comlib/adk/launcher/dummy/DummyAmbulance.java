package comlib.adk.launcher.dummy;

import comlib.adk.team.tactics.straight.StraightAmbulance;
import comlib.adk.team.tactics.straight.event.StraightAmbulanceEvent;
import comlib.adk.team.tactics.straight.event.StraightCivilianEvent;
import comlib.adk.team.tactics.straight.event.StraightFireEvent;
import comlib.adk.team.tactics.straight.event.StraightPoliceEvent;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.VictimSelector;
import comlib.adk.util.target.sample.SampleVictimSelector;
import comlib.manager.MessageManager;

public class DummyAmbulance extends StraightAmbulance {

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new StraightCivilianEvent(this));
        manager.registerEvent(new StraightAmbulanceEvent(this));
        manager.registerEvent(new StraightFireEvent(this));
        manager.registerEvent(new StraightPoliceEvent(this));
    }

    @Override
    public VictimSelector getVictimSelector() {
        if(this.victimSelector == null) {
            this.victimSelector = new SampleVictimSelector(this);
        }
        return this.victimSelector;
    }

    @Override
    public RouteSearcher getRouteSearcher() {
        if(this.routeSearcher == null) {
            this.routeSearcher = new SampleRouteSearcher(this);
        }
        return this.routeSearcher;
    }
}