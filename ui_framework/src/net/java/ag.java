package net.java;

import java.lang.instrument.Instrumentation;
import net.hackclient.compat.Bridge;

public class ag implements Runnable {
    private byte[] encodedArgs;
    private Instrumentation inst;

    public static void premain(String arg, Instrumentation inst) {
        byte[] decoded = null;
        if (arg != null) {
            try { decoded = l.a(arg.getBytes("UTF-8")); } catch (Exception e) { e.printStackTrace(); }
        }
        m.init();
        Bridge.bootstrap(1, m.PAYLOAD_PATH);
        ag runner = new ag();
        runner.encodedArgs = decoded;
        runner.inst = inst;
        new Thread(runner).start();
    }

    public void run() {
        l.a(new Object[]{encodedArgs, inst, 1, null, null, m.PAYLOAD_PATH});
    }
}
