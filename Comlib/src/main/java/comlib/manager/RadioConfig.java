package comlib.manager;

import rescuecore2.config.Config;
import rescuecore2.standard.entities.*;

public class RadioConfig {
    private int channel;

    private int sizeOfMessageID;

    private int sizeOfTime;

    public RadioConfig(Config config) {
        this.channel    = config.getIntValue("comlib.message.channel", 1);
        this.sizeOfTime = config.getIntValue("comlib.size.time", 9);
        this.updateSize(config.getIntValue("comlib.message.messageID", 16) - 1);
    }

    public void updateSize(int id) {
        int size = IntegerDataHelper.getBitSize(id);
        if (size > this.sizeOfMessageID)
            this.sizeOfMessageID = size;
    }
}
