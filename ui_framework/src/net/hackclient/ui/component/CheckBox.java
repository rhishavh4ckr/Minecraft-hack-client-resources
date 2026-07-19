package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.LionPalette;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Square checkbox control matching the LionClient reference: blue-filled
 * square with a simple painted check when checked, hollow outline when not.
 */
public class CheckBox extends UIComponent {
    public interface Listener { void onChanged(CheckBox source, boolean value); }
    private String label = "";
    private boolean value;
    private Listener listener;
    private final float boxSize = 16f;

    public CheckBox(String label, boolean initial) {
        this.label = label == null ? "" : label;
        this.value = initial;
        bounds.h = 22f;
    }
    public boolean value() { return value; }
    public CheckBox listener(Listener l) { this.listener = l; return this; }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        boolean over = bounds.contains(ev.x, ev.y);
        switch (ev.type) {
            case MOUSE_MOVE:
                hovered = over;
                hoverAnim.setTarget(hovered ? 1f : 0f);
                return false;
            case MOUSE_PRESS:
                if (over && ev.button == 0) { pressed = true; return true; }
                break;
            case MOUSE_RELEASE:
                if (pressed && over && ev.button == 0) {
                    value = !value;
                    if (listener != null) listener.onChanged(this, value);
                }
                pressed = false;
                break;
            default: break;
        }
        return false;
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 13f, false);
        // Hover row highlight (subtle)
        if (hovered) {
            ctx.fillRect(bounds.x, bounds.y, bounds.w, bounds.h,
                    new Color(255,255,255, (int)(18 * hoverAnim.progress())));
        }
        // Box
        float bx = bounds.x + 10f;
        float by = bounds.y + (bounds.h - boxSize) * 0.5f;
        Color boxBg = value ? LionPalette.TAB_ACTIVE : LionPalette.SURFACE_SUNKEN;
        ctx.fillRect(bx, by, boxSize, boxSize, boxBg);
        ctx.drawRect(bx + 0.5f, by + 0.5f, boxSize - 1f, boxSize - 1f,
                value ? LionPalette.ACCENT_DARK : LionPalette.ROW_BORDER, 1f);
        if (value) {
            // White check (simple two-line tick)
            java.awt.Color chk = new java.awt.Color(0xFF, 0xFF, 0xFF, 0xFF);
            ctx.graphics().setColor(chk);
            ctx.graphics().setStroke(new java.awt.BasicStroke(2f));
            int cx1 = Math.round(bx + 3f), cy1 = Math.round(by + boxSize * 0.55f);
            int cx2 = Math.round(bx + boxSize * 0.42f), cy2 = Math.round(by + boxSize - 4f);
            int cx3 = Math.round(bx + boxSize - 3f),   cy3 = Math.round(by + 4f);
            ctx.graphics().drawLine(cx1, cy1, cx2, cy2);
            ctx.graphics().drawLine(cx2, cy2, cx3, cy3);
        }
        // Label
        float lx = bx + boxSize + 8f;
        float ly = ctx.textBaseline(f, bounds.y + (bounds.h - f.lineHeight()) * 0.5f);
        ctx.drawString(f, label, lx, ly, LionPalette.TEXT_PRIMARY, false);
        // Value label right
        String valText = value ? "Enabled" : "Disabled";
        Color valCol = value ? LionPalette.TEXT_ACCENT : LionPalette.TEXT_MUTED;
        float vw = f.stringWidth(valText);
        ctx.drawString(f, valText, bounds.x + bounds.w - vw - 10f, ly, valCol, false);
    }

    @Override public float preferredHeight(float forWidth) { return 22f; }
    @Override public float preferredWidth(float forHeight) { return bounds.w > 0 ? bounds.w : 220f; }
}
