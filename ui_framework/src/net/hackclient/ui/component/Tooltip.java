package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Small floating label shown near the mouse cursor. Attached to any
 * component; the host layer controls visibility and position.
 */
public class Tooltip extends UIComponent {

    private final TextLabel label = new TextLabel();
    private float padding = 6f;

    public Tooltip(String text) {
        label.text(text).font(FontManager.FONT_PROXIMA_NOVA, 11f, false)
                .color(ThemeManager.current().textPrimary);
        add(label);
    }

    public void show(float x, float y) {
        visible(true);
        position(x + 10f, y + 10f);
    }

    public void hide() { visible(false); }

    @Override
    public void update(float delta) {
        super.update(delta);
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 11f, false);
        float w = f.stringWidth(label.text());
        bounds.w = w + padding * 2f;
        bounds.h = f.lineHeight() + padding * 2f;
        label.position(bounds.x + padding, bounds.y + padding);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        ctx.fillRoundedRect(bounds.x, bounds.y, bounds.w, bounds.h, theme.cornerRadius, theme.windowBg);
        ctx.drawRoundedRect(bounds.x + 0.5f, bounds.y + 0.5f, bounds.w - 1f, bounds.h - 1f,
                theme.cornerRadius, theme.panelBorder, 1f);
    }

    @Override
    public float preferredWidth(float forHeight) { return bounds.w; }
    @Override
    public float preferredHeight(float forWidth) { return bounds.h; }
}
