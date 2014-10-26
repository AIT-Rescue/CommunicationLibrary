package sample;

import comlib.adk.team.tactics.PoliceForceTactics;
import comlib.adk.util.action.PoliceAction;
import comlib.manager.MessageManager;
import comlib.message.DummyMessage;
import rescuecore2.messages.Message;
import rescuecore2.worldmodel.ChangeSet;

public class SamplePolice extends PoliceForceTactics {

    @Override
    public void preparation() {

    }

    @Override
    public void registerEvent(MessageManager manager) {

    }

    @Override
    public Message think(int time, ChangeSet changed, MessageManager manager) {
        manager.addSendMessage(new DummyMessage(time, 10, 0));
        return PoliceAction.rest(this, time);
    }
}
