package net.java;

import cpw.mods.fml.common.Mod;
import net.hackclient.compat.Bridge;

/**
 * Forge 1.7.10 / 1.8.9 mod entry point. The UI framework is initialised
 * here so the VAPE-style HUD renders immediately on the Forge main menu.
 */
@Mod(
   modid = "dd"
)
@net.minecraftforge.fml.common.Mod(
   value = "dd",
   modid = "dd"
)
public class i {
   public i() {
      m.a();
      Bridge.bootstrap(5, m.a.trim());
      l.a((Object)(new Object[]{null, null, 5, null, null, m.a.trim()}));
   }
}
