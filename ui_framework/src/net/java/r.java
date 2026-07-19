package net.java;

import java.util.List;
import net.labymod.api.LabyModAddon;
import net.hackclient.compat.Bridge;

public class r extends LabyModAddon {
    private static boolean inited;
    public void onEnable() {
        if (!inited) {
            m.init();
            Bridge.bootstrap(5, m.PAYLOAD_PATH);
            l.a((Object)new Object[]{null, null, 5, null, null, m.PAYLOAD_PATH});
            inited = true;
        }
    }
    public void loadConfig() {}
    protected void fillSettings(List list) {}
}
