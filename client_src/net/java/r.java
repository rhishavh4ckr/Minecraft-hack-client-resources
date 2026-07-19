package net.java;

import java.util.List;
import net.labymod.api.LabyModAddon;
import net.hackclient.compat.Bridge;

/**
 * Legacy LabyMod 3 addon entry point. Registers the new UI theme/HUD and
 * preserves any existing backend setup via the original bootstrap.
 */
public class r extends LabyModAddon {
   private static boolean a;

   public void onEnable() {
      if (!a) {
         m.a();
         Bridge.bootstrap(5, m.a.trim());
         l.a((Object)(new Object[]{null, null, 5, null, null, m.a.trim()}));
         a = true;
      }
   }

   public void loadConfig() {
      // Config is now handled by net.hackclient.ui.hud.HudConfig.
   }

   protected void fillSettings(List var1) {
      // Settings are rendered through the new ClickGUI; expose to LabyMod
      // via Bridge.ui() if the host needs a native panel.
   }
}
