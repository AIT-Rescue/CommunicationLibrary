package comlib.adk.dummy;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.util.action.AmbulanceAction;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class DummyAmbulance extends AmbulanceTeamTactics {
    @Override
    public void postConnect() {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        manager.addSendMessage(new DummyMessage(time, 10, 0));
        return AmbulanceAction.rest(this, time);
    }

    @Override
    public String toString() {
        return "dummy";
    }
}
