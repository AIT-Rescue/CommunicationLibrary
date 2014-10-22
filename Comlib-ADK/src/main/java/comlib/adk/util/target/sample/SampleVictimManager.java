package comlib.adk.util.target.sample;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.route.RouteUtil;
import comlib.adk.util.target.VictimManager;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.worldmodel.EntityID;

public class SampleVictimManager extends VictimManager{

    public SampleVictimManager(Tactics user) {
        super(user);
    }

    @Override
    public void add(Civilian civilian) {
        this.civilianList.add(civilian.getID());
    }

    @Override
    public void add(AmbulanceTeam agent) {
        this.agentList.add(agent.getID());
    }

    @Override
    public void add(FireBrigade agent) {
        this.agentList.add(agent.getID());
    }

    @Override
    public void add(PoliceForce agent) {
        this.agentList.add(agent.getID());
    }

    @Override
    public void remove(Civilian civilian) {
        this.civilianList.remove(civilian.getID());
    }

    @Override
    public void remove(AmbulanceTeam agent) {
        this.agentList.remove(agent.getID());
    }

    @Override
    public void remove(FireBrigade agent) {
        this.agentList.remove(agent.getID());
    }

    @Override
    public void remove(PoliceForce agent) {
        this.agentList.remove(agent.getID());
    }

    @Override
    public EntityID getTarget(int time) {
        //this.routeSearch.getPath(time, this.tactics.me, this.civilianList.get(0));
        EntityID result = null;
        int minDistance = Integer.MAX_VALUE;
        if(!this.civilianList.isEmpty()) {
            for (EntityID id : this.civilianList) {
                int d = RouteUtil.distance(this.tactics.model, this.tactics.me, this.tactics.model.getEntity(id));
                if (minDistance >= d) {
                    minDistance = d;
                    result = id;
                }
            }
        }
        else {
            for (EntityID id : this.agentList) {
                int d = RouteUtil.distance(this.tactics.model, this.tactics.me, this.tactics.model.getEntity(id));
                if (minDistance >= d) {
                    minDistance = d;
                    result = id;
                }
            }
        }
        return result;
    }
}
