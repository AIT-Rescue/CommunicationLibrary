package comlib.adk.agent;

import comlib.adk.team.tactics.FireBrigadeTactics;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class FireBrigadeAgent extends TacticsAgent<FireBrigadeTactics, FireBrigade> {
    public FireBrigadeAgent(FireBrigadeTactics fireBrigadeTactics) {
        super(fireBrigadeTactics);
    }

    @Override
    public String toString() {
        return "Fire Brigade : " + this.tactics.toString();
    }

    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.FIRE_BRIGADE);
    }
}
