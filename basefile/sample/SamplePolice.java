package sample;

import comlib.adk.team.tactics.PoliceForceTactics;
import comlib.adk.util.action.PoliceAction;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class SamplePolice extends extends StraightPolice {

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
    }
}
