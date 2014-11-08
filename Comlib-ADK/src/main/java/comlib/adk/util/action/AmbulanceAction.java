package comlib.adk.util.action;

import comlib.adk.team.tactics.AmbulanceTeamTactics;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKLoad;
import rescuecore2.standard.messages.AKRescue;
import rescuecore2.standard.messages.AKUnload;
import rescuecore2.worldmodel.EntityID;

public class AmbulanceAction extends Action {

    public static Message unload(EntityID id, int time) {
        return new AKUnload(id, time);
    }

    public static Message unload(AmbulanceTeamTactics tactics, int time) {
        return unload(tactics.agentID, time);
    }

    public static Message load(EntityID id, int time, EntityID target) {
        return new AKLoad(id, time, target);
    }

    public static Message load(AmbulanceTeamTactics tactics, int time, EntityID target) {
        return load(tactics.agentID, time, target);
    }

    public static Message rescue(EntityID id, int time, EntityID target) {
        return new AKRescue(id, time, target);
    }

    public static Message rescue(AmbulanceTeamTactics tactics, int time, EntityID target) {
        return rescue(tactics.agentID, time, target);
    }
}
