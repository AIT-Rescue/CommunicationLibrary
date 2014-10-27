package sample;

import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.util.action.FireAction;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.BuildingManager;
import comlib.adk.util.target.sample.SampleBuildingManager;
import comlib.manager.MessageManager;
import comlib.message.information.CivilianMessage;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.entities.Refuge;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;
import sample.event.SampleBuildingEvent;

import java.util.List;

public class SampleFire extends FireBrigadeTactics {
    public RouteSearcher routeSearcher;
    public BuildingSelector buildingSelector;

    @Override
    public void preparation() {
        this.routeSearcher = new SampleRouteSearcher(this);
        this.buildingSelector = new SampleBuildingManager(this);
    }

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new SampleBuildingEvent(this.model, this.buildingSelector));
    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        this.updateInfo(changed, manager);
        if (this.me.getWater() == 0) {
            this.target = null;
            return this.moveRefuge(time);
        }
        if(this.target != null) {
            Building building = (Building)this.model.getEntity(this.target);
            if(building.isOnFire()) {
                if(this.model.getDistance(this.agentID, this.target) <= this.maxDistance) {
                    return FireAction.extinguish(this, time, this.target, this.maxPower);
                }
                else {
                    List<EntityID> path = this.routeSearcher.getPath(time, this.me, this.target);
                    path.remove(path.size() - 1);
                    return FireAction.move(this, time, path);
                }
            }
            else {
                this.target = this.buildingSelector.getTarget(time);
                if(this.target != null) {
                    List<EntityID> path = this.routeSearcher.getPath(time, this.me, this.target);
                    path.remove(path.size() - 1);
                    return FireAction.move(this, time, path);
                }
                return FireAction.move(this, time, this.routeSearcher.randomWalk());
            }
        }
        else {
            //if(this.me.isWaterDefined()) { //??????????????????????????????????????????????????????
            if(this.location instanceof Refuge) {
                if (this.me.getWater() < this.maxWater) {
                    return FireAction.rest(this, time);
                }
                else {
                    this.target = this.buildingSelector.getTarget(time);
                    if(this.target != null) {
                        List<EntityID> path = this.routeSearcher.getPath(time, this.me, this.target);
                        path.remove(path.size() - 1);
                        return FireAction.move(this, time, path);
                    }
                    return FireAction.move(this, time, this.routeSearcher.randomWalk());
                }
            }
            else {
                this.target = this.buildingSelector.getTarget(time);
                if(this.target != null) {
                    List<EntityID> path = this.routeSearcher.getPath(time, this.me, this.target);
                    path.remove(path.size() - 1);
                    return FireAction.move(this, time, path);
                }
                return FireAction.move(this, time, this.routeSearcher.randomWalk());
            }
            //}
        }
    }

    private void updateInfo(ChangeSet changed, MessageManager manager) {
        for (EntityID next : changed.getChangedEntities()) {
            StandardEntity entity = model.getEntity(next);
            if(entity instanceof Civilian) {
                manager.addSendMessage(new CivilianMessage((Civilian)entity));
            }
            //else if(entity instanceof Blockade) {
            //manager.addSendMessage(new RoadMessage((Blockade)entity));
            //}
            else if(entity instanceof Building) {
                this.buildingSelector.add((Building) entity);
            }
        }
    }

    private Message moveRefuge(int time) {
        List<EntityID> path = this.routeSearcher.getPath(time, this.me, this.refugeList.get(0).getID());
        return path != null ? FireAction.move(this, time, path) : FireAction.move(this, time, this.routeSearcher.randomWalk());
    }
}
