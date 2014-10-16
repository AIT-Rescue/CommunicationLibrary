package comlib.adk.agent;

import comlib.adk.team.tactics.Tactics;
import comlib.agent.CommunicationAgent;
import comlib.manager.MessageManager;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;

public abstract class TacticsAgent<T extends Tactics, E extends StandardEntity> extends CommunicationAgent<E> {
    
    public T tactics;
    
    public TacticsAgent(Tactics t) {
        super();
        this.tactics = (T)t;
    }
    
    @Override
    public void postConnect() {
        super.postConnect();
        this.tactics.random = this.random;
        this.tactics.model = this.model; //this.tactics.setWorld(this.model);
        this.tactics.config = this.config; //this.tactics.setConfig(this.config);
        //this.tactics.agentID = this.getID(); //AgentのEntityIDはかわるのか？？
        //this.tactics.me = this.me();
        //List<Refuge> refuges = this.getRefuges();
        //this.tactics.manager = this.manager;
        this.initAgentValue();
        this.tactics.postConnect();
    }
    
    public abstract void initAgentValue();

    @Override
    public void registerProvider(MessageManager manager) {
    }

    @Override
    public void registerEvent(MessageManager manager) {
        this.tactics.registerEvent(manager);
    }
    
    @Override
    public void think(int time, ChangeSet changed) {
        this.tactics.model = this.model; //this.tactics.setWorld(this.model);
        this.tactics.config = this.config; //this.tactics.setConfig(this.config);
        this.tactics.agentID = this.getID();
        this.tactics.me = this.me();
        this.tactics.location = this.location();
        this.send(this.tactics.think(time, changed, this.manager));
    }
}