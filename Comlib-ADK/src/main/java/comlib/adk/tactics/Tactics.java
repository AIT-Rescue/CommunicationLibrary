package comlib.adk.tactics;

import comlib.adk.tactics.preparation.Preparation;
import comlib.adk.tactics.thinking.Thinking;
import rescuecore2.messages.Message;

public class Tactics {
    public Preparation preparation;
    public Thinking thinking;

    public void postConnect()
    {
        this.preparation.start();
    }

    public Message think()
    {
        return this.thinking.start();
    }
}
