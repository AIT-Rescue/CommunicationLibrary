package comlib.adk.team.tactics;

import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.StandardEntityURN;

public class PoliceForceTactics extends Tactics<PoliceForce>{
    public static final String DISTANCE_KEY = "clear.repair.distance";

    public PoliceForce me;
    public int distance;

    @Override
    public void postConnect() {
        this.model.indexClass(StandardEntityURN.ROAD);
        this.distance = this.config.getIntValue(DISTANCE_KEY);
    }
}
