package net.hackclient.ui.util;

/**
 * Fast, allocation-free math helpers used throughout the UI framework.
 * <p>
 * Methods are static and never return newly-allocated objects from hot paths.
 */
public final class MathUtil {

    private MathUtil() {
    }

    public static float clamp(float value, float min, float max) {
        return value < min ? min : (value > max ? max : value);
    }

    public static double clamp(double value, double min, double max) {
        return value < min ? min : (value > max ? max : value);
    }

    public static int clampInt(int value, int min, int max) {
        return value < min ? min : (value > max ? max : value);
    }

    /** Linear interpolation. */
    public static float lerp(float from, float to, float t) {
        return from + (to - from) * t;
    }

    public static double lerp(double from, double to, double t) {
        return from + (to - from) * t;
    }

    /** Ease in-out cubic used by {@link net.hackclient.ui.animation.AnimationController}. */
    public static float easeInOutCubic(float t) {
        float c = clamp(t, 0f, 1f);
        return c < 0.5f ? 4f * c * c * c : 1f - (float) Math.pow(-2.0 * c + 2.0, 3.0) / 2f;
    }

    /** 0..1 sine-wave for rainbow effects. */
    public static float wave(float phase) {
        return (float) ((Math.sin(phase) + 1.0) * 0.5);
    }

    public static int floor(float v) {
        int i = (int) v;
        return v < i ? i - 1 : i;
    }

    public static int ceil(float v) {
        int i = (int) v;
        return v > i ? i + 1 : i;
    }
}
