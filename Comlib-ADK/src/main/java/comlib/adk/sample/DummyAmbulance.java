package comlib.adk.sample;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.util.action.AmbulanceAction;
import comlib.manager.MessageManager;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class DummyAmbulance extends AmbulanceTeamTactics {
    @Override
    public void postConnect() {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        return AmbulanceAction.rest(this, time);
    }
}
