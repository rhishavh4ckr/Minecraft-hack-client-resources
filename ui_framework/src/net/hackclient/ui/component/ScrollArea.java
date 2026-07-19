package net.hackclient.ui.component;

import net.hackclient.ui.input.InputEvent;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.MathUtil;

/**
 * Vertical scrollable viewport around a single content panel. Draws a thin
 * scrollbar on the right edge when content overflows.
 */
public class ScrollArea extends UIComponent {

    private final Panel content = new Panel();
    private float scrollOffset;
    private float maxScroll;

    public ScrollArea() {
        content.drawBorder(false).bg(new net.hackclient.ui.theme.Color(0,0,0,0));
        super.add(content);
    }

    public Panel content() { return content; }

    @Override
    public UIComponent add(UIComponent child) { return content.add(child); }

    @Override
    public void update(float delta) {
        content.position(bounds.x + 2f, bounds.y + 2f - scrollOffset);
        content.size(bounds.w - 8f, content.bounds.h);
        float contentH = computeContentHeight();
        content.bounds.h = contentH;
        maxScroll = Math.max(0f, contentH - (bounds.h - 4f));
        scrollOffset = MathUtil.clamp(scrollOffset, 0f, maxScroll);
        super.update(delta);
    }

    private float computeContentHeight() {
        float h = 0f;
        for (UIComponent c : content.children()) {
            h += Math.max(c.preferredHeight(bounds.w - 8f), c.bounds.h) + 3f;
        }
        return h;
    }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        if (!bounds.contains(ev.x, ev.y)) return false;
        if (ev.type == net.hackclient.ui.input.InputEvent.Type.MOUSE_SCROLL) {
            scrollOffset = MathUtil.clamp(scrollOffset - ev.scrollDelta * 20f, 0f, maxScroll);
            return true;
        }
        // Translate coordinates for children by scroll offset before dispatch
        InputEvent translated = ev;
        if (maxScroll > 0f) {
            translated = new InputEvent(ev.type, ev.x, ev.y + scrollOffset);
            translated.button = ev.button;
            translated.scrollDelta = ev.scrollDelta;
            translated.keyCode = ev.keyCode;
            translated.character = ev.character;
        }
        return content.dispatch(translated);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        ctx.pushScissor(bounds.x, bounds.y, bounds.w, bounds.h);
        content.draw(ctx);
        ctx.popScissor();
        // Scrollbar
        if (maxScroll > 0f) {
            float barX = bounds.x + bounds.w - 3f;
            float barH = MathUtil.clamp((bounds.h - 4f) * ((bounds.h - 4f) / (content.bounds.h + 1f)), 14f, bounds.h - 4f);
            float trackH = bounds.h - 4f;
            float barY = bounds.y + 2f + (trackH - barH) * (scrollOffset / maxScroll);
            ctx.fillRoundedRect(barX, bounds.y + 2f, 2f, trackH, 1f, theme.surfaceSunken);
            ctx.fillRoundedRect(barX, barY, 2f, barH, 1f, theme.textMuted);
        }
    }

    @Override
    public float preferredWidth(float forHeight) { return bounds.w > 0 ? bounds.w : 160f; }
    @Override
    public float preferredHeight(float forWidth) { return bounds.h > 0 ? bounds.h : 200f; }
}
