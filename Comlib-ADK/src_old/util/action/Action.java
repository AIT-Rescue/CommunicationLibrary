package comlib.adk.util.action;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKMove;
import rescuecore2.standard.messages.AKRest;
import rescuecore2.worldmodel.EntityID;

import java.util.List;

public class Action {

    private static Message move(EntityID id, int time, List<EntityID> path) {
        return new AKMove(id, time, path);
    }

    public static Message move(Tactics tactics, int time, List<EntityID> path) {
        return move(tactics.agentID, time, path);
    }

    private static Message move(EntityID id, int time, List<EntityID> path, int destX, int destY) {
        return new AKMove(id, time, path, destX, destY);
    }

    public static Message move(Tactics tactics, int time, List<EntityID> path, int destX, int destY) {
        return move(tactics.agentID, time, path, destX, destY);
    }

    private static Message rest(EntityID id, int time) {
        return new AKRest(id, time);
    }

    public static Message rest(Tactics tactics, int time) {
        return rest(tactics.agentID, time);
    }
}