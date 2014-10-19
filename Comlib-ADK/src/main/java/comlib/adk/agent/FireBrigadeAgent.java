package comlib.adk.agent;

import comlib.adk.team.tactics.FireBrigadeTactics;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class FireBrigadeAgent extends TacticsAgent<FireBrigadeTactics, FireBrigade> {
    public static final String MAX_WATER_KEY = "fire.tank.maximum";
    public static final String MAX_DISTANCE_KEY = "fire.extinguish.max-distance";
    public static final String MAX_POWER_KEY = "fire.extinguish.max-sum";

    //public FireBrigadeTactics tactics;

    public FireBrigadeAgent(FireBrigadeTactics fireBrigadeTactics) {
        super(fireBrigadeTactics);
        //this.tactics = fireBrigadeTactics;
    }

    @Override
    public String toString() {
        return "Comlib Fire Brigade";
    }

    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.FIRE_BRIGADE);
    }

    @Override
    public void initAgentValue(FireBrigadeTactics fireBrigadeTactics) {
        //this.model.indexClass(StandardEntityURN.BUILDING, StandardEntityURN.REFUGE,StandardEntityURN.HYDRANT,StandardEntityURN.GAS_STATION);
        //this.tactics.maxWater = this.config.getIntValue(MAX_WATER_KEY);
        //this.tactics.maxDistance = this.config.getIntValue(MAX_DISTANCE_KEY);
        //this.tactics.maxPower = this.config.getIntValue(MAX_POWER_KEY);
        fireBrigadeTactics.maxWater = this.config.getIntValue(MAX_WATER_KEY);
        fireBrigadeTactics.maxDistance = this.config.getIntValue(MAX_DISTANCE_KEY);
        fireBrigadeTactics.maxPower = this.config.getIntValue(MAX_POWER_KEY);
        fireBrigadeTactics.refugeList = this.getRefuges();
    }
}
