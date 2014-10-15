package comlib.adk.team.tactics;

import rescuecore2.standard.entities.StandardEntityURN;

public class FireBrigadeTactics extends Tactics {
    public static final String MAX_WATER_KEY = "fire.tank.maximum";
    public static final String MAX_DISTANCE_KEY = "fire.extinguish.max-distance";
    public static final String MAX_POWER_KEY = "fire.extinguish.max-sum";

    public int maxWater;
    public int maxDistance;
    public int maxPower;

    @Override
    public void postConnect() {
        this.model.indexClass(StandardEntityURN.BUILDING, StandardEntityURN.REFUGE,StandardEntityURN.HYDRANT,StandardEntityURN.GAS_STATION);
        this.maxWater = this.config.getIntValue(MAX_WATER_KEY);
        this.maxDistance = this.config.getIntValue(MAX_DISTANCE_KEY);
        this.maxPower = this.config.getIntValue(MAX_POWER_KEY);
    }
}
