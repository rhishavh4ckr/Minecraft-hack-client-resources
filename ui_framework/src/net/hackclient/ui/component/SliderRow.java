package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.LionPalette;
import net.hackclient.ui.util.MathUtil;

/**
 * Lion-style setting row: label on left, number box on right, horizontal
 * slider track below. Matches the "Blocks 1.0" / "Clutch Move Delay 0"
 * rows from the reference screenshot.
 */
public class SliderRow extends UIComponent {
    public interface Listener { void onChanged(SliderRow source, float value); }
    private final String label;
    private float min, max, value;
    private int decimals;
    private Listener listener;
    private boolean dragging;
    private final NumberBox readout;

    public SliderRow(String label, float min, float max, float value, int decimals) {
        this.label = label;
        this.min = min; this.max = max; this.value = value; this.decimals = decimals;
        this.readout = new NumberBox(value, decimals);
        bounds.h = 42f;
        add(readout);
    }
    public SliderRow listener(Listener l) { this.listener = l; return this; }
    public float value() { return value; }

    @Override
    public void update(float delta) {
        super.update(delta);
        float pad = 12f;
        float topH = 18f;
        readout.bounds.set(bounds.x + bounds.w - 80f - pad, bounds.y + 8f, 80f, topH);
    }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        boolean over = bounds.contains(ev.x, ev.y);
        float pad = 12f;
        float trackY = bounds.y + 30f;
        float trackH = 3f;
        float trackX = bounds.x + pad;
        float trackW = bounds.w - pad * 2f;
        switch (ev.type) {
            case MOUSE_MOVE:
                hovered = over;
                hoverAnim.setTarget(hovered ? 1f : 0f);
                if (dragging) setFromMouse(ev.x, trackX, trackW);
                return false;
            case MOUSE_PRESS:
                if (over && ev.y >= trackY - 2 && ev.y <= trackY + trackH + 6 && ev.button == 0) {
                    dragging = true;
                    setFromMouse(ev.x, trackX, trackW);
                    return true;
                }
                break;
            case MOUSE_RELEASE:
                dragging = false;
                break;
            default: break;
        }
        // Dispatch to readout
        return readout.dispatch(ev);
    }

    private void setFromMouse(float mx, float trackX, float trackW) {
        float t = MathUtil.clamp((mx - trackX) / Math.max(1f, trackW), 0f, 1f);
        float nv = min + (max - min) * t;
        nv = Math.round(nv * (float) Math.pow(10, decimals)) / (float) Math.pow(10, decimals);
        if (Float.compare(nv, value) != 0) {
            value = nv;
            readout.set(nv);
            if (listener != null) listener.onChanged(this, nv);
        }
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 13f, false);
        // Row background
        ctx.fillRect(bounds.x, bounds.y, bounds.w, bounds.h, LionPalette.ROW_BG);
        ctx.drawRect(bounds.x + 0.5f, bounds.y + 0.5f, bounds.w - 1f, bounds.h - 1f, LionPalette.ROW_BORDER, 1f);
        // Label
        float pad = 12f;
        ctx.drawString(f, label, bounds.x + pad,
                ctx.textBaseline(f, bounds.y + 8f + (18f - f.lineHeight()) * 0.5f),
                LionPalette.TEXT_PRIMARY, false);
        // Track
        float trackY = bounds.y + 30f;
        float trackH = 3f;
        float trackX = bounds.x + pad;
        float trackW = bounds.w - pad * 2f;
        ctx.fillRect(trackX, trackY, trackW, trackH, LionPalette.SURFACE_SUNKEN);
        float t = (value - min) / Math.max(1e-5f, max - min);
        t = MathUtil.clamp(t, 0f, 1f);
        ctx.fillRect(trackX, trackY, trackW * t, trackH, LionPalette.ACCENT);
        // Knob
        float kx = trackX + trackW * t - 3f;
        ctx.fillRect(kx, trackY - 2f, 6f, trackH + 4f, LionPalette.ACCENT);
    }

    @Override public float preferredHeight(float forWidth) { return 42f; }
    @Override public float preferredWidth(float forHeight) { return bounds.w > 0 ? bounds.w : -1; }
}
