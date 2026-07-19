package net.hackclient.ui.hud;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.animation.Easing;
import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Compact HUD-level toast (separate from the component-layer
 * {@link net.hackclient.ui.component.Notification}) used before any screen
 * is open. Shares the same visual language: rounded window background,
 * a left accent strip, and a slide-in/slide-out animation.
 */
public final class NotificationToast {

    public enum Type { INFO, SUCCESS, WARNING, DANGER }

    private final String title;
    private final String body;
    private final Type type;
    private final float duration;
    private float elapsed;
    private boolean dead;
    private boolean leaving;
    private final AnimationController slide = new AnimationController(0f);
    private float lastW = 220f, lastH = 48f;

    public NotificationToast(String title, String body, Type type, float duration) {
        this.title = title == null ? "" : title;
        this.body = body == null ? "" : body;
        this.type = type;
        this.duration = duration;
        slide.setEasing(Easing.EASE_OUT_BACK);
        slide.setTarget(1f, 0.35f);
    }

    public boolean isDead() { return dead; }
    public float lastWidth()  { return lastW; }
    public float lastHeight() { return lastH; }
    private final net.hackclient.ui.util.Rectangle bounds = new net.hackclient.ui.util.Rectangle();
    net.hackclient.ui.util.Rectangle bounds() { return bounds; }

    public void update(float delta) {
        slide.update(delta);
        elapsed += delta;
        if (!leaving && elapsed >= duration) {
            leaving = true;
            slide.setTarget(0f, 0.25f);
        }
        if (leaving && slide.value() <= 0.01f) dead = true;
        FontManager.CachedFont t = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 13f, true);
        FontManager.CachedFont b = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 11f, false);
        float pad = 8f;
        lastW = Math.max(t.stringWidth(title), b.stringWidth(body)) + pad * 2f + 14f;
        lastH = pad * 2f + t.lineHeight() + b.lineHeight();
    }

    public void render(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        Theme theme = ThemeManager.current();
        float tx = (1f - slide.value()) * (lastW + 20f);
        float x = bounds.x + tx;
        float y = bounds.y;
        ctx.fillRoundedRect(x, y, lastW, lastH, theme.cornerRadius + 2, theme.windowBg);
        ctx.drawRoundedRect(x + 0.5f, y + 0.5f, lastW - 1, lastH - 1, theme.cornerRadius + 2, theme.panelBorder, 1f);
        Color accent;
        switch (type) {
            case SUCCESS: accent = theme.success; break;
            case WARNING: accent = theme.warning; break;
            case DANGER:  accent = theme.danger;  break;
            default:      accent = theme.accent(0.5f); break;
        }
        ctx.fillRoundedRect(x + 4f, y + 4f, 4f, lastH - 8f, 2f, accent);
        FontManager.CachedFont t = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 13f, true);
        FontManager.CachedFont b = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 11f, false);
        float pad = 8f;
        ctx.drawString(t, title, x + pad + 10f,
                ctx.textBaseline(t, y + pad), theme.textPrimary, true);
        ctx.drawString(b, body, x + pad + 10f,
                ctx.textBaseline(b, y + pad + t.lineHeight()), theme.textSecondary, true);
    }
}
