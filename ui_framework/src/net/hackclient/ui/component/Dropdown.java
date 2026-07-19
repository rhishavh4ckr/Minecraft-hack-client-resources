package net.hackclient.ui.component;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.MathUtil;
import net.hackclient.ui.util.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Compact dropdown/select control. Opens downward with a simple animated
 * reveal. Items are plain strings; selection callbacks notify the host.
 */
public class Dropdown extends UIComponent {

    public interface Listener { void onSelected(Dropdown source, int index, String value); }

    private String label = "";
    private final List<String> items = new ArrayList<>();
    private int selected = 0;
    private boolean open = false;
    private Listener listener;
    private final AnimationController openAnim = new AnimationController(0f);
    private int hoverIndex = -1;
    private float itemHeight = 18f;

    public Dropdown(String label) { this.label = label == null ? "" : label; bounds.h = 20f; }
    public Dropdown items(String... vals) { items.addAll(Arrays.asList(vals)); return this; }
    public Dropdown items(List<String> vals) { items.addAll(vals); return this; }
    public Dropdown select(int idx) { this.selected = MathUtil.clampInt(idx, 0, Math.max(0, items.size() - 1)); return this; }
    public Dropdown listener(Listener l) { this.listener = l; return this; }
    public int selectedIndex() { return selected; }
    public String selectedValue() { return items.isEmpty() ? "" : items.get(selected); }

    @Override
    public void update(float delta) {
        super.update(delta);
        openAnim.update(delta);
    }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        float mx = ev.x, my = ev.y;
        boolean overHead = new Rectangle(bounds.x, bounds.y, bounds.w, bounds.h).contains(mx, my);
        // Dropdown list bounds when fully open
        float listH = itemHeight * Math.max(0, items.size()) + 4f;
        float listY = bounds.y + bounds.h;
        Rectangle listRect = new Rectangle(bounds.x, listY, bounds.w, listH * openAnim.value());
        boolean overList = openAnim.value() > 0.01f && listRect.contains(mx, my);

        switch (ev.type) {
            case MOUSE_MOVE:
                hovered = overHead;
                hoverAnim.setTarget(hovered ? 1f : 0f);
                if (overList) {
                    float relY = my - (listY + 2f);
                    hoverIndex = MathUtil.clampInt((int)(relY / itemHeight), 0, items.size() - 1);
                } else {
                    hoverIndex = -1;
                }
                return false;
            case MOUSE_PRESS:
                if (overHead && ev.button == 0) {
                    open = !open;
                    openAnim.setTarget(open ? 1f : 0f, 0.16f);
                    return true;
                }
                if (overList && ev.button == 0 && hoverIndex >= 0) {
                    selected = hoverIndex;
                    open = false;
                    openAnim.setTarget(0f, 0.16f);
                    if (listener != null) listener.onSelected(this, selected, items.get(selected));
                    return true;
                }
                // outside click closes
                if (open) {
                    open = false;
                    openAnim.setTarget(0f, 0.16f);
                }
                break;
            default: break;
        }
        return false;
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f);
        // Head
        Color headBg = Color.lerp(theme.surfaceSunken, theme.accent(0.2f), hoverAnim.progress() * 0.5f);
        if (opacity < 1f) headBg = headBg.withAlpha(headBg.a * opacity);
        ctx.fillRoundedRect(bounds.x, bounds.y, bounds.w, bounds.h, theme.cornerRadius, headBg);
        ctx.drawRoundedRect(bounds.x + 0.5f, bounds.y + 0.5f, bounds.w - 1f, bounds.h - 1f, theme.cornerRadius,
                theme.panelBorder, 1f);
        String labelText = label + ": " + (items.isEmpty() ? "" : items.get(selected));
        ctx.drawString(f, labelText, bounds.x + 6f, ctx.textBaseline(f, bounds.y + (bounds.h - f.lineHeight()) * 0.5f),
                theme.textPrimary, true);
        // Chevron
        ctx.drawString(f, open ? "▲" : "▼",
                bounds.x + bounds.w - 12f,
                ctx.textBaseline(f, bounds.y + (bounds.h - f.lineHeight()) * 0.5f),
                theme.textMuted, true);

        // Drop list
        float openT = openAnim.value();
        if (openT > 0.01f) {
            float listH = itemHeight * items.size() + 4f;
            float dispH = listH * openT;
            float listY = bounds.y + bounds.h;
            ctx.pushScissor(bounds.x, listY, bounds.w, dispH);
            ctx.fillRoundedRect(bounds.x, listY, bounds.w, listH, theme.cornerRadius, theme.windowBg);
            ctx.drawRoundedRect(bounds.x + 0.5f, listY + 0.5f, bounds.w - 1f, listH - 1f, theme.cornerRadius,
                    theme.panelBorder, 1f);
            for (int i = 0; i < items.size(); i++) {
                float iy = listY + 2f + i * itemHeight;
                if (i == hoverIndex) {
                    ctx.fillRect(bounds.x + 2f, iy, bounds.w - 4f, itemHeight - 1f, theme.accent(0.3f).withAlpha(0.35f));
                }
                Color ic = i == selected ? theme.accent(0.5f) : theme.textSecondary;
                ctx.drawString(f, items.get(i), bounds.x + 6f,
                        ctx.textBaseline(f, iy + (itemHeight - f.lineHeight()) * 0.5f), ic, true);
            }
            ctx.popScissor();
        }
    }

    @Override
    public float preferredHeight(float forWidth) { return bounds.h; }
    @Override
    public float preferredWidth(float forHeight) { return Math.max(120f, bounds.w); }
}
