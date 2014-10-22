package comlib.adk.launcher.dummy;

import comlib.adk.launcher.dummy.event.AmbulanceCivilianEvent;
import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.util.action.AmbulanceAction;
import comlib.adk.util.route.RouteSearcher;
import comlib.adk.util.route.sample.SampleRouteSearcher;
import comlib.adk.util.target.VictimManager;
import comlib.adk.util.target.sample.SampleVictimManager;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;

public class DummyAmbulance extends AmbulanceTeamTactics {

    //移動経路の選択
    //救助対象の管理・選択
    public VictimManager civilianManager;

    public RouteSearcher routeSearch;

    public EntityID rescueTarget;

    public DummyAmbulance() {
    }

    @Override
    public void preparation() {
        this.rescueTarget = null;
        this.routeSearch = new SampleRouteSearcher(this);
        this.civilianManager = new SampleVictimManager(this);
    }

    @Override
    public void registerEvent(MessageManager manager) {
        manager.registerEvent(new AmbulanceCivilianEvent(this.model, this.civilianManager));
    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        /*
        受信処理→eventによる情報処理
        視覚情報の処理→情報処理完了
        ・上の２つで送る情報をある程度生成
        現在の状態の確認→
        ・埋まっている→救助要請、rest
        ・目標決まってない→情報より対象設定・補充が必要ならば避難場所、move
        ・目標決まってる→通れるか確認、move
        ・対象付近→救助活動
        情報の送信
         */
        this.updateInfo(changed, manager);

        if(this.someoneOnBoard()) {
            if (this.location instanceof Refuge) {
                this.rescueTarget = null;
                return AmbulanceAction.unload(this, time);
                /*if(this.rescueTarget != null){
                    //this.bus.getOutput().addMessage(this.bus.getAgent().messageFactory.createInformationMessage(this.bus.getMemory().getTime(), (Human)this.bus.getAgent().getModel().getEntity(this.bus.getMemory(AmbulanceMemory.class).getRescueTarget())));
                }*/
            }
            else {
                return this.moveRefuge(time);
            }
        }


        manager.addSendMessage(new DummyMessage(time, 10, 0));
        return AmbulanceAction.rest(this, time);
    }

    private void updateInfo(ChangeSet changed, MessageManager manager) {
        for (EntityID next : changed.getChangedEntities()) {
            StandardEntity entity = model.getEntity(next);
            if(entity instanceof Civilian) {
                //this.civilianManager.update((Civilian)entity, manager);
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
        if(this.rescueTarget == null) {
            return false;
        }
        return ((Human)this.model.getEntity(this.rescueTarget)).getPosition().equals(this.agentID);
    }

    private Message moveRefuge(int time) {
        return AmbulanceAction.move(this, time, this.routeSearch.getPath(time, this.me, this.refugeList.get(0).getID()));
    }
}
