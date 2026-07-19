package net.hackclient.ui.theme;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registry of available themes and holder of the currently active theme.
 * <p>
 * All components read {@link ThemeManager#current()} every frame so that a
 * hot theme swap (for example through a settings screen) is reflected on the
 * next draw without reconstructing UI trees.
 */
public final class ThemeManager {

    private static final ThemeManager INSTANCE = new ThemeManager();

    private final Map<String, Theme> themes = new LinkedHashMap<>();
    private volatile Theme current;

    private ThemeManager() {
        register(Theme.vapeDark());
        this.current = themes.values().iterator().next();
    }

    public static ThemeManager instance() {
        return INSTANCE;
    }

    public static Theme current() {
        return INSTANCE.current;
    }

    public void register(Theme theme) {
        themes.put(theme.name, theme);
    }

    public Map<String, Theme> available() {
        return Collections.unmodifiableMap(themes);
    }

    public void setCurrent(String name) {
        Theme t = themes.get(name);
        if (t != null) this.current = t;
    }

    public void setCurrent(Theme theme) {
        if (theme != null) this.current = theme;
    }
}
