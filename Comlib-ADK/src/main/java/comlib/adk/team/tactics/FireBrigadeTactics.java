package comlib.adk.team.tactics;

import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.Refuge;

import java.util.List;

public abstract class FireBrigadeTactics extends Tactics<FireBrigade> {

    public FireBrigade me;

    public int maxWater;
    public int maxDistance;
    public int maxPower;

    public List<Refuge> refugeList;

    public FireBrigade me() {
        return this.me;
    }
}
