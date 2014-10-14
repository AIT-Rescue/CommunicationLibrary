package comlib.adk.util.action;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.action.Action;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKExtinguish;
import rescuecore2.worldmodel.EntityID;

public class FireAction extends Action {

    public static Message extinguish(EntityID id, int time, EntityID target, int maxPower) {
        return new AKExtinguish(id, time, target, maxPower);
    }

    public static Message extinguish(Tactics tactics, int time, EntityID target, int maxPower) {
        return extinguish(tactics.agentID, time, target, maxPower);
    }
}
