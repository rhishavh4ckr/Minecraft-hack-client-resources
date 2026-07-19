package net.hackclient.ui.component;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.layout.LayoutManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.MathUtil;
import net.hackclient.ui.util.Rectangle;

/**
 * Collapsible panel with a bold title bar. Collapse is animated so the
 * body area slides open/closed. Used for grouping related modules in the
 * ClickGUI.
 */
public class CategoryContainer extends Panel {

    private final TextLabel title = new TextLabel();
    private final Panel body = new Panel();
    private boolean expanded = true;
    private final AnimationController expandAnim = new AnimationController(1f);
    private float measuredBodyHeight;

    public CategoryContainer(String name) {
        super();
        drawBorder(false);
        bg(new net.hackclient.ui.theme.Color(0,0,0,0));
        layout(LayoutManager.vertical(0f, 0f));
        title.text(name)
                .font(FontManager.FONT_PROXIMA_BOLD, 13f, true)
                .color(ThemeManager.current().textPrimary);
        body.drawBorder(false).bg(new net.hackclient.ui.theme.Color(0,0,0,0))
                .layout(LayoutManager.vertical(3f, 0f));
        super.add(title);
        super.add(body);
    }

    public Panel body() { return body; }
    public boolean expanded() { return expanded; }
    public void toggle() {
        expanded = !expanded;
        expandAnim.setTarget(expanded ? 1f : 0f, 0.22f);
    }

    @Override
    public UIComponent add(UIComponent child) { return body.add(child); }

    @Override
    public void update(float delta) {
        expandAnim.update(delta);
        // Measure the body for animation
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 13f, true);
        float titleH = Math.max(20f, f.lineHeight() + 8f);
        float bodyH = 0f;
        for (UIComponent c : body.children()) {
            bodyH += Math.max(c.bounds.h, c.preferredHeight(bounds.w - 8f));
        }
        bodyH += 3f * Math.max(0, body.children().size() - 1);
        measuredBodyHeight = bodyH;
        title.position(bounds.x, bounds.y);
        title.size(bounds.w, titleH);
        body.position(bounds.x, bounds.y + titleH);
        body.size(bounds.w, bodyH * expandAnim.value());
        bounds.h = titleH + bodyH * expandAnim.value();
        body.layout(LayoutManager.vertical(3f, 0f));
        super.update(delta);
    }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 13f, true);
        float titleH = Math.max(20f, f.lineHeight() + 8f);
        Rectangle titleRect = new Rectangle(bounds.x, bounds.y, bounds.w, titleH);
        switch (ev.type) {
            case MOUSE_PRESS:
                if (titleRect.contains(ev.x, ev.y) && ev.button == 0) {
                    toggle();
                    return true;
                }
                break;
            default: break;
        }
        return super.onInput(ev);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        // Background
        float r = theme.cornerRadius;
        ctx.fillRoundedRect(bounds.x, bounds.y, bounds.w, bounds.h, r, theme.panelBg.withAlpha(0.8f));
        // Title strip
        float titleH = Math.max(20f, FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 13f, true).lineHeight() + 8f);
        ctx.fillRoundedRect(bounds.x, bounds.y, bounds.w, titleH, r, theme.accent(0.6f).withAlpha(0.55f));
        // Expand chevron
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 11f);
        String chevron = expanded ? "▾" : "▸";
        ctx.drawString(f, chevron, bounds.x + bounds.w - 12f,
                ctx.textBaseline(f, bounds.y + (titleH - f.lineHeight()) * 0.5f),
                theme.textPrimary, true);
    }

    @Override
    public float preferredHeight(float forWidth) {
        float titleH = 24f;
        float bodyH = 0f;
        for (UIComponent c : body.children()) {
            bodyH += Math.max(c.preferredHeight(forWidth), c.bounds.h);
        }
        bodyH += 3f * Math.max(0, body.children().size() - 1);
        return titleH + bodyH * expandAnim.value();
    }
}
