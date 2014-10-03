package comlib.adk.agent;

import comlib.adk.tactics.Tactics;
import comlib.agent.CommunicationAgent;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;

public abstract class TacticsAgent<E extends StandardEntity> extends CommunicationAgent<E>
{
    public Tactics tactics;

    public TacticsAgent(Tactics t) {
        super();
        this.tactics = t;
    }

    @Override
    public void postConnect() {
        super.postConnect();
        this.tactics.setWorld(this.model);
        this.tactics.setConfig(this.config);
        this.tactics.postConnect();
    }

    @Override
    public void think(int time, ChangeSet changed) {
        this.tactics.setWorld(this.model);
        this.tactics.setConfig(this.config);
        this.send(this.tactics.think(time, changed));
    }
}