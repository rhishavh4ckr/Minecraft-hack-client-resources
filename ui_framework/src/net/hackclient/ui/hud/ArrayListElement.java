package net.hackclient.ui.hud;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.ColorPalette;
import net.hackclient.ui.util.MathUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * VAPE v4-style {@code ArrayList}: a vertical list of enabled module names
 * anchored in a screen corner. Each row has a translucent black background
 * block sized to the text width, with a vertical orange→red accent rail on
 * the left. Text is rendered bold in white (or rainbow) with a drop shadow.
 * <p>
 * This is the element visible in both reference screenshots (the
 * AutoClicker/Fullbright/AntiBot/Aimbot/Sprint list).
 */
public class ArrayListElement extends HudElement {

    /** One displayed module. */
    public static final class Entry {
        public String name;
        public String tag;          // optional tag text rendered after the name, e.g. "Fly [V]".
        public int priority;        // higher priority → higher up when upper-anchored
        public Entry(String name, String tag, int priority) {
            this.name = name; this.tag = tag; this.priority = priority;
        }
    }

    public enum RectSide { LEFT, RIGHT, OFF }

    private final List<Entry> entries = new ArrayList<>();
    private String fontId = FontManager.FONT_PROXIMA_BOLD;
    private float fontSize = 16f;
    private boolean shadow = true;
    private boolean rainbow;
    private boolean upperCase;
    private RectSide rectSide = RectSide.LEFT;
    private float bgAlpha = 0.58f;
    private Color bgColorOverride;
    private float rowPadding = 6f;
    private float textYOffset;
    private float textHeight;
    private float lastW, lastH;

    public ArrayListElement bgColorWorkaround(Color c) { this.bgColorOverride = c; return this; }

    public ArrayListElement(String id) { super(id); }

    public ArrayListElement font(String id, float size) { this.fontId = id; this.fontSize = size; return this; }
    public ArrayListElement rainbow(boolean r) { this.rainbow = r; return this; }
    public ArrayListElement shadow(boolean s) { this.shadow = s; return this; }
    public ArrayListElement upperCase(boolean u) { this.upperCase = u; return this; }
    public ArrayListElement rectSide(RectSide s) { this.rectSide = s; return this; }
    public ArrayListElement bgAlpha(float a) { this.bgAlpha = MathUtil.clamp(a, 0f, 1f); return this; }
    public ArrayListElement rowPadding(float p) { this.rowPadding = p; return this; }
    public ArrayListElement textHeight(float h) { this.textHeight = h; return this; }
    public ArrayListElement textYOffset(float y) { this.textYOffset = y; return this; }

    public List<Entry> entries() { return entries; }

    public void setEntries(List<Entry> list) {
        entries.clear();
        entries.addAll(list);
        entries.sort(Comparator.<Entry>comparingInt(e -> -e.priority)
                .thenComparing(e -> -e.name.length()));
    }

    @Override
    public void render(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        if (entries.isEmpty()) { lastW = 0; lastH = 0; return; }

        float fSize = fontSize * scale;
        FontManager.CachedFont nameFont = FontManager.instance().get(fontId, fSize, true);
        float lineH = textHeight > 0 ? textHeight * scale : nameFont.lineHeight() * 1.1f;
        float padX = rowPadding * scale;

        // Determine max row width for alignment
        float maxNameW = 0;
        float maxTagW = 0;
        for (Entry e : entries) {
            String nm = upperCase ? e.name.toUpperCase() : e.name;
            maxNameW = Math.max(maxNameW, nameFont.stringWidth(nm));
            if (e.tag != null && !e.tag.isEmpty()) {
                maxTagW = Math.max(maxTagW, nameFont.stringWidth(" " + e.tag));
            }
        }
        float rowW = maxNameW + maxTagW + padX * 2f + (rectSide == RectSide.OFF ? 0f : 4f * scale);
        float totalH = lineH * entries.size();

        // Anchor origin
        float ox = originX(screenW, rowW);
        float oy = originY(screenH, totalH);
        lastW = rowW;
        lastH = totalH;

        // Draw left accent rail once spanning full height
        if (rectSide == RectSide.LEFT) {
            float railW = 4f * scale;
            ctx.fillGradientV(ox, oy, railW, totalH, ColorPalette.HUD_RAIL_TOP, ColorPalette.HUD_RAIL_BOTTOM);
        } else if (rectSide == RectSide.RIGHT) {
            float railW = 4f * scale;
            ctx.fillGradientV(ox + rowW - railW, oy, railW, totalH, ColorPalette.HUD_RAIL_TOP, ColorPalette.HUD_RAIL_BOTTOM);
        }

        float rowBg = new Color(0,0,0, bgAlpha).toARGB();
        Color bgColor = bgColorOverride != null ? bgColorOverride : new Color(0,0,0, bgAlpha);
        float rowX = ox + (rectSide == RectSide.LEFT ? 4f * scale : 0f);
        float innerW = rowW - (rectSide == RectSide.OFF ? 0f : 4f * scale);

        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            String nm = upperCase ? e.name.toUpperCase() : e.name;
            float y = oy + i * lineH;
            // Background block sized to this row's name + tag
            float nameW = nameFont.stringWidth(nm);
            float tagW = e.tag == null ? 0 : nameFont.stringWidth(" " + e.tag);
            float rowWforEntry = nameW + tagW + padX * 2f;
            float bx = rowX;
            // If facing right, align to right edge of the max width
            if (hFacing == HorizontalFacing.RIGHT) {
                bx = rowX + (innerW - rowWforEntry);
            }
            ctx.fillRect(bx, y, rowWforEntry, lineH - 1f, bgColor);
            // Text
            float textX = bx + padX;
            float baseY = ctx.textBaseline(nameFont, y + (lineH - nameFont.lineHeight()) * 0.5f + textYOffset * scale);
            Color textColor;
            if (rainbow) {
                float phase = (System.currentTimeMillis() * 0.002f + i * 0.6f);
                textColor = Color.fromHSB((phase % 1f + 1f) % 1f, 0.75f, 1f, 1f);
            } else {
                textColor = Color.lerp(ColorPalette.ACCENT_ORANGE, ColorPalette.ACCENT_RED, i / (float) Math.max(1, entries.size() - 1));
            }
            // Shadow
            ctx.drawString(nameFont, nm, textX + 1.2f * scale, baseY + 1.2f * scale, ColorPalette.TEXT_SHADOW, false);
            ctx.drawString(nameFont, nm, textX, baseY, textColor, false);
            if (e.tag != null && !e.tag.isEmpty()) {
                float tagX = textX + nameW;
                ctx.drawString(nameFont, " " + e.tag, tagX + 1.2f * scale, baseY + 1.2f * scale,
                        new Color(0,0,0,0.5f), false);
                ctx.drawString(nameFont, " " + e.tag, tagX, baseY, Color.WHITE, false);
            }
        }
    }

    @Override public float lastWidth() { return lastW; }
    @Override public float lastHeight() { return lastH; }
}
