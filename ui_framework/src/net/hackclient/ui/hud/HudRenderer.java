package net.hackclient.ui.hud;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Orchestrates every on-screen HUD element and the notification toasts.
 * The host mod calls {@link #render(RenderContext, int, int, float)} once
 * per frame from its GUI-overlay hook.
 */
public final class HudRenderer {

    private static final HudRenderer INSTANCE = new HudRenderer();

    private final List<HudElement> elements = new ArrayList<>();
    private final List<NotificationToast> toasts = new ArrayList<>();

    private HudRenderer() {
        // Default VAPE-style layout (top-left)
        WatermarkElement wm = new WatermarkElement("watermark");
        wm.anchor(0.04f, 0.04f).facing(HudElement.HorizontalFacing.LEFT, HudElement.VerticalFacing.UP).scale(0.75f);
        ArrayListElement al = new ArrayListElement("arraylist");
        al.anchor(0.04f, 0.18f).facing(HudElement.HorizontalFacing.LEFT, HudElement.VerticalFacing.UP)
                .font(FontManager.FONT_PROXIMA_BOLD, 18f)
                .rainbow(false).upperCase(false).rectSide(ArrayListElement.RectSide.LEFT)
                .rowPadding(8f);
        ScoreboardElement sb = new ScoreboardElement("scoreboard");
        sb.anchor(0.02f, 0.0f).facing(HudElement.HorizontalFacing.RIGHT, HudElement.VerticalFacing.UP)
                .title("Stats").drawRect(true).font(FontManager.FONT_PROXIMA_NOVA, 13f);
        elements.add(wm);
        elements.add(al);
        elements.add(sb);
    }

    public static HudRenderer instance() { return INSTANCE; }

    public List<HudElement> elements() { return elements; }

    public void add(HudElement e) { elements.add(e); }
    public void remove(HudElement id) { elements.removeIf(e -> e.id().equals(id.id())); }

    public ArrayListElement arrayList() {
        for (HudElement e : elements) if (e instanceof ArrayListElement) return (ArrayListElement) e;
        ArrayListElement al = new ArrayListElement("arraylist");
        elements.add(al);
        return al;
    }

    public void pushToast(String title, String body, NotificationToast.Type type, float duration) {
        toasts.add(new NotificationToast(title, body, type, duration));
    }

    public void render(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        for (HudElement e : elements) {
            if (!e.visible()) continue;
            e.render(ctx, screenW, screenH, partialTicks);
        }
        // Toasts stack top-right
        float pad = 12f;
        float y = pad;
        Iterator<NotificationToast> it = toasts.iterator();
        while (it.hasNext()) {
            NotificationToast t = it.next();
            t.update(partialTicks);
            if (t.isDead()) { it.remove(); continue; }
            float w = t.lastWidth();
            float x = screenW - w - pad;
            t.bounds().set(x, y, w, t.lastHeight());
            t.render(ctx, screenW, screenH, partialTicks);
            y += t.lastHeight() + pad;
        }
    }
}
