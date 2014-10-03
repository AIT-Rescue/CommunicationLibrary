package comlib.manager;

import comlib.util.IntegerDataHelper;
import rescuecore2.config.Config;
import rescuecore2.standard.entities.Building;

public class RadioConfig {
    private int channel;

    private int sizeOfMessageID;

    private int sizeOfTime;

    public RadioConfig(Config config) {
        this.channel    = config.getIntValue("comlib.message.channel", 1);
        this.sizeOfTime = config.getIntValue("comlib.size.time", 9);
        this.updateMessageIDSize(config.getIntValue("comlib.message.messageID", 16) - 1);
    }

    public int getChannel() {
        return this.channel;
    }

    public int getSizeOfMessageID() {
        return this.sizeOfMessageID;
    }

    public void updateMessageIDSize(int id) {
        int size = IntegerDataHelper.getBitSize(id);
        if (size > this.sizeOfMessageID)
            this.sizeOfMessageID = size;
    }

    public int getSizeOfTime() {
        return this.sizeOfTime;
    }

    public int getSizeOfEntityID(Class<? extends Building> c) {

        return c != null ? 32: 0;
    }
}
