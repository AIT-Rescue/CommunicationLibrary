package comlib.adk.team.tactics;

import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardEntityURN;

public abstract class FireBrigadeTactics extends Tactics<FireBrigade> {

    public FireBrigade me;

    public int maxWater;
    public int maxDistance;
    public int maxPower;
}
