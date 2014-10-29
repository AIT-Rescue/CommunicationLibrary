package comlib.adk.agent;

import comlib.adk.team.tactics.FireBrigadeTactics;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class FireBrigadeAgent extends TacticsAgent<FireBrigadeTactics, FireBrigade> {
    
    public static final String MAX_WATER_KEY = "fire.tank.maximum";
    public static final String MAX_DISTANCE_KEY = "fire.extinguish.max-distance";
    public static final String MAX_POWER_KEY = "fire.extinguish.max-sum";
    
    public FireBrigadeTactics tactics;
    
    public FireBrigadeAgent(FireBrigadeTactics fireBrigadeTactics) {
        super(fireBrigadeTactics);
    }
    
    @Override
    public String toString() {
        return "Comlib Fire Brigade";
    }
    
    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.FIRE_BRIGADE);
    }
    
    public void setAgentEntity(FireBrigadeTactics fireBrigadeTactics) {
        fireBrigadeTactics.me = this.me();
    }
    
    public void setAgentUniqueValue(FireBrigadeTactics fireBrigadeTactics) {
        fireBrigadeTactics.maxWater = this.config.getIntValue(MAX_WATER_KEY);
        fireBrigadeTactics.maxDistance = this.config.getIntValue(MAX_DISTANCE_KEY);
        fireBrigadeTactics.maxPower = this.config.getIntValue(MAX_POWER_KEY);
        fireBrigadeTactics.refugeList = this.getRefuges();
    }
}