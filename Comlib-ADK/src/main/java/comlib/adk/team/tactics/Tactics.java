package comlib.adk.team.tactics;

import comlib.adk.util.action.Action;
import comlib.manager.MessageManager;
import rescuecore2.config.Config;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

import java.util.Random;

public class Tactics<E extends StandardEntity> {
    public StandardWorldModel model;
    public Config config;
    public EntityID agentID;
    public StandardEntity location;
    public E me;
    //public MessageManager manager;

    public Random random;

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

    public Message think(int time, ChangeSet changed, MessageManager manager) {
        return Action.rest(this, time);
    }

    public StandardEntity location() {
        return this.location;
    }

    public EntityID getID() {
        return this.agentID;
    }

    public E me() {
        return this.me;
    }
}
