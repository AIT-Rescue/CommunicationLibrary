package comlib.adk.agent;

import comlib.adk.tactics.Tactics;
import comlib.agent.CommunicationAgent;
import comlib.agent.MessageEvent;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;

public abstract class TacticsAgent<E extends StandardEntity> extends CommunicationAgent<E>
{
    public Tactics tactics;

    public TacticsAgent(Tactics t){
        super();
        this.tactics = t;
    }

    /*@Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return null;
    }*/

    @Override
    public MessageEvent getMessageEvent() {
        return null;
    }

    @Override
    public void postConnect() {
        super.postConnect();
        this.tactics.postConnect();
    }

    @Override
    public void think(int time, ChangeSet changed) {
        this.send(this.tactics.think());
    }
}