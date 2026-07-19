package net.hackclient.ui.component;

import net.hackclient.ui.layout.LayoutManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.Theme;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.Rectangle;

/**
 * Styled container with a background, rounded corners, padding and an
 * optional border. Panel delegates its layout to a {@link LayoutManager}
 * (vertical by default) so adding children is enough to get them positioned.
 */
public class Panel extends UIComponent {

    private LayoutManager layout = LayoutManager.vertical(4f, 6f);
    private Color bg;
    private Color border;
    private float cornerRadius = -1;
    private boolean drawBorder = true;

    public Panel() { }

    public Panel layout(LayoutManager m) { this.layout = m; return this; }
    public Panel bg(Color c) { this.bg = c; return this; }
    public Panel border(Color c) { this.border = c; return this; }
    public Panel cornerRadius(float r) { this.cornerRadius = r; return this; }
    public Panel drawBorder(boolean b) { this.drawBorder = b; return this; }

    @Override
    public void update(float delta) {
        relayout();
        super.update(delta);
    }

    private void relayout() {
        if (layout == null) return;
        Theme theme = ThemeManager.current();
        Rectangle inner = new Rectangle();
        float pad = theme.panelPadding;
        inner.copyFrom(bounds);
        inner.inset(pad);
        layout.layout(children, inner);
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        Theme theme = ThemeManager.current();
        Color bgCol = bg != null ? bg : theme.panelBg;
        Color borderCol = border != null ? border : theme.panelBorder;
        float r = cornerRadius >= 0 ? cornerRadius : theme.cornerRadius;

        if (opacity < 1f) bgCol = bgCol.withAlpha(bgCol.a * opacity);
        ctx.fillRoundedRect(bounds.x, bounds.y, bounds.w, bounds.h, r, bgCol);
        if (drawBorder) {
            Color bc = borderCol;
            if (opacity < 1f) bc = bc.withAlpha(bc.a * opacity);
            ctx.drawRoundedRect(bounds.x + 0.5f, bounds.y + 0.5f, bounds.w - 1f, bounds.h - 1f, r, bc, 1f);
        }
    }
}
