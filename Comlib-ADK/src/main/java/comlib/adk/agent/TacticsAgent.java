package comlib.adk.agent;

import comlib.adk.team.tactics.Tactics;
import comlib.agent.CommunicationAgent;
import comlib.manager.MessageManager;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;

public abstract class TacticsAgent<T extends Tactics, E extends StandardEntity> extends CommunicationAgent<E> {
    
    public T tactics;
    private int ignoreAgentCommand;

    public TacticsAgent(T t) {
        super();
        this.tactics = t;
    }
    
    @Override
    public void postConnect() {
        super.postConnect();
        ignoreAgentCommand = config.getIntValue(kernel.KernelConstants.IGNORE_AGENT_COMMANDS_KEY);
        //set value
        this.tactics.random = this.random;
        this.tactics.model = this.model;
        this.tactics.config = this.config;
        this.tactics.agentID = this.getID(); //AgentのEntityIDはかわるのか？？
        this.tactics.location = this.location();
        this.setAgentEntity(this.tactics);
        this.setAgentUniqueValue(this.tactics);
        
        this.tactics.preparation();
    }
    
    public abstract void setAgentEntity(T t);
    
    public abstract void setAgentUniqueValue(T t);

    @Override
    public void registerProvider(MessageManager manager) {
        this.tactics.registerProvider(manager);
    }
    
    @Override
    public void registerEvent(MessageManager manager) {
        this.tactics.registerEvent(manager);
    }
    
    @Override
    public void thinkEvent(int time, ChangeSet changed) {
        this.send(this.tactics.think(time, changed, this.manager));
    }

    @Override
    public void receiveBeforeEvent(int time, ChangeSet changed) {
        //set value
        this.tactics.model = this.model;
        this.tactics.config = this.config;
        //this.tactics.agentID = this.getID();
        this.tactics.location = this.location();
        this.setAgentEntity(this.tactics);
    }

    @Override
    public void sendAfterEvent(int time, ChangeSet changed) {

    }
}