package net.hackclient.ui.animation;

/**
 * Easing functions for UI animations. Each is a pure function of {@code t}
 * in [0,1] returning a value in [0,1].
 */
public enum Easing {
    LINEAR {
        public float apply(float t) { return t; }
    },
    EASE_IN_OUT_CUBIC {
        public float apply(float t) {
            float c = t < 0f ? 0f : (t > 1f ? 1f : t);
            return c < 0.5f ? 4f * c * c * c
                    : 1f - (float) Math.pow(-2.0 * c + 2.0, 3.0) / 2f;
        }
    },
    EASE_OUT_QUAD {
        public float apply(float t) {
            float c = t < 0f ? 0f : (t > 1f ? 1f : t);
            return 1f - (1f - c) * (1f - c);
        }
    },
    EASE_OUT_BACK {
        public float apply(float t) {
            float c = t < 0f ? 0f : (t > 1f ? 1f : t);
            final float s = 1.70158f;
            float cm = c - 1f;
            return 1f + cm * cm * ((s + 1f) * cm + s);
        }
    };

    public abstract float apply(float t);
}
