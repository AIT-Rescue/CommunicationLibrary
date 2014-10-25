package comlib.adk.launcher.dummy;

import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.util.action.FireAction;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.BuildingManager;
import comlib.adk.util.target.sample.SampleBuildingManager;
import comlib.manager.MessageManager;
import comlib.message.information.CivilianMessage;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

import java.util.List;

public class DummyFire extends FireBrigadeTactics {

    public RouteSearcher routeSearcher;
    public BuildingManager buildingManager;

    public EntityID targetBuilding;

    @Override
    public void preparation() {
        this.routeSearcher = new SampleRouteSearcher(this);
        this.buildingManager = new SampleBuildingManager(this);
    }

    @Override
    public void registerEvent(MessageManager manager) {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        //Targetは定まっているか
        //  →true→燃えているか
        //    →true→水は足りるか（距離からの演算）
        //      →true→消化
        //      →false→近くにいるのか
        //        →true→給水(moveRefuge)
        //        →false→移動して放水できるか
        //          →true→移動
        //          →false→給水(moveRefuge)
        //    →false→他のTargetはあるか
        //      →true→移動して放水できるか
        //        →true→移動
        //        →false→給水(moveRefuge)
        //      →false→給水が必要か
        //        →true→給水(moveRefuge)
        //        →false→ランダム移動
        //  →false→他のTargetはあるか
        //    →true→移動して放水できるか
        //      →true→移動
        //      →false→給水(moveRefuge)
        //    →false→給水が必要か
        //      →true→給水(moveRefuge)
        //      →false→ランダム移動

        this.updateInfo(changed, manager);

        if(this.me.isWaterDefined()) { //??????????????????????????????????????????????????????
            if(this.location instanceof Refuge) {
                if (this.me.getWater() < this.maxWater) {
                    return FireAction.rest(this, time);
                }
                else {
                    //move target
                    EntityID id = this.buildingManager.getTarget(time);
                    if(id != null) {
                        List<EntityID> path = this.routeSearcher.getPath(time, this.me, id);
                        path.remove(path.size() - 1);
                        return FireAction.move(this, time, path);
                    }
                    return FireAction.move(this, time, this.routeSearcher.randomWalk());
                }
            }
            else {
                if (this.me.getWater() == 0) {
                    return this.moveRefuge(time);
                }
            }
        }
        // Find all buildings that are on fire
        //Collection<EntityID> all = getBurningBuildings();
        // Can we extinguish any right now?
        /*for (EntityID next : all) {
            if (this.model.getDistance(getID(), next) <= this.maxDistance) {
                return FireAction.extinguish(this, time, next, this.maxPower);
            }
        }*/
        if(this.model.getDistance(getID(), this.targetBuilding) <= this.maxDistance) {
        }
        // Plan a path to a fire
        /*for (EntityID next : all) {
            List<EntityID> path = planPathToFire(next);
            if (path != null) {
                sendMove(time, path);
                return;
            }
        }*/
        List<EntityID> path = this.routeSearcher.randomWalk();
        return path != null ? FireAction.move(this, time, path) : FireAction.rest(this, time);
    }

    private void updateInfo(ChangeSet changed, MessageManager manager) {
        for (EntityID next : changed.getChangedEntities()) {
            StandardEntity entity = model.getEntity(next);
            if(entity instanceof Civilian) {
                manager.addSendMessage(new CivilianMessage((Civilian)entity));
            }
            else if(entity instanceof Blockade) {
                //manager.addSendMessage(new RoadMessage((Blockade)entity));
            }
            else if(entity instanceof Building) {
                this.buildingManager.add((Building)entity);
            }
        }
    }

    private Message moveRefuge(int time) {
        List<EntityID> path = this.routeSearcher.getPath(time, this.me, this.refugeList.get(0).getID());
        return path != null ? FireAction.move(this, time, path) : FireAction.move(this, time, this.routeSearcher.randomWalk());
    }

    //  →false→他のTargetはあるか
    //    →true→移動して放水できるか
    //      →true→移動
    //      →false→給水(moveRefuge)
    //    →false→給水が必要か
    //      →true→給水(moveRefuge)
    //      →false→ランダム移動
}
