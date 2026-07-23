package net.java;

import java.util.List;
import net.labymod.api.LabyModAddon;

public class r extends LabyModAddon {
   private static boolean a;

   public void onEnable() {
      if (!a) {
         m.a();
         l.a((Object)(new Object[]{null, null, 5, null, null, m.a.trim()}));
         a = true;
      }
   }

   public void loadConfig() {
   }

   protected void fillSettings(List var1) {
   }
}
