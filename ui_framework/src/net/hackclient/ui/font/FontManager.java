package net.hackclient.ui.font;

import net.hackclient.ui.util.MathUtil;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Loads and caches TTF fonts and pre-rasterized glyph pages.
 * <p>
 * Because we target a Minecraft mod environment we expose fonts as AWT
 * {@link Font} objects that can be consumed both by our own software renderer
 * (for 2D HUD overlays drawn on {@link BufferedImage}) and by OpenGL-based
 * renderers that upload each glyph atlas as a texture.
 * <p>
 * The manager ships with the three reference fonts (Proxima Nova regular/bold
 * and Assetsio display font) loaded from {@code /fonts/} on the classpath,
 * matching the {@code files.zip/fonts/fonts.json} manifest.
 */
public final class FontManager {

    public static final String FONT_PROXIMA_NOVA = "proxima";
    public static final String FONT_PROXIMA_BOLD = "proximabd";
    public static final String FONT_ASSETSIO    = "assetsio";
    public static final String FONT_MINECRAFT   = "minecraft";

    private static final FontManager INSTANCE = new FontManager();

    private final Map<FontKey, CachedFont> cache = new HashMap<>();

    private FontManager() {
        registerBundled(FONT_PROXIMA_NOVA, "/fonts/proxima.ttf");
        registerBundled(FONT_PROXIMA_BOLD, "/fonts/proximabd.ttf");
        registerBundled(FONT_ASSETSIO,    "/fonts/assetsio.ttf");
        // Minecraft default is mapped to a built-in font when the game is not
        // available; the integration layer overrides this in a real MC env.
        registerFallback(FONT_MINECRAFT, new Font(Font.MONOSPACED, Font.PLAIN, 16));
    }

    public static FontManager instance() { return INSTANCE; }

    private void registerBundled(String id, String resourcePath) {
        try (InputStream in = FontManager.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                registerFallback(id, new Font(Font.SANS_SERIF, Font.PLAIN, 16));
                return;
            }
            Font base = Font.createFont(Font.TRUETYPE_FONT, in);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base);
            cache.put(new FontKey(id, 16f, false), new CachedFont(base.deriveFont(16f)));
        } catch (FontFormatException | IOException e) {
            registerFallback(id, new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        }
    }

    private void registerFallback(String id, Font font) {
        cache.put(new FontKey(id, 16f, false), new CachedFont(font));
    }

    /** Retrieve a derived font at the given point size, using the bold variant when requested. */
    public CachedFont get(String id, float sizePt, boolean bold) {
        String lookupId = bold && !id.equals(FONT_PROXIMA_BOLD) ? FONT_PROXIMA_BOLD : id;
        FontKey key = new FontKey(lookupId, sizePt, bold);
        CachedFont cf = cache.get(key);
        if (cf != null) return cf;
        CachedFont base = cache.get(new FontKey(lookupId, 16f, false));
        if (base == null) base = cache.get(new FontKey(FONT_PROXIMA_NOVA, 16f, false));
        Font derived = base.font.deriveFont(bold ? Font.BOLD : Font.PLAIN, sizePt);
        cf = new CachedFont(derived);
        cache.put(key, cf);
        return cf;
    }

    public CachedFont get(String id, float sizePt) { return get(id, sizePt, false); }

    /**
     * A font instance bound to a specific point size, with cached text
     * measurements to avoid {@link GlyphVector} allocations every frame.
     */
    public static final class CachedFont {
        public final Font font;
        private final Map<String, Float> widthCache  = new HashMap<>(256);
        private final Map<String, Float> heightCache = new HashMap<>(8);
        private final FontRenderContext  frc;

        CachedFont(Font font) {
            this.font = font;
            this.frc = new FontRenderContext(null,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }

        public float stringWidth(String text) {
            if (text == null || text.isEmpty()) return 0f;
            Float cached = widthCache.get(text);
            if (cached != null) return cached;
            Rectangle2D bounds = font.getStringBounds(text, frc);
            float w = (float) bounds.getWidth();
            if (widthCache.size() > 4096) widthCache.clear(); // prevent pathological growth
            widthCache.put(text, w);
            return w;
        }

        public float lineHeight() {
            return font.getLineMetrics("Ag", frc).getHeight();
        }

        public float ascent() { return font.getLineMetrics("Ag", frc).getAscent(); }
        public float descent() { return font.getLineMetrics("Ag", frc).getDescent(); }

        /** Paint {@code text} onto a graphics surface at the given baseline, returning the advance width. */
        public float drawString(Graphics2D g, String text, float x, float y, java.awt.Color color, boolean shadow) {
            if (text == null || text.isEmpty()) return 0f;
            g.setFont(font);
            if (shadow) {
                java.awt.Color shadowCol = new java.awt.Color(0, 0, 0, MathUtil.clampInt((int)(color.getAlpha() * 0.55f), 0, 255));
                g.setColor(shadowCol);
                g.drawString(text, x + 1.5f, y + 1.5f);
            }
            g.setColor(color);
            g.drawString(text, x, y);
            return stringWidth(text);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CachedFont)) return false;
            return Objects.equals(font, ((CachedFont) o).font);
        }

        @Override
        public int hashCode() { return font.hashCode(); }
    }

    private static final class FontKey {
        final String id;
        final float size;
        final boolean bold;
        FontKey(String id, float size, boolean bold) {
            this.id = id; this.size = size; this.bold = bold;
        }
        @Override public boolean equals(Object o) {
            if (!(o instanceof FontKey)) return false;
            FontKey k = (FontKey) o;
            return Float.compare(k.size, size) == 0 && bold == k.bold && Objects.equals(id, k.id);
        }
        @Override public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + Float.floatToIntBits(size);
            result = 31 * result + (bold ? 1 : 0);
            return result;
        }
    }
}
