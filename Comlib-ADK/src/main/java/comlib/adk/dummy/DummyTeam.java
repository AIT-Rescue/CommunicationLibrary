package comlib.adk.dummy;

import comlib.adk.dummy.DummyAmbulance;
import comlib.adk.dummy.DummyFire;
import comlib.adk.dummy.DummyPolice;
import comlib.adk.team.Team;
import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.team.tactics.PoliceForceTactics;
import rescuecore2.config.Config;

public class DummyTeam implements Team {

    @Override
    public String getTeamName() {
        return "dummy";
    }

    @Override
    public void readConfig(Config config) {
    }

    @Override
    public FireBrigadeTactics getFireBrigadeTactics() {
        return new DummyFire();
    }

    @Override
    public AmbulanceTeamTactics getAmbulanceTeamTactics() {
        return new DummyAmbulance();
    }

    @Override
    public PoliceForceTactics getPoliceForceTactics() {
        return new DummyPolice();
    }
}
