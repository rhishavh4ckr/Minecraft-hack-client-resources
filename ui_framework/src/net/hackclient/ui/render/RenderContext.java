package net.hackclient.ui.render;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.util.MathUtil;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Draw surface abstraction used by all UI components.
 * <p>
 * The default implementation targets an AWT {@link Graphics2D} surface, which
 * works for HUD previews, screenshots, and software-rendered overlays. To
 * integrate with Minecraft's OpenGL pipeline, callers subclass this and
 * override the primitive methods (drawRect, drawGradientV, drawRoundedRect,
 * drawString) to issue GL quads instead.
 */
public class RenderContext implements AutoCloseable {

    protected final Graphics2D g;
    protected final int viewportWidth;
    protected final int viewportHeight;
    protected float scale = 1f;
    private final java.awt.Shape clipBackup;

    public RenderContext(Graphics2D g, int viewportWidth, int viewportHeight) {
        this.g = g;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        if (g != null) {
            clipBackup = g.getClip();
            // Sensible defaults for UI rendering. Subclasses may override.
            g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                    java.awt.RenderingHints.VALUE_RENDER_QUALITY);
        } else {
            clipBackup = null;
        }
    }

    public int width()  { return viewportWidth; }
    public int height() { return viewportHeight; }
    public float scale() { return scale; }
    public void setScale(float scale) { this.scale = Math.max(0.01f, scale); }
    /** Expose the underlying Graphics2D for low-level lines (e.g. checkbox ticks). */
    public Graphics2D graphics() { return g; }

    // ---------------------------- Primitive shapes ----------------------------

    public void fillRect(float x, float y, float w, float h, Color color) {
        if (color.a <= 0f || w <= 0f || h <= 0f) return;
        g.setColor(toAwt(color));
        g.fill(new Rectangle2D.Float(x, y, w, h));
    }

    /** Vertical gradient (top color → bottom color). */
    public void fillGradientV(float x, float y, float w, float h, Color top, Color bottom) {
        if (w <= 0f || h <= 0f) return;
        GradientPaint paint = new GradientPaint(x, y, toAwt(top), x, y + h, toAwt(bottom));
        java.awt.Paint prev = g.getPaint();
        g.setPaint(paint);
        g.fill(new Rectangle2D.Float(x, y, w, h));
        g.setPaint(prev);
    }

    public void drawRect(float x, float y, float w, float h, Color color, float stroke) {
        if (color.a <= 0f || w <= 0f || h <= 0f) return;
        java.awt.Stroke prev = g.getStroke();
        g.setStroke(new BasicStroke(stroke));
        g.setColor(toAwt(color));
        g.draw(new Rectangle2D.Float(x, y, w, h));
        g.setStroke(prev);
    }

    public void fillRoundedRect(float x, float y, float w, float h, float radius, Color color) {
        if (color.a <= 0f || w <= 0f || h <= 0f) return;
        float r = MathUtil.clamp(radius, 0f, Math.min(w, h) * 0.5f);
        g.setColor(toAwt(color));
        g.fill(new RoundRectangle2D.Float(x, y, w, h, r * 2f, r * 2f));
    }

    public void drawRoundedRect(float x, float y, float w, float h, float radius, Color color, float stroke) {
        if (color.a <= 0f || w <= 0f || h <= 0f) return;
        float r = MathUtil.clamp(radius, 0f, Math.min(w, h) * 0.5f);
        java.awt.Stroke prev = g.getStroke();
        g.setStroke(new BasicStroke(stroke));
        g.setColor(toAwt(color));
        g.draw(new RoundRectangle2D.Float(x, y, w, h, r * 2f, r * 2f));
        g.setStroke(prev);
    }

    // --------------------------------- Text -----------------------------------

    public float drawString(FontManager.CachedFont font, String text, float x, float y, Color color, boolean shadow) {
        if (font == null || text == null || text.isEmpty()) return 0f;
        return font.drawString(g, text, x, y, toAwt(color), shadow);
    }

    /** Returns the top-aligned y baseline for text drawn at (x,y) using this font. */
    public float textBaseline(FontManager.CachedFont font, float topY) {
        return topY + font.ascent();
    }

    // ------------------------------- Scissors ---------------------------------

    public void pushScissor(float x, float y, float w, float h) {
        g.clip(new Rectangle2D.Float(x, y, w, h));
    }

    public void popScissor() {
        g.setClip(clipBackup);
    }

    // ------------------------------- Utilities --------------------------------

    /** Draw a soft shadow around a rectangle, used by BaseWindow for depth. */
    public void drawShadow(float x, float y, float w, float h, float radius, float strength) {
        if (strength <= 0f) return;
        int steps = MathUtil.clampInt((int) radius, 1, 8);
        for (int i = steps; i > 0; i--) {
            float pad = i;
            float alpha = strength * (1f - (float) i / (steps + 1)) * 0.4f;
            g.setColor(new java.awt.Color(0, 0, 0, MathUtil.clampInt((int)(alpha * 255), 0, 255)));
            g.fill(new RoundRectangle2D.Float(x - pad, y - pad, w + pad * 2f, h + pad * 2f,
                    radius * 2f + pad, radius * 2f + pad));
        }
    }

    protected static java.awt.Color toAwt(Color c) {
        return new java.awt.Color(
                MathUtil.clampInt((int) (c.r * 255f + 0.5f), 0, 255),
                MathUtil.clampInt((int) (c.g * 255f + 0.5f), 0, 255),
                MathUtil.clampInt((int) (c.b * 255f + 0.5f), 0, 255),
                MathUtil.clampInt((int) (c.a * 255f + 0.5f), 0, 255));
    }

    @Override
    public void close() {
        if (g != null) {
            g.setClip(null);
        }
    }
}
