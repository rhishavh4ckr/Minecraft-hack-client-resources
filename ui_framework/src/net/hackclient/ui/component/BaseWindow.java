package net.hackclient.ui.component;

import net.hackclient.ui.layout.LayoutManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Top-level floating window with a title bar, a drop shadow and a
 * vertical-stacking body area. Subclass to build menus, ClickGUIs, etc.
 */
public class BaseWindow extends Panel {

    public static final float TITLE_HEIGHT = 28f;

    private final Panel titleBar = new Panel();
    private final TextLabel titleLabel = new TextLabel();
    private final Panel content = new Panel();
    private boolean dragging;
    private float dragDX, dragDY;

    public BaseWindow(String title) {
        titleLabel.text(title).font("proximabd", 14f, true)
                .color(ThemeManager.current().textPrimary)
                .alignment(TextLabel.Alignment.LEFT);
        titleBar.drawBorder(false).bg(new Color(0,0,0,0))
                .layout(LayoutManager.horizontal(6f, 8f));
        titleBar.add(titleLabel);
        content.drawBorder(false).bg(new Color(0,0,0,0))
                .layout(LayoutManager.vertical(4f, 8f));
        // Inline title + content manually so the content area fills the rest.
        super.add(titleBar);
        super.add(content);
        cornerRadius(6f);
    }

    public Panel content() { return content; }
    public String title() { return titleLabel.text(); }
    public BaseWindow title(String t) { titleLabel.text(t); return this; }

    @Override
    public void update(float delta) {
        Theme theme = ThemeManager.current();
        titleBar.position(bounds.x, bounds.y);
        titleBar.size(bounds.w, TITLE_HEIGHT);
        content.position(bounds.x, bounds.y + TITLE_HEIGHT);
        content.size(bounds.w, bounds.h - TITLE_HEIGHT);
        content.getBounds().set(bounds.x, bounds.y + TITLE_HEIGHT, bounds.w, Math.max(0, bounds.h - TITLE_HEIGHT));
        // Trigger content layout
        content.layout(LayoutManager.vertical(4f, 8f));
        super.update(delta);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        // Shadow
        ctx.drawShadow(bounds.x, bounds.y, bounds.w, bounds.h, theme.cornerRadius + 6, theme.shadowStrength);
        // Background
        Color bg = theme.windowBg;
        if (opacity < 1f) bg = bg.withAlpha(bg.a * opacity);
        ctx.fillRoundedRect(bounds.x, bounds.y, bounds.w, bounds.h, theme.cornerRadius + 2f, bg);
        // Border
        ctx.drawRoundedRect(bounds.x + 0.5f, bounds.y + 0.5f, bounds.w - 1f, bounds.h - 1f,
                theme.cornerRadius + 2f, theme.panelBorder, 1f);
        // Title bar accent stripe
        ctx.fillRoundedRect(bounds.x + 4f, bounds.y + 4f, bounds.w - 8f, 2f, 1f,
                theme.accent(hoverAnim.value()));
    }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        switch (ev.type) {
            case MOUSE_PRESS:
                if (titleBar.bounds().contains(ev.x, ev.y)) {
                    dragging = true;
                    dragDX = ev.x - bounds.x;
                    dragDY = ev.y - bounds.y;
                    return true;
                }
                break;
            case MOUSE_RELEASE:
                dragging = false;
                break;
            case MOUSE_MOVE:
                if (dragging) {
                    position(ev.x - dragDX, ev.y - dragDY);
                    return true;
                }
                break;
            default: break;
        }
        return super.onInput(ev);
    }
}
