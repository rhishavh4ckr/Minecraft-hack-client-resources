package net.hackclient.ui.component;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.animation.Easing;
import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Toast notification that slides in from the right edge, stays visible for
 * a configurable duration, then slides out. Renders with an accent strip
 * on the left for type (info/success/warning/danger).
 */
public class Notification extends UIComponent {

    public enum Type { INFO, SUCCESS, WARNING, DANGER }

    public interface DismissHandler { void onDismiss(Notification n); }

    private final TextLabel title = new TextLabel();
    private final TextLabel body = new TextLabel();
    private Type type = Type.INFO;
    private float duration = 3f;
    private float elapsed;
    private boolean dead;
    private DismissHandler onDismiss;
    private final AnimationController slide = new AnimationController(0f);
    private boolean entering = true;

    public Notification(String title, String body, Type type) {
        this.title.text(title).font(FontManager.FONT_PROXIMA_BOLD, 13f, true);
        this.body.text(body).font(FontManager.FONT_PROXIMA_NOVA, 11f, false)
                .color(ThemeManager.current().textSecondary);
        this.type = type;
        add(this.title);
        add(this.body);
        slide.setEasing(Easing.EASE_OUT_BACK);
        slide.setTarget(1f, 0.35f);
    }

    public Notification duration(float seconds) { this.duration = seconds; return this; }
    public Notification onDismiss(DismissHandler h) { this.onDismiss = h; return this; }
    public boolean isDead() { return dead; }

    @Override
    public void update(float delta) {
        super.update(delta);
        slide.update(delta);
        elapsed += delta;
        if (entering && slide.value() >= 0.999f) entering = false;
        if (!entering && elapsed >= duration) {
            // Start dismiss
            slide.setTarget(0f, 0.25f);
            if (slide.value() <= 0.01f) {
                dead = true;
                if (onDismiss != null) onDismiss.onDismiss(this);
            }
        }
        float pad = 8f;
        title.position(bounds.x + pad + 6f, bounds.y + pad);
        body.position(bounds.x + pad + 6f, bounds.y + pad + 16f);
        float w = Math.max(title.preferredWidth(-1), body.preferredWidth(-1)) + pad * 2f + 12f;
        bounds.w = Math.max(bounds.w, w);
        bounds.h = pad * 2f + 32f;
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        float translateX = (1f - slide.value()) * (bounds.w + 20f);
        ctx.fillRoundedRect(bounds.x - translateX, bounds.y, bounds.w, bounds.h, theme.cornerRadius + 2,
                theme.windowBg);
        ctx.drawRoundedRect(bounds.x - translateX + 0.5f, bounds.y + 0.5f, bounds.w - 1f, bounds.h - 1f,
                theme.cornerRadius + 2, theme.panelBorder, 1f);
        Color accent;
        switch (type) {
            case SUCCESS: accent = theme.success; break;
            case WARNING: accent = theme.warning; break;
            case DANGER:  accent = theme.danger;  break;
            default:      accent = theme.accent(0.5f); break;
        }
        ctx.fillRoundedRect(bounds.x - translateX + 4f, bounds.y + 4f, 4f, bounds.h - 8f, 2f, accent);
        // Draw children with translation
        ctx.pushScissor(bounds.x - translateX, bounds.y, bounds.w, bounds.h);
        for (UIComponent c : children) {
            float ox = c.bounds.x, oy = c.bounds.y;
            c.bounds.offset(-translateX, 0f);
            c.draw(ctx);
            c.bounds.set(ox, oy, c.bounds.w, c.bounds.h);
        }
        ctx.popScissor();
    }

    @Override
    public float preferredWidth(float forHeight) { return 220f; }
    @Override
    public float preferredHeight(float forWidth) { return 52f; }
}
