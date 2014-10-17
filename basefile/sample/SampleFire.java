package sample;

import comlib.adk.team.tactics.FireBrigadeTactics;
import comlib.adk.util.action.FireAction;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class SampleFire extends FireBrigadeTactics {
    @Override
    public void postConnect() {

    }

    @Override
    public void registerEvent(MessageManager manager) {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        manager.addSendMessage(new DummyMessage(time, 10, 0));
        return FireAction.rest(this, time);
    }
}
