package net.java;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.hackclient.compat.Bridge;

/**
 * Modern LabyMod addon entry point (the {@code @AddonMain} annotated class).
 * On enable we bring up the new UI stack, then hand off to the original
 * payload for backend/module logic.
 */
@AddonMain
public class s extends LabyAddon {
   private static boolean a;

   protected void enable() {
      if (!a) {
         m.a();
         Bridge.bootstrap(5, m.a.trim());
         l.a((Object)(new Object[]{null, null, 5, null, null, m.a.trim()}));
         a = true;
      }
   }

   protected Class configurationClass() {
      return t.class;
   }
}
