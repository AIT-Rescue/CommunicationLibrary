package comlib.adk.dummy;

import comlib.adk.team.tactics.PoliceForceTactics;
import comlib.adk.util.action.Action;
import comlib.adk.util.action.PoliceAction;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class DummyPolice extends PoliceForceTactics {

    @Override
    public void postConnect() {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        manager.addSendMessage(new DummyMessage(time, 10, 0));
        return PoliceAction.rest(this, time);
    }

    @Override
    public String toString() {
        return "dummy";
    }
}
