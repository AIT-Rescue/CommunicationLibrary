package comlib.adk.tactics;

import comlib.manager.MessageManager;
import rescuecore2.config.Config;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.ChangeSet;

public abstract class Tactics {
    //public Preparation preparation;
    //public Thinking thinking;
    
    public StandardWorldModel model;
    public Config config;
    
    public Tactics() {
    }
    
    /*public Tactics(Preparation p, Thinking t) {
        
        this.preparation = p;
        this.thinking = t;
    }*/
    
    public abstract String getTacticsName();
    
    public void postConnect() {
        //this.preparation.start();
    }
    
    public void registerEvent(MessageManager manager) {
    }
    
    public Message think(int time, ChangeSet changed) {
        //return this.thinking.start(time, changed);
        return null;
    }
    
    /*public void setWorld(StandardWorldModel swm) {
        this.model = swm;
    }
    
    public void setConfig(Config cfg) {
        this.config = cfg;
    }*/
}