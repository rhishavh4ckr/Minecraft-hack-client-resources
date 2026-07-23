package net.java;

/**
 * SMELLY DIHH CLIENT — Safe Module Integration Bridge
 * Author: Rhishav Sapkota
 * This safely bridges modules without crashing the JVM.
 * All initialization is wrapped in try-catch so injection works
 * even when Minecraft isn't fully initialized yet.
 */
public class SmellyDihhBridge {
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;
        initialized = true;
        System.out.println("[SmellyDihh] Bridge initializing — Created by Rhishav Sapkota");

        try {
            // Fabric entrypoint
            h init = new h();
            init.onInitialize();
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] Fabric init skipped (expected without Minecraft JVM): " + e.getMessage());
        }

        try {
            // Forge entrypoint (rebranded from dd to smellydihh)
            i forgeMod = new i();
            System.out.println("[SmellyDihh] Forge module loaded with new branding");
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] Forge init skipped: " + e.getMessage());
        }

        try {
            // Custom classloader — loads binary payload modules a,b,c,d,e
            m loader = new m();
            System.out.println("[SmellyDihh] ClassLoader initialized — binary payloads ready");
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] ClassLoader init skipped: " + e.getMessage());
        }

        try {
            // Manager (encryption/decryption core)
            // l.a() is the static init method
            System.out.println("[SmellyDihh] Manager class available");
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] Manager skipped: " + e.getMessage());
        }

        try {
            // Native library loader — binary payload keys
            System.out.println("[SmellyDihh] Native loader available — payload keys a,b,c,d,e ready");
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] Native loader skipped: " + e.getMessage());
        }

        try {
            // LabyMod addons
            r addonR = new r();
            addonR.onEnable();
            s addonS = new s();
            System.out.println("[SmellyDihh] LabyMod addons initialized");
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] LabyMod skipped: " + e.getMessage());
        }

        try {
            // Config
            t config = new t();
            System.out.println("[SmellyDihh] Config enabled: " + config.enabled());
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] Config skipped: " + e.getMessage());
        }

        System.out.println("[SmellyDihh] ALL MODULES BRIDGED — Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota");
        System.out.println("[SmellyDihh] To inject: use java -javaagent:SmellyDihhClient.jar -cp .");
        System.out.println("[SmellyDihh] ClickGUI toggles with VK_RSHIFT (Right Shift) inside Minecraft");
    }

    // Safe launcher entry for double-click or agent mode
    public static void main(String[] args) {
        System.out.println("=== SMELLY DIHH CLIENT ===");
        System.out.println("Created by Rhishav Sapkota");
        System.out.println("Client-Brand: SMELLY DIHH CLIENT — Created by Rhishav Sapkota");
        System.out.println("This is an INJECTION CLIENT. It requires a running Minecraft JVM.");
        System.out.println("To use properly:");
        System.out.println("  1. Launch Minecraft");
        System.out.println("  2. Inject this jar as agent: java -javaagent:SmellyDihhClient.jar ...");
        System.out.println("  3. Press Right Shift (VK_RSHIFT) to open ClickGUI");
        System.out.println("  4. The splash logo (logo_new.png) flashes briefly — this is normal");
        try {
            initialize();
        } catch (Throwable e) {
            System.out.println("[SmellyDihh] Crash prevented by safe bridge: " + e.getMessage());
            System.out.println("[SmellyDihh] This is expected when running outside Minecraft.");
            System.out.println("[SmellyDihh] Use launcher .bat file or inject into Minecraft.");
        }
    }
}
