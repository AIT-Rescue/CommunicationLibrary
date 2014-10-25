package comlib.adk.agent;

import comlib.adk.team.tactics.PoliceForceTactics;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class PoliceForceAgent extends TacticsAgent<PoliceForceTactics, PoliceForce> {
    
    public static final String DISTANCE_KEY = "clear.repair.distance";
    
    public PoliceForceTactics tactics;
    
    public PoliceForceAgent(PoliceForceTactics policeForceTactics) {
        super(policeForceTactics);
    }
    
    @Override
    public String toString() {
        return "Comlib Police Force";
    }
    
    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.POLICE_FORCE);
    }
    
    @Override
    public void setAgentUniqueValue(PoliceForceTactics policeForceTactics) {
        policeForceTactics.model.indexClass(StandardEntityURN.ROAD);
        policeForceTactics.distance = this.config.getIntValue(DISTANCE_KEY);
    }
    
    @Override
    public void setAgentEntity(PoliceForceTactics policeForceTactics) {
        policeForceTactics.me = this.me();
    }
}
