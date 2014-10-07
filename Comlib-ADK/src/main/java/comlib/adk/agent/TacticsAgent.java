package comlib.adk.agent;

import comlib.adk.tactics.Tactics;
import comlib.agent.CommunicationAgent;
import comlib.manager.MessageManager;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;

public abstract class TacticsAgent<T extends Tactics, E extends StandardEntity> extends CommunicationAgent<E> {
    
    public T tactics;
    
    public TacticsAgent(Tactics t) {
        super();
        this.tactics = t;
    }
    
    @Override
    public void postConnect() {
        super.postConnect();
        this.tactics.model = this.model; //this.tactics.setWorld(this.model);
        this.tactics.config = this.config; //this.tactics.setConfig(this.config);
        //this.
        this.tactics.postConnect();
    }
    
    public abstract void 
    
    @Override
    public void registerEvent(MessageManager manager) {
        this.tactics.registerEvent(manager);
    }
    
    @Override
    public void think(int time, ChangeSet changed) {
        this.tactics.model = this.model; //this.tactics.setWorld(this.model);
        this.tactics.config = this.config; //this.tactics.setConfig(this.config);
        this.send(this.tactics.think(time, changed));
    }
}