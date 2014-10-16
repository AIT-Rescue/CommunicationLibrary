package comlib.adk.dummy;

import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.util.action.FireAction;
import comlib.manager.MessageManager;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class DummyFire extends FireBrigadeTactics {
    @Override
    public void postConnect() {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        return FireAction.rest(this, time);
    }

    @Override
    public String toString() {
        return "dummy";
    }
}
