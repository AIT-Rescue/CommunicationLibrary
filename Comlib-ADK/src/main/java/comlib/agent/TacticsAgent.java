package comlib.adk.agent;

public abstract class TacticsAgent<E extends StandardEntity> extends CommunicationAgent<E> {
    
    public Tactics tactics;
    
    public TacticsAgent(Tactics t) {
        super();
        this.tactics = t;
    }
    
    @Override
    public void postConnect() {
        super.postConnect();
        this.tactics.model = this.model; //this.tactics.setWorld(this.model);
        this.tactics.config = this.config; //this.tactics.setConfig(this.config);
        this.tactics.postConnect();
    }
    
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