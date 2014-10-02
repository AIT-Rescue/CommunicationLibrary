package comlib.adk.agent;

import comlib.adk.tactics.Tactics;
import comlib.agent.CommunicationAgent;
import rescuecore2.standard.entities.StandardEntity;

public abstract class TacticsAgent<E extends StandardEntity> extends CommunicationAgent<E>
{
    public Tactics tactics;

    private TacticsAgent() {
        super();
    }

    public TacticsAgent(Tactics t){
        this();
        this.tactics = t;
    }
}