package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.LionPalette;
import net.hackclient.ui.util.MathUtil;

/**
 * Right-aligned value readout used next to sliders on Lion's settings rows.
 * Displays numbers in the accent-blue color against a sunken box on the right.
 */
public class NumberBox extends UIComponent {
    private float value;
    private int decimals = 1;
    public NumberBox(float initial, int decimals) { this.value = initial; this.decimals = decimals; bounds.h = 18f; bounds.w = 80f; }
    public NumberBox(int initial) { this(initial, 0); }
    public void set(float v) { this.value = v; }
    public float get() { return value; }

    @Override
    protected void onDraw(RenderContext ctx) {
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f, false);
        ctx.fillRect(bounds.x, bounds.y, bounds.w, bounds.h, LionPalette.SURFACE_SUNKEN);
        ctx.drawRect(bounds.x + 0.5f, bounds.y + 0.5f, bounds.w - 1f, bounds.h - 1f, LionPalette.ROW_BORDER, 1f);
        String s = String.format("%." + decimals + "f", value);
        if (decimals == 0) s = Long.toString(Math.round(value));
        float sw = f.stringWidth(s);
        ctx.drawString(f, s, bounds.x + bounds.w - sw - 6f,
                ctx.textBaseline(f, bounds.y + (bounds.h - f.lineHeight()) * 0.5f),
                LionPalette.TEXT_ACCENT, false);
    }
    @Override public float preferredHeight(float forWidth) { return 18f; }
    @Override public float preferredWidth(float forHeight) { return 80f; }
}
