package net.hackclient.ui.theme;

import net.hackclient.ui.util.MathUtil;

/**
 * Immutable RGBA color with fast pack/unpack helpers and HSL interpolation
 * utilities used throughout the theme and animation systems.
 */
public final class Color {

    public static final Color TRANSPARENT = new Color(0f, 0f, 0f, 0f);
    public static final Color WHITE       = new Color(1f, 1f, 1f, 1f);
    public static final Color BLACK       = new Color(0f, 0f, 0f, 1f);
    public static final Color RED         = new Color(1f, 0f, 0f, 1f);
    public static final Color GREEN       = new Color(0f, 1f, 0f, 1f);
    public static final Color BLUE        = new Color(0f, 0f, 1f, 1f);

    public final float r, g, b, a;

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int r, int g, int b, int a) {
        this(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /** ARGB packed int (the format Minecraft and most GL renderers use). */
    public static Color fromARGB(int argb) {
        return new Color(
                (argb >> 16) & 0xFF,
                (argb >> 8) & 0xFF,
                argb & 0xFF,
                (argb >> 24) & 0xFF
        );
    }

    public int toARGB() {
        int ri = MathUtil.clampInt((int) (r * 255f + 0.5f), 0, 255);
        int gi = MathUtil.clampInt((int) (g * 255f + 0.5f), 0, 255);
        int bi = MathUtil.clampInt((int) (b * 255f + 0.5f), 0, 255);
        int ai = MathUtil.clampInt((int) (a * 255f + 0.5f), 0, 255);
        return (ai << 24) | (ri << 16) | (gi << 8) | bi;
    }

    public Color withAlpha(float alpha) {
        return new Color(r, g, b, MathUtil.clamp(alpha, 0f, 1f));
    }

    public Color withAlpha(int alpha) {
        return withAlpha(alpha / 255f);
    }

    /** Linear interpolation between two colors (no gamma correction – fast path for UI). */
    public static Color lerp(Color from, Color to, float t) {
        return new Color(
                MathUtil.lerp(from.r, to.r, t),
                MathUtil.lerp(from.g, to.g, t),
                MathUtil.lerp(from.b, to.b, t),
                MathUtil.lerp(from.a, to.a, t)
        );
    }

    /**
     * Compute an HSB rainbow color for the given phase, returning a fully opaque color.
     *
     * @param phase progression in radians; advances with time to animate the hue
     */
    public static Color rainbow(float phase, float saturation, float brightness) {
        float hue = (phase * 0.15915494f) % 1f; // 1/(2*pi)
        if (hue < 0f) hue += 1f;
        return fromHSB(hue, saturation, brightness, 1f);
    }

    public static Color fromHSB(float h, float s, float b, float a) {
        int rgb = java.awt.Color.HSBtoRGB(h, s, b);
        Color c = fromARGB(0xFF000000 | rgb);
        return new Color(c.r, c.g, c.b, a);
    }

    /**
     * VAPE-style gradient: orange (#FF8800) at the top fading to red (#FF2020) at the bottom.
     */
    public static Color vapeOrangeRed(float t) {
        t = MathUtil.clamp(t, 0f, 1f);
        return new Color(
                1f,
                MathUtil.lerp(0.53f, 0.13f, t),
                MathUtil.lerp(0f, 0.13f, t),
                1f
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;
        Color c = (Color) o;
        return Float.compare(c.r, r) == 0
                && Float.compare(c.g, g) == 0
                && Float.compare(c.b, b) == 0
                && Float.compare(c.a, a) == 0;
    }

    @Override
    public int hashCode() {
        return toARGB();
    }

    @Override
    public String toString() {
        return "Color(" + r + "," + g + "," + b + "," + a + ")";
    }
}
