package net.hackclient.ui.component;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.input.InputEvent;
import net.hackclient.ui.layout.LayoutManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for every UI element.
 * <p>
 * Components form a strict tree. A component owns its bounds rectangle, an
 * opacity animation (for fade in/out), a hover animation, and an arbitrary
 * list of children. Subclasses override {@link #onDraw(RenderContext)} to
 * paint themselves; child painting is handled automatically before/after as
 * required.
 */
public abstract class UIComponent implements LayoutManager.LayoutChild {

    protected Rectangle bounds = new Rectangle();
    protected final List<UIComponent> children = new ArrayList<>();
    protected UIComponent parent;
    protected boolean visible = true;
    protected boolean hovered;
    protected boolean pressed;
    protected float opacity = 1f;
    protected String id;

    protected final AnimationController hoverAnim = new AnimationController(0f);
    protected final AnimationController appearAnim = new AnimationController(1f);

    public UIComponent() {
        appearAnim.setValueInstant(0f);
        appearAnim.setTarget(1f, 0.2f);
    }

    public String id() { return id; }
    public UIComponent id(String id) { this.id = id; return this; }

    public boolean visible() { return visible; }
    public UIComponent visible(boolean v) { this.visible = v; return this; }

    public Rectangle bounds() { return bounds; }

    public UIComponent size(float w, float h) {
        bounds.w = w; bounds.h = h; return this;
    }

    public UIComponent position(float x, float y) {
        bounds.x = x; bounds.y = y; return this;
    }

    public UIComponent add(UIComponent child) {
        child.parent = this;
        children.add(child);
        return this;
    }

    public void remove(UIComponent child) {
        children.remove(child);
        child.parent = null;
    }

    public void clearChildren() {
        for (UIComponent c : children) c.parent = null;
        children.clear();
    }

    public List<UIComponent> children() { return children; }

    /** Update animations; call once per frame from the host renderer. */
    public void update(float delta) {
        hoverAnim.update(delta);
        appearAnim.update(delta);
        opacity = appearAnim.value();
        for (int i = 0, n = children.size(); i < n; i++) {
            children.get(i).update(delta);
        }
    }

    /** Draw this component and its children. */
    public final void draw(RenderContext ctx) {
        if (!visible) return;
        if (opacity <= 0f) return;
        onDraw(ctx);
        drawChildren(ctx);
    }

    /** Hook that subclasses implement for their own rendering. Children are drawn automatically. */
    protected void onDraw(RenderContext ctx) { /* default: nothing */ }

    protected void drawChildren(RenderContext ctx) {
        for (int i = 0, n = children.size(); i < n; i++) {
            UIComponent c = children.get(i);
            c.draw(ctx);
        }
    }

    /** Hit-test: returns the topmost visible component under (px, py) in screen coords. */
    public UIComponent hitTest(float px, float py) {
        if (!visible || opacity <= 0f) return null;
        for (int i = children.size() - 1; i >= 0; i--) {
            UIComponent hit = children.get(i).hitTest(px, py);
            if (hit != null) return hit;
        }
        return bounds.contains(px, py) ? this : null;
    }

    /** Route an input event to the correct component. Returns true if consumed. */
    public boolean dispatch(InputEvent ev) {
        if (!visible) return false;
        boolean handled = onInput(ev);
        if (handled) return true;
        for (int i = children.size() - 1; i >= 0; i--) {
            if (children.get(i).dispatch(ev)) return true;
        }
        return false;
    }

    protected boolean onInput(InputEvent ev) {
        switch (ev.type) {
            case MOUSE_MOVE:
                hovered = bounds.contains(ev.x, ev.y);
                hoverAnim.setTarget(hovered ? 1f : 0f);
                return false;
            case MOUSE_PRESS:
                if (bounds.contains(ev.x, ev.y)) { pressed = true; return true; }
                break;
            case MOUSE_RELEASE:
                pressed = false;
                break;
            default: break;
        }
        return false;
    }

    // ---- LayoutChild ----

    @Override public Rectangle getBounds() { return bounds; }

    @Override
    public float preferredWidth(float forHeight) {
        return bounds.w > 0 ? bounds.w : -1;
    }

    @Override
    public float preferredHeight(float forWidth) {
        return bounds.h > 0 ? bounds.h : -1;
    }
}
