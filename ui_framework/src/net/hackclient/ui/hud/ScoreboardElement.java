package net.hackclient.ui.hud;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Scoreboard-styled element with an optional translucent background.
 * Used for informational stats (FPS, CPS, coordinates, ping, ...).
 */
public class ScoreboardElement extends HudElement {

    public static final class Line {
        public final String left;
        public final String right;
        public Line(String left, String right) { this.left = left; this.right = right; }
    }

    private String title = "";
    private final List<Line> lines = new ArrayList<>();
    private Color textColor = Color.WHITE;
    private Color bgColor = new Color(0, 0, 0, 95/255f);
    private boolean bgRect = true;
    private float fontSize = 14f;
    private String fontId = FontManager.FONT_PROXIMA_NOVA;
    private float lastW, lastH;

    public ScoreboardElement(String id) { super(id); }

    public ScoreboardElement title(String t) { this.title = t == null ? "" : t; return this; }
    public ScoreboardElement add(Line l) { lines.add(l); return this; }
    public ScoreboardElement add(String left, String right) { lines.add(new Line(left, right)); return this; }
    public void clear() { lines.clear(); }
    public ScoreboardElement textColor(Color c) { this.textColor = c; return this; }
    public ScoreboardElement bgColor(Color c) { this.bgColor = c; return this; }
    public ScoreboardElement drawRect(boolean b) { this.bgRect = b; return this; }
    public ScoreboardElement font(String id, float size) { this.fontId = id; this.fontSize = size; return this; }

    @Override
    public void render(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        FontManager.CachedFont titleF = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, fontSize * scale, true);
        FontManager.CachedFont lineF  = FontManager.instance().get(fontId, (fontSize - 1f) * scale, false);
        float pad = 5f * scale;
        float rowH = lineF.lineHeight();
        float titleH = title.isEmpty() ? 0 : titleF.lineHeight();

        float maxLeftW = 0, maxRightW = 0;
        for (Line l : lines) {
            maxLeftW = Math.max(maxLeftW, lineF.stringWidth(l.left));
            maxRightW = Math.max(maxRightW, lineF.stringWidth(l.right));
        }
        float titleW = titleF.stringWidth(title);
        float w = Math.max(titleW, maxLeftW + maxRightW + 6f * scale) + pad * 2f;
        float h = titleH + rowH * lines.size() + pad * 2f + (title.isEmpty() ? 0 : 3f * scale);
        lastW = w;
        lastH = h;

        float ox = originX(screenW, w);
        float oy = originY(screenH, h);

        if (bgRect) {
            ctx.fillRect(ox, oy, w, h, bgColor);
        }

        float cy = oy + pad;
        if (!title.isEmpty()) {
            ctx.drawString(titleF, title, ox + (w - titleW) * 0.5f,
                    ctx.textBaseline(titleF, cy), textColor, true);
            cy += titleH + 3f * scale;
        }
        for (Line l : lines) {
            float lw = lineF.stringWidth(l.left);
            ctx.drawString(lineF, l.left, ox + pad, ctx.textBaseline(lineF, cy), textColor, true);
            ctx.drawString(lineF, l.right, ox + w - pad - lineF.stringWidth(l.right),
                    ctx.textBaseline(lineF, cy), textColor, true);
            cy += rowH;
        }
    }

    @Override public float lastWidth() { return lastW; }
    @Override public float lastHeight() { return lastH; }
}
