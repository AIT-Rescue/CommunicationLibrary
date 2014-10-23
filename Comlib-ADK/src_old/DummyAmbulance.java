package comlib.adk.launcher.dummy;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.util.action.AmbulanceAction;

public class DummyAmbulance extends AmbulanceTeamTactics {

    private AmbulanceStatus status;

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        switch (status) {
            case AmbulanceStatus.TARGET_ON_BOARD:
                return this.thinkTargetOnBoard();
            default:
                return AmbulanceAction.rest(this, time);

        }
    }

    public Message thinkTargetOnBoard() {
        if(this.someoneOnBoard()) {
            //LOAD_TARGET_REFUGE
            if (this.location instanceof Refuge) {
                this.rescueTarget = null;
                return AmbulanceAction.unload(this, time);
            }
            else {
                //LOAD_TARGET_MOVE
                return this.moveRefuge(time);
            }
        }
    }

    /*private boolean someoneOnBoard() {
        for (StandardEntity next : this.model.getEntitiesOfType(StandardEntityURN.CIVILIAN)) {
            if (((Human)next).getPosition().equals(this.agentID)) {
                return true;
            }
        }
        return false;
    }*/

    private boolean someoneOnBoard() {
        return this.rescueTarget != null && ((Human) this.model.getEntity(this.rescueTarget)).getPosition().equals(this.agentID);
    }
}