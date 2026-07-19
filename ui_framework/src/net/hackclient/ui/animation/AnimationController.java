package net.hackclient.ui.animation;

import net.hackclient.ui.util.MathUtil;

/**
 * Lightweight, allocation-free animation state. One controller per animating
 * property (opacity, offset, hover progress, etc.).
 * <p>
 * Usage:
 * <pre>
 *   controller.setTarget(1f);   // when hovered
 *   controller.update();        // once per frame
 *   float value = controller.value();
 * </pre>
 */
public final class AnimationController {

    private float value;
    private float target;
    private float start;
    private float duration;
    private float elapsed;
    private boolean animating;
    private Easing easing = Easing.EASE_IN_OUT_CUBIC;

    public AnimationController(float initial) {
        this.value = initial;
        this.target = initial;
        this.start = initial;
    }

    public AnimationController() {
        this(0f);
    }

    public void setEasing(Easing easing) {
        this.easing = easing == null ? Easing.LINEAR : easing;
    }

    /** Snap the value immediately (no animation). */
    public void setValueInstant(float value) {
        this.value = value;
        this.target = value;
        this.start = value;
        this.elapsed = 0f;
        this.animating = false;
    }

    /** Begin animating to {@code target} over {@code durationSeconds} using linear easing. */
    public void setTarget(float target, float durationSeconds) {
        if (Math.abs(target - this.target) < 1e-4f && animating) return;
        this.start = this.value;
        this.target = target;
        this.duration = Math.max(0.001f, durationSeconds);
        this.elapsed = 0f;
        this.animating = true;
    }

    /** Begin animating with the default 180ms duration. */
    public void setTarget(float target) {
        setTarget(target, 0.18f);
    }

    /** Advance the animation by {@code delta} seconds. Call once per frame from the render loop. */
    public void update(float delta) {
        if (!animating) return;
        elapsed += delta;
        float t = MathUtil.clamp(elapsed / duration, 0f, 1f);
        value = MathUtil.lerp(start, target, easing.apply(t));
        if (t >= 1f) {
            value = target;
            animating = false;
        }
    }

    /** Update using a default 60fps timestep (for environments without delta-time). */
    public void update() {
        update(1f / 60f);
    }

    public float value() { return value; }
    public float target() { return target; }
    public boolean isAnimating() { return animating; }

    /** Convenience: value clamped to [0,1], useful for fades/pulses. */
    public float progress() { return MathUtil.clamp(value, 0f, 1f); }
}
