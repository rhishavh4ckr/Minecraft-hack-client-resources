package net.hackclient.ui.theme;

/**
 * LionClient-inspired palette matching the reference screenshot:
 * <ul>
 *   <li>Deep navy panel backgrounds (#1e2d3d)</li>
 *   <li>Slightly darker window chrome (#17222e)</li>
 *   <li>Bright blue accent (#4aa0ff) used for selected tabs, active checkboxes
 *       and slider fills</li>
 *   <li>Light grey text on dark panels, blue value labels on the right</li>
 * </ul>
 */
public final class LionPalette {
    private LionPalette() {}

    public static final Color WINDOW_BG       = new Color(0x17, 0x22, 0x2E, 235);
    public static final Color PANEL_BG        = new Color(0x1E, 0x2D, 0x3D, 255);
    public static final Color ROW_BG          = new Color(0x24, 0x35, 0x48, 255);
    public static final Color ROW_BORDER      = new Color(0x33, 0x4A, 0x63, 255);
    public static final Color SURFACE_SUNKEN  = new Color(0x14, 0x1D, 0x28, 255);
    public static final Color DIVIDER         = new Color(0x2C, 0x3E, 0x52, 255);

    public static final Color TAB_BG          = new Color(0x1B, 0x27, 0x34, 255);
    public static final Color TAB_ACTIVE      = new Color(0x4A, 0xA0, 0xFF, 255);
    public static final Color TAB_HOVER       = new Color(0x2A, 0x40, 0x58, 255);

    public static final Color TEXT_PRIMARY    = new Color(0xDF, 0xE8, 0xF2, 255);
    public static final Color TEXT_SECONDARY  = new Color(0xB0, 0xC2, 0xD6, 255);
    public static final Color TEXT_MUTED      = new Color(0x7A, 0x8E, 0xA5, 255);
    public static final Color TEXT_ACCENT     = new Color(0x6F, 0xBE, 0xFF, 255); // right-side numbers
    public static final Color TEXT_SHADOW     = new Color(0, 0, 0, 110);

    public static final Color ACCENT          = new Color(0x4A, 0xA0, 0xFF, 255);
    public static final Color ACCENT_DARK     = new Color(0x2F, 0x70, 0xC0, 255);
    public static final Color SUCCESS         = new Color(0x4A, 0xFF, 0x8F, 255);
    public static final Color WARNING         = new Color(0xFF, 0xB8, 0x4A, 255);
    public static final Color DANGER          = new Color(0xFF, 0x5C, 0x5C, 255);

    public static Theme lionDark() {
        return Theme.builder("Lion Dark")
                .cornerRadius(2)
                .panelPadding(8)
                .componentHeight(24)
                .innerPadding(6)
                .shadowStrength(0f) // Lion uses flat chrome, no soft shadows
                .windowBg(WINDOW_BG)
                .panelBg(PANEL_BG)
                .panelBorder(ROW_BORDER)
                .surfaceSunken(SURFACE_SUNKEN)
                .textPrimary(TEXT_PRIMARY)
                .textSecondary(TEXT_SECONDARY)
                .textMuted(TEXT_MUTED)
                .textShadow(TEXT_SHADOW)
                .accentStart(ACCENT)
                .accentEnd(ACCENT_DARK)
                .success(SUCCESS)
                .warning(WARNING)
                .danger(DANGER)
                .build();
    }
}
