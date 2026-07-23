package net.java;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class s extends LabyAddon {
   private static boolean a;

   protected void enable() {
      if (!a) {
         m.a();
         l.a((Object)(new Object[]{null, null, 5, null, null, m.a.trim()}));
         a = true;
      }
   }

   protected Class configurationClass() {
      return t.class;
   }
}
