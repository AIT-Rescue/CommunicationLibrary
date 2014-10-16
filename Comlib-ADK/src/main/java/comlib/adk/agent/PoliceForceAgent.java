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
        return "Police Force : " + this.tactics.toString();
    }

    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.POLICE_FORCE);
    }

    @Override
    public void initAgentValue() {
        //this.model.indexClass(StandardEntityURN.ROAD);
        this.tactics.distance = this.config.getIntValue(DISTANCE_KEY);

    }
}
