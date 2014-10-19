package comlib.adk.dummy;

import comlib.adk.team.tactics.PoliceForceTactics;
import comlib.adk.util.action.PoliceAction;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class DummyPolice extends PoliceForceTactics {

    @Override
    public void postConnect() {

    }

    @Override
    public void registerEvent(MessageManager manager) {

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
        manager.addSendMessage(new DummyMessage(time, 10, 0));
        return PoliceAction.rest(this, time);
    }
}
