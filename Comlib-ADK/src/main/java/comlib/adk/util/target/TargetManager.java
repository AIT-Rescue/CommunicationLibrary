package comlib.adk.util.target;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.worldmodel.EntityID;

public abstract class TargetManager {

    protected Tactics tactics;

    public TargetManager(Tactics t) {
        this.tactics = t;
    }

    public abstract EntityID getTarget(int time);
}