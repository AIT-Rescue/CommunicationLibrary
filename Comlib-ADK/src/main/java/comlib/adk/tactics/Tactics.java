package comlib.adk.tactics;

import comlib.adk.tactics.preparation.Preparation;
import comlib.adk.tactics.thinking.Thinking;
import rescuecore2.config.Config;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.ChangeSet;

public class Tactics {
    public Preparation preparation;
    public Thinking thinking;

    public StandardWorldModel model;
    public Config config;

    public Tactics(Preparation p, Thinking t) {
        this.preparation = p;
        this.thinking = t;
    }

    public void postConnect()
    {
        this.preparation.start();
    }

    public Message think(int time, ChangeSet changed)
    {
        return this.thinking.start(time, changed);
    }

    public void setWorld(StandardWorldModel swm) {
        this.model = swm;
    }

    public void setConfig(Config cfg) {
        this.config = cfg;
    }
}
