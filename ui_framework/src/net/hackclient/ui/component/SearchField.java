package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Single-line text input with a magnifying-glass icon character and an
 * animated underline in the accent color when focused.
 */
public class SearchField extends UIComponent {

    public interface Listener { void onTextChanged(SearchField source, String text); }

    private String text = "";
    private String placeholder = "Search...";
    private boolean focused;
    private int caret;
    private Listener listener;
    private final StringBuilder edit = new StringBuilder();

    public SearchField() { bounds.h = 20f; }
    public SearchField placeholder(String p) { this.placeholder = p; return this; }
    public String text() { return text; }
    public SearchField listener(Listener l) { this.listener = l; return this; }
    public SearchField text(String t) {
        this.text = t == null ? "" : t;
        this.edit.setLength(0);
        this.edit.append(this.text);
        this.caret = this.text.length();
        return this;
    }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        boolean over = bounds.contains(ev.x, ev.y);
        switch (ev.type) {
            case MOUSE_MOVE:
                hovered = over;
                hoverAnim.setTarget(hovered || focused ? 1f : 0f);
                return false;
            case MOUSE_PRESS:
                focused = over;
                hoverAnim.setTarget(focused ? 1f : 0f);
                return over;
            case CHAR_TYPE:
                if (focused && ev.character >= 32 && ev.character != 127) {
                    edit.insert(caret, ev.character);
                    caret++;
                    commit();
                    return true;
                }
                break;
            case KEY_PRESS:
                if (focused) {
                    switch (ev.keyCode) {
                        case 14: // backspace
                        case 259:
                            if (caret > 0) { edit.deleteCharAt(--caret); commit(); }
                            return true;
                        case 211: // delete
                            if (caret < edit.length()) { edit.deleteCharAt(caret); commit(); }
                            return true;
                        case 263: caret = Math.max(0, caret - 1); return true;
                        case 262: caret = Math.min(edit.length(), caret + 1); return true;
                        default: break;
                    }
                }
                break;
            default: break;
        }
        return false;
    }

    private void commit() {
        text = edit.toString();
        if (listener != null) listener.onTextChanged(this, text);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f);
        ctx.fillRoundedRect(bounds.x, bounds.y, bounds.w, bounds.h, theme.cornerRadius, theme.surfaceSunken);
        // Icon
        ctx.drawString(f, "🔍", bounds.x + 6f,
                ctx.textBaseline(f, bounds.y + (bounds.h - f.lineHeight()) * 0.5f),
                theme.textMuted, true);
        float iconW = f.stringWidth("🔍") + 10f;
        // Text
        String render = text.isEmpty() && !focused ? placeholder : text;
        Color tc = text.isEmpty() && !focused ? theme.textMuted : theme.textPrimary;
        ctx.drawString(f, render, bounds.x + iconW,
                ctx.textBaseline(f, bounds.y + (bounds.h - f.lineHeight()) * 0.5f), tc, true);
        // Focus underline
        float underlineH = 2f;
        float fillW = bounds.w * hoverAnim.progress();
        ctx.fillRect(bounds.x, bounds.y + bounds.h - underlineH, fillW, underlineH,
                theme.accent(0.5f));
        // Caret (blink simple)
        if (focused && (System.currentTimeMillis() / 500L) % 2 == 0) {
            String pre = text.substring(0, Math.min(caret, text.length()));
            float cx = bounds.x + iconW + f.stringWidth(pre);
            ctx.fillRect(cx, bounds.y + 4f, 1f, bounds.h - 8f, theme.textPrimary);
        }
    }

    @Override
    public float preferredHeight(float forWidth) { return 20f; }
    @Override
    public float preferredWidth(float forHeight) { return Math.max(100f, bounds.w); }
}
