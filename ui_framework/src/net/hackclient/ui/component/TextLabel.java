package net.hackclient.ui.component;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.ColorPalette;

/**
 * Single-line text label. Color defaults to primary text. Supports shadow
 * for contrast over game backgrounds.
 */
public class TextLabel extends UIComponent {

    public enum Alignment { LEFT, CENTER, RIGHT }

    private String text = "";
    private String fontId = FontManager.FONT_PROXIMA_NOVA;
    private float fontSize = 14f;
    private boolean bold = false;
    private boolean shadow = true;
    private Alignment alignment = Alignment.LEFT;
    private Color color = ColorPalette.TEXT_PRIMARY;

    public TextLabel() { }
    public TextLabel(String text) { this.text = text; }

    public String text() { return text; }
    public TextLabel text(String text) { this.text = text == null ? "" : text; return this; }
    public TextLabel font(String id, float size, boolean bold) {
        this.fontId = id; this.fontSize = size; this.bold = bold; return this;
    }
    public TextLabel color(Color c) { this.color = c; return this; }
    public TextLabel shadow(boolean s) { this.shadow = s; return this; }
    public TextLabel alignment(Alignment a) { this.alignment = a; return this; }

    @Override
    protected void onDraw(RenderContext ctx) {
        FontManager.CachedFont font = FontManager.instance().get(fontId, fontSize, bold);
        float tw = font.stringWidth(text);
        float x = bounds.x;
        float y = ctx.textBaseline(font, bounds.y);
        switch (alignment) {
            case CENTER: x += (bounds.w - tw) * 0.5f; break;
            case RIGHT:  x += bounds.w - tw; break;
            default: break;
        }
        if (opacity < 1f) {
            ctx.drawString(font, text, x, y, color.withAlpha((int)(color.a * 255 * opacity)), shadow);
        } else {
            ctx.drawString(font, text, x, y, color, shadow);
        }
    }

    @Override
    public float preferredWidth(float forHeight) {
        return FontManager.instance().get(fontId, fontSize, bold).stringWidth(text);
    }

    @Override
    public float preferredHeight(float forWidth) {
        return FontManager.instance().get(fontId, fontSize, bold).lineHeight();
    }
}
