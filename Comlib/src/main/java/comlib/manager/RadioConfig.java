package comlib.manager;

import comlib.util.IntegerDataHelper;
import rescuecore2.config.Config;
import rescuecore2.standard.entities.*;

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

        return c != null ? 32 : 0;
    }

    public int getSizeOfEntityID(Class<? extends Civilian> c) {
        return c != null ? 32 : 0;
    }

    public int getSizeOfEntityID(Class<? extends FireBrigade> c)
    {
        return 32;
    }

    public int getSizeOfEntityID(Class<? extends GasStation> c)
    {
        return 32;
    }

    public int getSizeOfEntityID(Class<? extends PoliceForce> c)
    {
        return 32;
    }

    public int getSizeOfEntityID(Class<? extends Refuge> c)
    {
        return 32;
    }

    public int getSizeOfEntityID(Class<? extends Road> c)
    {
        return 32;
    }

    public int getSizeOfHumanHP() {
        return 32;
    }

    public int getSizeOfHumanBuriedness() {
        return 32;
    }

    public int getSizeOfHumanDamage() {
        return 32;
    }

    public int getSizeOfPosition() {
        return 32;
    }

}
