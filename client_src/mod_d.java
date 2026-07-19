import net.java.l;
import net.java.m;
import net.hackclient.compat.Bridge;

/**
 * Standalone BaseMod entry point used by some legacy loaders (e.g. older
 * Forge/LabyMod/Weave builds). Mirrors the other bootstraps: set up the
 * decrypting class loader, bring up the new UI framework, then delegate
 * to the original payload.
 */
public class mod_d extends BaseMod {
   public mod_d() {
      m.a();
      Bridge.bootstrap(5, m.a.trim());
      l.a((Object)(new Object[]{null, null, 5, null, null, m.a.trim()}));
   }

   public String getVersion() {
      StringBuilder var1;
      (var1 = new StringBuilder()).append('1');
      var1.append('.');
      var1.append('0');
      return var1.toString();
   }

   public void load() {
   }
}
