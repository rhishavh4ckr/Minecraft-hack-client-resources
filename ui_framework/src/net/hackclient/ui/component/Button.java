package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.MathUtil;

/**
 * Accent-colored pressable button. When hovered/pressed the surface lightens
 * and the press animation gives a small "pop".
 */
public class Button extends UIComponent {

    public interface Action { void invoke(Button source); }

    private String label = "";
    private Action action;
    private Color fg = Color.WHITE;
    private boolean enabled = true;

    public Button() { bounds.h = 22f; }
    public Button(String label) { this(); this.label = label; }
    public Button label(String s) { this.label = s == null ? "" : s; return this; }
    public Button action(Action a) { this.action = a; return this; }
    public Button enabled(boolean e) { this.enabled = e; return this; }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        if (!enabled) return false;
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
                if (pressed && over && ev.button == 0 && action != null) {
                    action.invoke(this);
                }
                pressed = false;
                break;
            default: break;
        }
        return false;
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        float hover = hoverAnim.progress();
        float press = pressed ? 0.92f : 1f;
        float scale = MathUtil.lerp(1f, press, hover);
        // Body
        Color bg = Color.lerp(theme.accentStart, theme.accentEnd, 0.5f);
        bg = Color.lerp(bg, Color.WHITE, hover * 0.25f);
        if (!enabled) bg = bg.withAlpha(0.4f);
        if (opacity < 1f) bg = bg.withAlpha(bg.a * opacity);
        float w = bounds.w * scale;
        float h = bounds.h * scale;
        float ox = bounds.x + (bounds.w - w) * 0.5f;
        float oy = bounds.y + (bounds.h - h) * 0.5f;
        ctx.fillRoundedRect(ox, oy, w, h, theme.cornerRadius, bg);
        // Label
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 12f, true);
        float tw = f.stringWidth(label);
        float lx = bounds.x + (bounds.w - tw) * 0.5f;
        float ly = ctx.textBaseline(f, bounds.y + (bounds.h - f.lineHeight()) * 0.5f);
        Color labelCol = fg;
        if (opacity < 1f) labelCol = labelCol.withAlpha(labelCol.a * opacity);
        ctx.drawString(f, label, lx, ly, labelCol, true);
    }

    @Override
    public float preferredHeight(float forWidth) { return 22f; }
    @Override
    public float preferredWidth(float forHeight) {
        return FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 12f, true).stringWidth(label) + 20f;
    }
}
