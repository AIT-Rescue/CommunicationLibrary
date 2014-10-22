package comlib.adk.util.target.sample;

import comlib.adk.team.tactics.Tactics;
import comlib.adk.util.route.RouteUtil;
import comlib.adk.util.target.BlockadeManager;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.worldmodel.EntityID;

public class SampleBlockadeManager extends BlockadeManager{

    public SampleBlockadeManager(Tactics user) {
        super(user);
    }

    @Override
    public void add(Blockade blockade) {
        this.blockadeList.add(blockade.getID());
    }

    @Override
    public void remove(Blockade blockade) {
        this.blockadeList.remove(blockade.getID());
    }

    @Override
    public EntityID getTarget(int time) {
        EntityID result = null;
        int minDistance = Integer.MAX_VALUE;
        for (EntityID id : this.blockadeList) {
            int d = RouteUtil.distance(this.tactics.model, this.tactics.me, this.tactics.model.getEntity(id));
            if (minDistance >= d) {
                minDistance = d;
                result = id;
            }
        }
        return result;
    }
}
