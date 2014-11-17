package comlib.adk.launcher.dummy;

import comlib.adk.team.Team;
import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.team.tactics.PoliceForceTactics;

public class DummyTeam extends Team {

    @Override
    public String getTeamName() {
        return "dummy";
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
