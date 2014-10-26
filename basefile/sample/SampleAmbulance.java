package sample;

import comlib.adk.launcher.dummy.event.AmbulanceCivilianEvent;
import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.util.action.AmbulanceAction;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.VictimManager;
import comlib.adk.util.target.sample.SampleVictimManager;
import comlib.manager.MessageManager;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

import java.util.List;

public class SampleAmbulance extends AmbulanceTeamTactics {

    //救助対象の管理・選択
    public VictimManager victimManager;
    //移動経路の選択
    public RouteSearcher routeSearcher;

    public SampleAmbulance() {
    }

    @Override
    public void preparation() {
        this.routeSearcher = new SampleRouteSearcher(this);
        this.victimManager = new SampleVictimManager(this);
    }

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new AmbulanceCivilianEvent(this.model, this.victimManager));
    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        this.updateInfo(changed, manager);

        if(this.someoneOnBoard()) {
            if (this.location instanceof Refuge) {
                this.target = null;
                return AmbulanceAction.unload(this, time);
            }
            else {
                return this.moveRefuge(time);
            }
        }
        if(this.target != null) {
            Human human = (Human)this.model.getEntity(this.target);
            if(human.getPosition().equals(location.getID())) {
                if ((human instanceof Civilian) && human.getBuriedness() == 0 && !(this.location instanceof Refuge)) {
                    return AmbulanceAction.load(this, time, this.target);
                } else if (human.getBuriedness() > 0) {
                    return AmbulanceAction.rescue(this, time, this.target);
                }
                else {
                    this.target = null;
                    List<EntityID> path = this.routeSearcher.randomWalk();
                    return path != null ? AmbulanceAction.move(this, time, path) : AmbulanceAction.rest(this, time);
                }
            }
            else {
                return AmbulanceAction.move(this, time, this.routeSearcher.getPath(time, this.me, this.target));
            }
        }
        EntityID id = this.victimManager.getTarget(time);
        if (id != null) {
            this.target = id;
            return AmbulanceAction.move(this, time, this.routeSearcher.getPath(time, this.me, id));
        }
        if(this.me.getBuriedness() > 0) {
            return AmbulanceAction.rest(this, time);
        }
        //manager.addSendMessage(new DummyMessage(time, 10, 0));
        List<EntityID> path = this.routeSearcher.randomWalk();
        return path != null ? AmbulanceAction.move(this, time, path) : AmbulanceAction.rest(this, time);
    }

    private void updateInfo(ChangeSet changed, MessageManager manager) {
        for (EntityID next : changed.getChangedEntities()) {
            StandardEntity entity = model.getEntity(next);
            if(entity instanceof Civilian) {
                this.victimManager.add((Civilian)entity);
            }
            else if(entity instanceof Blockade) {
                //manager.addSendMessage(new BlockadeMessage((Blockade)entity));
            }
            else if(entity instanceof Building) {
                Building b = (Building)entity;
                if(b.getFieryness() > 0) {
                    //manager.addSendMessage(new BuildingMessage(b));
                }
            }
        }
    }

    /*private boolean someoneOnBoard() {
        for (StandardEntity next : this.model.getEntitiesOfType(StandardEntityURN.CIVILIAN)) {
            if (((Human)next).getPosition().equals(this.agentID)) {
                return true;
            }
        }
        return false;
    }*/

    private boolean someoneOnBoard() {
        return this.target != null && ((Human) this.model.getEntity(this.target)).getPosition().equals(this.agentID);
    }

    private Message moveRefuge(int time) {
        List<EntityID> path = this.routeSearcher.getPath(time, this.me, this.refugeList.get(0).getID());
        return path != null ? AmbulanceAction.move(this, time, path) : AmbulanceAction.rest(this, time);
    }
}
