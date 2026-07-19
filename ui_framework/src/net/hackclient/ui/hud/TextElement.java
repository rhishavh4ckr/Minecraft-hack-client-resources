package net.hackclient.ui.hud;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;

/**
 * Arbitrary single-line HUD text element, supporting optional rainbow
 * coloring (animated over time) and a drop shadow. Mirrors the "Text"
 * entries from {@code hud.json}.
 */
public class TextElement extends HudElement {

    private String text = "";
    private Color color = Color.WHITE;
    private boolean shadow = true;
    private boolean rainbow;
    private float rainbowSpeed = 2000f; // period in ms
    private String fontId = FontManager.FONT_ASSETSIO;
    private float fontSize = 64f;
    private boolean bold;
    private float lastW, lastH;

    public TextElement(String id) { super(id); }

    public TextElement text(String t) { this.text = t == null ? "" : t; return this; }
    public TextElement color(Color c) { this.color = c; return this; }
    public TextElement shadow(boolean s) { this.shadow = s; return this; }
    public TextElement rainbow(boolean r) { this.rainbow = r; return this; }
    public TextElement font(String id, float size, boolean bold) {
        this.fontId = id; this.fontSize = size; this.bold = bold; return this;
    }

    @Override
    public void render(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        FontManager.CachedFont f = FontManager.instance().get(fontId, fontSize * scale, bold);
        float w = f.stringWidth(text);
        float h = f.lineHeight();
        lastW = w;
        lastH = h;
        float ox = originX(screenW, w / Math.max(scale, 0.001f));
        float oy = originY(screenH, h / Math.max(scale, 0.001f));
        Color drawColor;
        if (rainbow) {
            float phase = (System.currentTimeMillis() % (long) rainbowSpeed) / rainbowSpeed;
            drawColor = Color.fromHSB(phase, 0.8f, 1f, 1f);
        } else {
            drawColor = color;
        }
        ctx.drawString(f, text, ox, ctx.textBaseline(f, oy), drawColor, shadow);
    }

    @Override public float lastWidth() { return lastW; }
    @Override public float lastHeight() { return lastH; }
}
