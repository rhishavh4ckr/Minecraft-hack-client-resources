package net.hackclient.ui.hud;

import net.hackclient.ui.render.RenderContext;

/**
 * A HUD overlay element (watermark, array list, scoreboard, custom text, ...).
 * Elements are positioned using fractional [0..1] coordinates against the
 * screen size (matching the schema in {@code hud.json}) and scaled by a
 * per-element factor so that user layouts transfer across resolutions.
 */
public abstract class HudElement {

    public enum HorizontalFacing { LEFT, RIGHT }
    public enum VerticalFacing   { UP, MIDDLE, DOWN }

    protected String id;
    protected float anchorX;       // 0..1 fraction of screen width
    protected float anchorY;       // 0..1 fraction of screen height
    protected float scale = 1f;
    protected HorizontalFacing hFacing = HorizontalFacing.LEFT;
    protected VerticalFacing   vFacing   = VerticalFacing.UP;
    protected boolean visible = true;

    public HudElement(String id) { this.id = id; }

    public String id() { return id; }
    public HudElement anchor(float x, float y) { this.anchorX = x; this.anchorY = y; return this; }
    public HudElement facing(HorizontalFacing h, VerticalFacing v) { this.hFacing = h; this.vFacing = v; return this; }
    public HudElement scale(float s) { this.scale = s; return this; }
    public boolean visible() { return visible; }
    public HudElement visible(boolean v) { this.visible = v; return this; }

    /** Render the element. Implementations receive the screen-space origin they should draw from. */
    public abstract void render(RenderContext ctx, int screenW, int screenH, float partialTicks);

    /** Width/height of the element in pixels (pre-scale) at the last rendered frame. */
    public abstract float lastWidth();
    public abstract float lastHeight();

    protected float originX(int screenW, float w) {
        float x = anchorX * screenW;
        switch (hFacing) {
            case RIGHT: return x - w * scale;
            default:    return x;
        }
    }

    protected float originY(int screenH, float h) {
        float y = anchorY * screenH;
        switch (vFacing) {
            case MIDDLE: return y - (h * scale) * 0.5f;
            case DOWN:   return y - h * scale;
            default:     return y;
        }
    }
}
