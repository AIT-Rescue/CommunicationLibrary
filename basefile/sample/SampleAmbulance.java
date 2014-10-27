package sample;

import comlib.adk.team.tactics.straight.StraightAmbulance;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.VictimSelector;
import comlib.adk.util.target.sample.SampleVictimSelector;

public class SampleAmbulance extends StraightAmbulance {

    @Override
    public VictimSelector getVictimSelector() {
        return new SampleVictimSelector(this);
    }

    @Override
    public RouteSearcher getRouteSearcher() {
        return new SampleRouteSearcher(this);
    }
}
