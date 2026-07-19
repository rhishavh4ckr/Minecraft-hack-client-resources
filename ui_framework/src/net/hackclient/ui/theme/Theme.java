package net.hackclient.ui.theme;

/**
 * Theme bundles every color/metric the rendering layer needs to paint the
 * UI. Themes are immutable at runtime; swaps replace the active instance
 * atomically via {@link ThemeManager}.
 */
public final class Theme {

    public final String name;

    // Metrics (in dp, scaled at draw time).
    public final int    cornerRadius;
    public final int    panelPadding;
    public final int    componentHeight;
    public final int    innerPadding;
    public final float  shadowStrength;

    // Colors
    public final Color  windowBg;
    public final Color  panelBg;
    public final Color  panelBorder;
    public final Color  surfaceSunken;
    public final Color  textPrimary;
    public final Color  textSecondary;
    public final Color  textMuted;
    public final Color  textShadow;
    public final Color  accentStart;
    public final Color  accentEnd;
    public final Color  success;
    public final Color  warning;
    public final Color  danger;

    private Theme(Builder b) {
        this.name           = b.name;
        this.cornerRadius   = b.cornerRadius;
        this.panelPadding   = b.panelPadding;
        this.componentHeight= b.componentHeight;
        this.innerPadding   = b.innerPadding;
        this.shadowStrength = b.shadowStrength;
        this.windowBg       = b.windowBg;
        this.panelBg        = b.panelBg;
        this.panelBorder    = b.panelBorder;
        this.surfaceSunken  = b.surfaceSunken;
        this.textPrimary    = b.textPrimary;
        this.textSecondary  = b.textSecondary;
        this.textMuted      = b.textMuted;
        this.textShadow     = b.textShadow;
        this.accentStart    = b.accentStart;
        this.accentEnd      = b.accentEnd;
        this.success        = b.success;
        this.warning        = b.warning;
        this.danger         = b.danger;
    }

    /** Sample the accent gradient (0=orange, 1=red). */
    public Color accent(float t) {
        return Color.lerp(accentStart, accentEnd, t);
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    /** Default VAPE-inspired dark theme. */
    public static Theme vapeDark() {
        return builder("VAPE Dark")
                .cornerRadius(4)
                .panelPadding(8)
                .componentHeight(22)
                .innerPadding(6)
                .shadowStrength(0.55f)
                .windowBg(ColorPalette.WINDOW_BG)
                .panelBg(ColorPalette.PANEL_BG)
                .panelBorder(ColorPalette.PANEL_BORDER)
                .surfaceSunken(ColorPalette.SURFACE_SUNKEN)
                .textPrimary(ColorPalette.TEXT_PRIMARY)
                .textSecondary(ColorPalette.TEXT_SECONDARY)
                .textMuted(ColorPalette.TEXT_MUTED)
                .textShadow(ColorPalette.TEXT_SHADOW)
                .accentStart(ColorPalette.ACCENT_ORANGE)
                .accentEnd(ColorPalette.ACCENT_RED)
                .success(ColorPalette.SUCCESS)
                .warning(ColorPalette.WARNING)
                .danger(ColorPalette.DANGER)
                .build();
    }

    public static final class Builder {
        private final String name;
        private int    cornerRadius   = 4;
        private int    panelPadding   = 8;
        private int    componentHeight= 22;
        private int    innerPadding   = 6;
        private float  shadowStrength = 0.5f;
        private Color  windowBg       = ColorPalette.WINDOW_BG;
        private Color  panelBg        = ColorPalette.PANEL_BG;
        private Color  panelBorder    = ColorPalette.PANEL_BORDER;
        private Color  surfaceSunken  = ColorPalette.SURFACE_SUNKEN;
        private Color  textPrimary    = ColorPalette.TEXT_PRIMARY;
        private Color  textSecondary  = ColorPalette.TEXT_SECONDARY;
        private Color  textMuted      = ColorPalette.TEXT_MUTED;
        private Color  textShadow     = ColorPalette.TEXT_SHADOW;
        private Color  accentStart    = ColorPalette.ACCENT_ORANGE;
        private Color  accentEnd      = ColorPalette.ACCENT_RED;
        private Color  success        = ColorPalette.SUCCESS;
        private Color  warning        = ColorPalette.WARNING;
        private Color  danger         = ColorPalette.DANGER;

        Builder(String name) { this.name = name; }

        public Builder cornerRadius(int v)    { this.cornerRadius = v;    return this; }
        public Builder panelPadding(int v)     { this.panelPadding = v;    return this; }
        public Builder componentHeight(int v)  { this.componentHeight = v; return this; }
        public Builder innerPadding(int v)     { this.innerPadding = v;    return this; }
        public Builder shadowStrength(float v) { this.shadowStrength = v;  return this; }
        public Builder windowBg(Color v)       { this.windowBg = v;        return this; }
        public Builder panelBg(Color v)        { this.panelBg = v;         return this; }
        public Builder panelBorder(Color v)    { this.panelBorder = v;     return this; }
        public Builder surfaceSunken(Color v)  { this.surfaceSunken = v;   return this; }
        public Builder textPrimary(Color v)    { this.textPrimary = v;     return this; }
        public Builder textSecondary(Color v)  { this.textSecondary = v;   return this; }
        public Builder textMuted(Color v)      { this.textMuted = v;       return this; }
        public Builder textShadow(Color v)     { this.textShadow = v;      return this; }
        public Builder accentStart(Color v)    { this.accentStart = v;     return this; }
        public Builder accentEnd(Color v)      { this.accentEnd = v;       return this; }
        public Builder success(Color v)        { this.success = v;         return this; }
        public Builder warning(Color v)        { this.warning = v;         return this; }
        public Builder danger(Color v)         { this.danger = v;          return this; }

        public Theme build() { return new Theme(this); }
    }
}
