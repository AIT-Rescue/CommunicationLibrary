package comlib.adk.dummy;

import comlib.adk.dummy.event.AmbulanceCivilianEvent;
import comlib.adk.dummy.util.CivilianManager;
import comlib.adk.team.tactics.AmbulanceTeamTactics;
import comlib.adk.util.action.AmbulanceAction;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class DummyAmbulance extends AmbulanceTeamTactics {

    public CivilianManager civilianManager;

    public DummyAmbulance() {
        this.civilianManager = new CivilianManager();
    }

    @Override
    public void postConnect() {
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
        this.civilianManager.update(this.model, changed, manager);

        manager.addSendMessage(new DummyMessage(time, 10, 0));
        return AmbulanceAction.rest(this, time);
    }
}
