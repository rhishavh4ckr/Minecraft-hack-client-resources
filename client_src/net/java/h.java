package net.java;

import net.fabricmc.api.ModInitializer;
import net.hackclient.compat.Bridge;

/**
 * Fabric mod entry point. Initialises the new UI framework and then
 * delegates to the original encrypted backend bootstrap for module logic.
 */
public class h implements ModInitializer {
    public void onInitialize() {
        // Initialise the decrypting ClassLoader (preserves backend module loading).
        m.a();
        // Initialise our new UI theme/font/HUD stack.
        Bridge.bootstrap(6, m.a.trim());
        // Hand off to the original bootstrap (native unpacking, etc.).
        l.a((Object)(new Object[]{null, null, 6, null, null, m.a.trim()}));
    }
}
