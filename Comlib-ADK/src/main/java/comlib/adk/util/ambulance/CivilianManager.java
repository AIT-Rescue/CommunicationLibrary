package comlib.adk.util.ambulance;

import comlib.adk.team.tactics.Tactics;
import rescuecore2.worldmodel.EntityID;

public abstract class CivilianManager {

    protected Tactics tactics;

    public CivilianManager(Tactics user) {
        this.tactics = user;
    }

    public abstract EntityID getTarget();

}
