package comlib.adk.util.ambulance;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.route.RouteSearch;
import comlib.adk.util.route.RouteUtil;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.List;

public class CivilianManager {

    protected Tactics tactics;

    protected RouteSearch routeSearch;

    public List<EntityID> civilianList;

    public CivilianManager(Tactics user, RouteSearch rs) {
        this.tactics = user;
        this.routeSearch = rs;
        this.civilianList = new ArrayList<>();
    }

    public void add(Civilian civilian) {
        this.civilianList.add(civilian.getID());
    }

    public void remove(Civilian civilian) {
        this.remove(civilian.getID());
    }

    public void remove(EntityID id) {
        this.civilianList.remove(id);
    }

    public EntityID getTarget(int time) {
        //this.routeSearch.getPath(time, this.tactics.me, this.civilianList.get(0));
        EntityID result = null;
        int minDistance = Integer.MAX_VALUE;
        for(EntityID id : this.civilianList) {
            int d = RouteUtil.distance(this.tactics.model, this.tactics.me, this.tactics.model.getEntity(id));
            if(minDistance >= d) {
                minDistance = d;
                result = id;
            }
        }
        return result;
    }

}
