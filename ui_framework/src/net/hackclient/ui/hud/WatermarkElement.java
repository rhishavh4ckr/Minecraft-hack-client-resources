package net.hackclient.ui.hud;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.ColorPalette;
import net.hackclient.ui.util.MathUtil;

/**
 * VAPE v4-style wordmark: bold "VAPE" in the orange→red gradient with a
 * small outlined "v4" badge offset to the right. Adds a subtle breathing
 * pulse and a drop shadow.
 */
public class WatermarkElement extends HudElement {

    private final String brand = "VAPE";
    private final String badge = "v4";
    private final FontManager.CachedFont brandFont;
    private final FontManager.CachedFont badgeFont;
    private final AnimationController pulse = new AnimationController(0f);
    private float lastW, lastH;

    public WatermarkElement(String id) {
        super(id);
        this.brandFont = FontManager.instance().get(FontManager.FONT_ASSETSIO, 64f, true);
        this.badgeFont = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 28f, false);
        scale(0.75f);
    }

    @Override
    public void render(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        pulse.update(partialTicks);
        float time = (System.currentTimeMillis() & 0xFFFFFFFFL) * 0.001f;
        float breath = 0.02f * MathUtil.wave(time * 1.2f);

        float bw = brandFont.stringWidth(brand);
        float bh = brandFont.lineHeight();
        float badgeW = badgeFont.stringWidth(badge) + 12f;
        float badgeH = badgeFont.lineHeight() + 4f;

        float totalW = (bw + badgeW + 4f) * (scale + breath);
        float totalH = Math.max(bh, badgeH) * (scale + breath);
        this.lastW = totalW;
        this.lastH = totalH;

        ctx.setScale(scale + breath);
        float ox = originX(screenW, bw + badgeW + 4f);
        float oy = originY(screenH, Math.max(bh, badgeH));
        float sx = ox / (scale + breath);
        float sy = oy / (scale + breath);

        // Brand gradient: we draw per-character with an orange→red lerp, plus soft shadow
        float charX = sx;
        FontManager.CachedFont f = brandFont;
        for (int i = 0; i < brand.length(); i++) {
            String ch = String.valueOf(brand.charAt(i));
            float t = i / (float) (brand.length() - 1);
            Color c = Color.lerp(ColorPalette.ACCENT_ORANGE, ColorPalette.ACCENT_HIGHLIGHT, t);
            // Shadow
            ctx.drawString(f, ch, charX + 2f, ctx.textBaseline(f, sy) + 2f,
                    new Color(0, 0, 0, 0.55f), false);
            ctx.drawString(f, ch, charX, ctx.textBaseline(f, sy), c, false);
            charX += f.stringWidth(ch);
        }
        // Badge (outlined rectangle)
        float badgeX = sx + bw + 6f;
        float badgeY = sy + (bh - badgeH) * 0.5f;
        ctx.drawRoundedRect(badgeX, badgeY, badgeW, badgeH, 4f, ColorPalette.HUD_BADGE_OUTLINE, 2f);
        ctx.drawString(badgeFont, badge,
                badgeX + (badgeW - badgeFont.stringWidth(badge)) * 0.5f,
                ctx.textBaseline(badgeFont, badgeY + (badgeH - badgeFont.lineHeight()) * 0.5f),
                Color.WHITE, true);

        ctx.setScale(1f);
    }

    @Override public float lastWidth() { return lastW; }
    @Override public float lastHeight() { return lastH; }
}
