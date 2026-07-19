package net.hackclient.ui.component;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.MathUtil;

/**
 * Pill-shaped boolean toggle with a label to the left. Mirrors the
 * compact on/off switches popularised by VAPE and similar clients.
 */
public class Toggle extends UIComponent {

    public interface Listener { void onToggle(Toggle source, boolean value); }

    private String label = "";
    private boolean value = false;
    private Listener listener;
    private final AnimationController toggleAnim = new AnimationController(0f);

    public Toggle() { bounds.h = 20f; }
    public Toggle(String label) { this(); this.label = label; }
    public Toggle label(String s) { this.label = s == null ? "" : s; return this; }
    public boolean value() { return value; }
    public Toggle value(boolean v) { this.value = v; toggleAnim.setValueInstant(v ? 1f : 0f); return this; }
    public Toggle listener(Listener l) { this.listener = l; return this; }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        boolean over = bounds.contains(ev.x, ev.y);
        switch (ev.type) {
            case MOUSE_MOVE:
                hovered = over;
                hoverAnim.setTarget(hovered ? 1f : 0f);
                return false;
            case MOUSE_PRESS:
                if (over && ev.button == 0) {
                    pressed = true;
                    return true;
                }
                break;
            case MOUSE_RELEASE:
                if (pressed && over && ev.button == 0) {
                    value = !value;
                    toggleAnim.setTarget(value ? 1f : 0f, 0.18f);
                    if (listener != null) listener.onToggle(this, value);
                }
                pressed = false;
                break;
            default: break;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        toggleAnim.update(delta);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f, false);
        // Label on the left
        float labelH = f.lineHeight();
        float labelY = ctx.textBaseline(f, bounds.y + (bounds.h - labelH) * 0.5f);
        Color textCol = theme.textPrimary;
        if (opacity < 1f) textCol = textCol.withAlpha(textCol.a * opacity);
        ctx.drawString(f, label, bounds.x, labelY, textCol, true);
        float labelW = f.stringWidth(label);
        // Track on the right
        float trackW = 28f;
        float trackH = 12f;
        float trackX = bounds.x + labelW + 8f;
        float trackY = bounds.y + (bounds.h - trackH) * 0.5f;
        Color trackBg = value ? theme.accent(0.5f).withAlpha(0.9f) : theme.surfaceSunken;
        if (opacity < 1f) trackBg = trackBg.withAlpha(trackBg.a * opacity);
        ctx.fillRoundedRect(trackX, trackY, trackW, trackH, trackH * 0.5f, trackBg);
        // Knob
        float knobSize = trackH - 2f;
        float animT = toggleAnim.value();
        float knobX = MathUtil.lerp(trackX + 1f, trackX + trackW - knobSize - 1f, animT);
        float knobY = trackY + 1f;
        Color knobCol = Color.lerp(theme.textMuted, Color.WHITE, animT);
        if (opacity < 1f) knobCol = knobCol.withAlpha(knobCol.a * opacity);
        ctx.fillRoundedRect(knobX, knobY, knobSize, knobSize, knobSize * 0.5f, knobCol);
    }

    @Override
    public float preferredHeight(float forWidth) { return 20f; }
    @Override
    public float preferredWidth(float forHeight) {
        float labelW = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f, false).stringWidth(label);
        return labelW + 8f + 28f;
    }
}
