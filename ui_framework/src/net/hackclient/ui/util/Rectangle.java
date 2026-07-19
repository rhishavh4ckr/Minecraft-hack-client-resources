package net.hackclient.ui.util;

/**
 * Allocation-free rectangle used for component bounds, hit-testing, and
 * layout passes. Uses mutable fields to avoid GC churn from 60fps layouts.
 */
public final class Rectangle {
    public float x, y, w, h;

    public Rectangle() { }

    public Rectangle(float x, float y, float w, float h) { set(x, y, w, h); }

    public void set(float x, float y, float w, float h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public void copyFrom(Rectangle r) { set(r.x, r.y, r.w, r.h); }

    public boolean contains(float px, float py) {
        return px >= x && px < x + w && py >= y && py < y + h;
    }

    public float right()  { return x + w; }
    public float bottom() { return y + h; }

    public Rectangle inset(float pad) {
        x += pad; y += pad; w -= pad * 2f; h -= pad * 2f;
        return this;
    }

    public Rectangle offset(float dx, float dy) {
        x += dx; y += dy;
        return this;
    }

    @Override
    public String toString() {
        return "Rect[" + x + "," + y + "," + w + "x" + h + "]";
    }
}
