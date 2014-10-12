package comlib.adk.sample;

import comlib.adk.team.Team;
import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.team.tactics.PoliceForceTactics;
import rescuecore2.config.Config;

public class SampleTeam implements Team {

    @Override
    public String getTeamName() {
        return "sample";
    }

    @Override
    public void readConfig(Config config) {
    }

    @Override
    public FireBrigadeTactics getFireBrigadeTactics() {
        return new SampleFire();
    }

    @Override
    public AmbulanceTeamTactics getAmbulanceTeamTactics() {
        return new SampleAmbulance();
    }

    @Override
    public PoliceForceTactics getPoliceForceTactics() {
        return new SamplePolice();
    }
}
