package comlib.adk.launcher.option;

import comlib.adk.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionAmbulance extends Option {

    @Override
    public String getKey() {
        return "-at";
    }

    @Override
    public void setValue(Config config, String[] datas) {
        if(datas.length == 3) {
            config.setValue(ConfigKey.KEY_AMBULANCE_NAME, datas[1]);
            config.setValue(ConfigKey.KEY_AMBULANCE_COUNT, datas[2]);
        }
        else if(datas.length == 2) {
            config.setValue(ConfigKey.KEY_AMBULANCE_COUNT, datas[1]);
        }
    }
}