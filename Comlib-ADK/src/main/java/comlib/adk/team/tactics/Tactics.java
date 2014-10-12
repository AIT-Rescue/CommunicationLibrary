package comlib.adk.team.tactics;

import comlib.adk.util.Action;
import comlib.manager.MessageManager;
import rescuecore2.config.Config;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

public class Tactics {
    public StandardWorldModel model;
    public Config config;
    public EntityID agentID;

    public Tactics() {

    }

    @Override
    public String toString() {
        return "Test";
    }

    public void postConnect() {

    }

    public void registerEvent(MessageManager manager) {
    }

    public Message think(int time, ChangeSet changed) {
        return Action.rest(this, time);
    }
}
