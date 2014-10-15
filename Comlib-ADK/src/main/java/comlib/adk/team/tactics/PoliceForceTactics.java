package comlib.adk.team.tactics;

import rescuecore2.standard.entities.StandardEntityURN;

public class PoliceForceTactics extends Tactics{
    public static final String DISTANCE_KEY = "clear.repair.distance";

    public int distance;

    @Override
    public void postConnect() {
        this.model.indexClass(StandardEntityURN.ROAD);
        this.distance = this.config.getIntValue(DISTANCE_KEY);
    }
}
