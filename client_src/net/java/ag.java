package net.java;

import java.lang.instrument.Instrumentation;
import net.hackclient.compat.Bridge;

/**
 * Java Agent entry point (used when attaching to an existing Minecraft
 * process). Initialises the UI subsystem before the agent thread starts
 * transforming classes.
 */
public class ag implements Runnable {
   private byte[] a;
   private Instrumentation a;

   public static void premain(String var0, Instrumentation var1) {
      byte[] var2 = null;
      if (var0 != null) {
         try {
            String var10000 = var0;
            StringBuilder var4;
            (var4 = new StringBuilder()).append('U');
            var4.append('T');
            var4.append('F');
            var4.append('-');
            var4.append('8');
            var2 = l.a(var10000.getBytes(var4.toString()));
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      m.a();
      Bridge.bootstrap(1, m.a.trim());
      ag var5;
      (var5 = new ag()).a = var2;
      var5.a = var1;
      (new Thread(var5)).start();
   }

   public void run() {
      l.a((Object)(new Object[]{this.a, this.a, 1, null, null, m.a.trim()}));
   }
}
