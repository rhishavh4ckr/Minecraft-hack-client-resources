package net.hackclient.ui.layout;

import net.hackclient.ui.util.Rectangle;

import java.util.List;

/**
 * Minimal layout engine. Components expose a desired minimum size; the
 * layout manager arranges them along an axis with consistent padding.
 * <p>
 * This intentionally avoids the complexity of Swing/AWT LayoutManagers – the
 * UI we render is a relatively flat HUD with a few panel types, so a
 * straightforward vertical/horizontal stack is the common case.
 */
public final class LayoutManager {

    public enum Axis { VERTICAL, HORIZONTAL }
    public enum Alignment { START, CENTER, END, STRETCH }

    private final Axis axis;
    private final Alignment crossAlignment;
    private final float gap;
    private final float padding;

    public LayoutManager(Axis axis, Alignment crossAlignment, float gap, float padding) {
        this.axis = axis;
        this.crossAlignment = crossAlignment;
        this.gap = gap;
        this.padding = padding;
    }

    public static LayoutManager vertical(float gap, float padding) {
        return new LayoutManager(Axis.VERTICAL, Alignment.STRETCH, gap, padding);
    }

    public static LayoutManager horizontal(float gap, float padding) {
        return new LayoutManager(Axis.HORIZONTAL, Alignment.CENTER, gap, padding);
    }

    /**
     * Lay out {@code children} inside {@code bounds}, writing their
     * {@code bounds} rectangles in place.
     */
    public <T extends LayoutChild> void layout(List<T> children, Rectangle bounds) {
        if (children == null || children.isEmpty()) return;

        float x0 = bounds.x + padding;
        float y0 = bounds.y + padding;
        float innerW = bounds.w - padding * 2f;
        float innerH = bounds.h - padding * 2f;

        // Vertical stack
        if (axis == Axis.VERTICAL) {
            float cursor = y0;
            float totalFixed = 0f;
            int stretchCount = 0;
            for (T c : children) {
                float pref = c.preferredHeight(innerW);
                if (pref < 0) stretchCount++;
                else totalFixed += pref;
            }
            float availableStretch = Math.max(0, innerH - totalFixed - gap * (children.size() - 1));
            float stretchSize = stretchCount > 0 ? availableStretch / stretchCount : 0;

            for (T c : children) {
                float pref = c.preferredHeight(innerW);
                float h = pref < 0 ? stretchSize : pref;
                float w;
                switch (crossAlignment) {
                    case STRETCH:
                    default:
                        w = innerW;
                        break;
                    case CENTER:
                        w = Math.min(innerW, c.preferredWidth(h));
                        break;
                    case START:
                    case END:
                        w = c.preferredWidth(h);
                        break;
                }
                float x = x0;
                if (crossAlignment == Alignment.CENTER) x = x0 + (innerW - w) * 0.5f;
                else if (crossAlignment == Alignment.END) x = x0 + innerW - w;
                c.getBounds().set(x, cursor, w, h);
                cursor += h + gap;
            }
        } else {
            // Horizontal stack
            float cursor = x0;
            float totalFixed = 0f;
            int stretchCount = 0;
            for (T c : children) {
                float pref = c.preferredWidth(-1);
                if (pref < 0) stretchCount++;
                else totalFixed += pref;
            }
            float availableStretch = Math.max(0, innerW - totalFixed - gap * (children.size() - 1));
            float stretchSize = stretchCount > 0 ? availableStretch / stretchCount : 0;

            for (T c : children) {
                float w = c.preferredWidth(-1);
                if (w < 0) w = stretchSize;
                float h = c.preferredHeight(w);
                float y = y0;
                if (crossAlignment == Alignment.CENTER) y = y0 + (innerH - h) * 0.5f;
                else if (crossAlignment == Alignment.END) y = y0 + innerH - h;
                c.getBounds().set(cursor, y, w, h);
                cursor += w + gap;
            }
        }
    }

    /** Interface every child that participates in layout must implement. */
    public interface LayoutChild {
        Rectangle getBounds();
        /** Preferred width given a height (or -1 if unknown). Return negative to stretch. */
        float preferredWidth(float forHeight);
        /** Preferred height given a width (or -1 if unknown). Return negative to stretch. */
        float preferredHeight(float forWidth);
    }
}
