package net.hackclient.ui.theme;

/**
 * Central palette for the UI redesign, modeled on the VAPE v4 reference:
 * <ul>
 *   <li>Deep, near-black translucent panels</li>
 *   <li>Bright orange→red accent gradient used for the active rail/wordmark</li>
 *   <li>High-contrast white text with two weight tiers</li>
 * </ul>
 * All values are exposed as constants so that swapping themes (e.g. for a
 * light-mode variant) is a single-file change.
 */
public final class ColorPalette {

    private ColorPalette() {
    }

    // ---- Backgrounds ----
    /** Default panel background: near-black with ~58% opacity (matches VAPE ArrayList row). */
    public static final Color PANEL_BG          = new Color(10, 10, 12, 148);
    /** Stronger background used for modal/base windows. */
    public static final Color WINDOW_BG         = new Color(14, 14, 18, 225);
    /** Border/separator line, very subtle. */
    public static final Color PANEL_BORDER      = new Color(255, 255, 255, 22);
    /** Slightly lighter inset for sliders, toggle tracks, etc. */
    public static final Color SURFACE_SUNKEN    = new Color(0, 0, 0, 110);

    // ---- Text ----
    public static final Color TEXT_PRIMARY      = new Color(255, 255, 255, 255);
    public static final Color TEXT_SECONDARY    = new Color(200, 200, 210, 220);
    public static final Color TEXT_MUTED        = new Color(140, 140, 155, 220);
    public static final Color TEXT_SHADOW       = new Color(0, 0, 0, 140);

    // ---- Accent (VAPE orange→red) ----
    public static final Color ACCENT_ORANGE     = new Color(255, 136, 0, 255);
    public static final Color ACCENT_RED        = new Color(255, 32, 32, 255);
    public static final Color ACCENT_HIGHLIGHT  = new Color(255, 170, 40, 255);

    // ---- Semantic ----
    public static final Color SUCCESS           = new Color(80, 230, 120, 255);
    public static final Color WARNING           = new Color(255, 200, 40, 255);
    public static final Color DANGER            = new Color(230, 60, 60, 255);

    // ---- HUD ----
    /** Left vertical accent rail used behind the ArrayList. */
    public static final Color HUD_RAIL_TOP      = ACCENT_ORANGE;
    public static final Color HUD_RAIL_BOTTOM   = ACCENT_RED;
    /** Background rect behind each ArrayList row. */
    public static final Color HUD_ROW_BG        = new Color(0, 0, 0, 150);
    /** Watermark outline badge color (the little "v4" tag). */
    public static final Color HUD_BADGE_OUTLINE = new Color(255, 255, 255, 220);

    /**
     * Sample a point along the accent gradient where {@code t=0} is orange
     * and {@code t=1} is red.
     */
    public static Color accentGradient(float t) {
        return Color.vapeOrangeRed(t);
    }
}
