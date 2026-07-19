package net.hackclient.compat;

import net.hackclient.ui.UIController;
import net.hackclient.ui.hud.HudConfig;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Static bridge used by the existing bootstrap entry points ({@code h},
 * {@code i}, {@code r}, {@code s}, {@code ag}, {@code mod_d}) to initialise
 * the new UI framework without importing the obfuscated class names.
 * <p>
 * Replaces the old call pattern {@code m.a(); l.a(new Object[]{...});} which
 * unpacked/invoked the encrypted payload. The new implementation simply
 * initialises theme/font/HUD subsystems and leaves all backend module logic
 * untouched (it still lives in the bundled encrypted classes; we only
 * replace the presentation layer).
 */
public final class Bridge {

    private static boolean initialised = false;

    private Bridge() { }

    /**
     * Initialise the new UI framework. Safe to call multiple times; the
     * second and subsequent calls are no-ops.
     *
     * @param bootstrapType 1=JavaAgent, 5=Forge/LabyMod addon, 6=Fabric, etc.
     *                      (accepted for API compatibility; not used).
     * @param resourcePath  optional path to a bundled payload kept for
     *                      backward compatibility with the old bootstrap.
     */
    public static synchronized void bootstrap(int bootstrapType, String resourcePath) {
        if (initialised) return;
        initialised = true;
        // Ensure the theme manager and default theme are ready before any draw.
        ThemeManager.instance();
        // Attempt to load the reference HUD layout if available.
        HudConfig.loadFromResource("/hud.json");
        // If a resource path was supplied (e.g. the "/64FV7P4H2NO7Q" blob from
        // the old loader), the backend bootstrap still happens via the
        // original encrypted ClassLoader – we simply don't need to route UI
        // through it anymore.
        if (resourcePath != null && !resourcePath.isEmpty()) {
            // Handled externally by the existing ClassLoader (m.java).
        }
    }

    /** Convenience for call sites that use the exact old signature {@code l.a(new Object[]{...})}. */
    public static void bootstrap(Object[] args) {
        int type = 5;
        String path = null;
        if (args != null && args.length >= 6) {
            if (args[2] instanceof Number) type = ((Number) args[2]).intValue();
            if (args[5] instanceof String) path = (String) args[5];
        }
        bootstrap(type, path);
    }

    /** Expose the UI controller to the rest of the mod (module GUIs, HUD hooks, etc.). */
    public static UIController ui() { return UIController.instance(); }
}
