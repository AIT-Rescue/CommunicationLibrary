package comlib.adk.agent;

import comlib.adk.team.tactics.Tactics;
import comlib.agent.CommunicationAgent;
import comlib.manager.MessageManager;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;

public abstract class TacticsAgent<T extends Tactics, E extends StandardEntity> extends CommunicationAgent<E> {
    
    public T tactics;
    
    public TacticsAgent(T t) {
        super();
        this.tactics = t;
    }
    
    @Override
    public void postConnect() {
        super.postConnect();
        this.initValue();
        this.tactics.postConnect();
    }

    public void initValue() {
        this.tactics.random = this.random;
        this.tactics.model = this.model; //this.tactics.setWorld(this.model);
        this.tactics.config = this.config; //this.tactics.setConfig(this.config);
        //this.tactics.agentID = this.getID(); //AgentのEntityIDはかわるのか？？
        //this.tactics.me = this.me();
        //this.tactics.refuges = this.getRefuges();
        this.initAgentValue(this.tactics);
    }
    
    public abstract void initAgentValue(T t);

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

        //this.tactics.setMe(this.me());
        //this.tactics.me = this.me();
        this.setAgent();

        this.tactics.location = this.location();
        this.send(this.tactics.think(time, changed, this.manager));
    }

    public abstract void setAgent();


}