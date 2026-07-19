package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.MathUtil;

/**
 * Horizontal slider for numeric values over a [min,max] range. Displays the
 * current value to the right of the track with a consistent label area.
 */
public class Slider extends UIComponent {

    public interface Listener { void onValueChanged(Slider source, float value); }

    private String label = "";
    private float min = 0f;
    private float max = 100f;
    private float value = 50f;
    private int decimals = 1;
    private Listener listener;
    private boolean dragging;

    public Slider() { bounds.h = 20f; }
    public Slider(String label, float min, float max, float value) {
        this(); this.label = label; this.min = min; this.max = max; this.value = value;
    }
    public Slider label(String s) { this.label = s; return this; }
    public Slider range(float min, float max) { this.min = min; this.max = max; return this; }
    public Slider decimals(int d) { this.decimals = MathUtil.clampInt(d, 0, 4); return this; }
    public Slider listener(Listener l) { this.listener = l; return this; }

    public float value() { return value; }
    public Slider value(float v) { this.value = MathUtil.clamp(v, min, max); return this; }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        boolean over = bounds.contains(ev.x, ev.y);
        switch (ev.type) {
            case MOUSE_MOVE:
                hovered = over;
                hoverAnim.setTarget(hovered ? 1f : 0f);
                if (dragging) setFromMouse(ev.x);
                return false;
            case MOUSE_PRESS:
                if (over && ev.button == 0) {
                    dragging = true;
                    setFromMouse(ev.x);
                    return true;
                }
                break;
            case MOUSE_RELEASE:
                dragging = false;
                break;
            default: break;
        }
        return false;
    }

    private void setFromMouse(float mx) {
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f);
        float labelW = f.stringWidth(label) + 8f;
        float valW = f.stringWidth(formatValue()) + 6f;
        float trackX = bounds.x + labelW;
        float trackW = bounds.w - labelW - valW;
        float t = MathUtil.clamp((mx - trackX) / Math.max(1f, trackW), 0f, 1f);
        float nv = min + (max - min) * t;
        nv = Math.round(nv * (float) Math.pow(10, decimals)) / (float) Math.pow(10, decimals);
        if (Float.compare(nv, value) != 0) {
            value = nv;
            if (listener != null) listener.onValueChanged(this, value);
        }
    }

    private String formatValue() {
        return String.format("%." + decimals + "f", value);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f);
        float labelH = f.lineHeight();
        float labelY = ctx.textBaseline(f, bounds.y + (bounds.h - labelH) * 0.5f);
        ctx.drawString(f, label, bounds.x, labelY, theme.textPrimary, true);
        String valText = formatValue();
        float valW = f.stringWidth(valText);
        ctx.drawString(f, valText, bounds.x + bounds.w - valW, labelY, theme.textSecondary, true);

        float labelW = f.stringWidth(label) + 8f;
        float trackX = bounds.x + labelW;
        float trackW = bounds.w - labelW - valW - 6f;
        float trackY = bounds.y + bounds.h * 0.5f - 2f;
        float trackH = 4f;
        ctx.fillRoundedRect(trackX, trackY, trackW, trackH, trackH * 0.5f, theme.surfaceSunken);

        float t = (value - min) / Math.max(1e-5f, max - min);
        t = MathUtil.clamp(t, 0f, 1f);
        float filledW = trackW * t;
        Color filled = theme.accent(hoverAnim.progress() * 0.6f);
        ctx.fillRoundedRect(trackX, trackY, filledW, trackH, trackH * 0.5f, filled);

        float knobSize = trackH + 4f;
        float knobX = trackX + filledW - knobSize * 0.5f;
        float knobY = trackY + trackH * 0.5f - knobSize * 0.5f;
        ctx.fillRoundedRect(knobX, knobY, knobSize, knobSize, knobSize * 0.5f, Color.WHITE);
    }

    @Override
    public float preferredHeight(float forWidth) { return 20f; }
    @Override
    public float preferredWidth(float forHeight) { return Math.max(120f, bounds.w); }
}
