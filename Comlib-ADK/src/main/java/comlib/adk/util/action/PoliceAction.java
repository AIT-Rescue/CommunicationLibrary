package comlib.adk.util.action;

import comlib.adk.team.tactics.PoliceForceTactics;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKClear;
import rescuecore2.standard.messages.AKClearArea;
import rescuecore2.worldmodel.EntityID;

public class PoliceAction extends Action {

    public static Message clear(EntityID id, int time, EntityID target) {
        return new AKClear(id, time, target);
    }

    public static Message clear(PoliceForceTactics tactics, int time, EntityID target) {
        return clear(tactics.agentID, time, target);
    }

    public static Message clear(EntityID id, int time, int destX, int destY) {
        return new AKClearArea(id, time, destX, destY);
    }

    public static Message clear(PoliceForceTactics tactics, int time, int destX, int destY) {
        return clear(tactics.agentID, time, destX, destY);
    }
}
