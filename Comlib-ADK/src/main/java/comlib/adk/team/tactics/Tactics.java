package comlib.adk.team.tactics;

import comlib.manager.MessageManager;
import rescuecore2.config.Config;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

import java.util.Random;

public abstract class Tactics<E extends StandardEntity> {
    public StandardWorldModel model;
    public Config config;
    public EntityID agentID;
    public StandardEntity location;
    public E me;

    public EntityID targetID;

    public Random random;

    public abstract void postConnect();

    public abstract void registerEvent(MessageManager manager);

    public abstract Message think(int time, ChangeSet changed, MessageManager manager);

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
