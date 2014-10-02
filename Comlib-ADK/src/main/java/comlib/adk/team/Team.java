package comlib.adk.team;

import comlib.adk.tactics.AmbulanceTeamTactics;
import comlib.adk.tactics.FireBrigadeTactics;
import comlib.adk.tactics.PoliceForceTactics;

public interface Team {
    public String getTeamName();
    public FireBrigadeTactics getFireBrigadeTactics();
    public AmbulanceTeamTactics getAmbulanceTeamTactics();
    public PoliceForceTactics getPoliceForceTactics();
}