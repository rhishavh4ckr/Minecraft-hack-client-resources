package net.hackclient.ui.gui;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.component.ConfettiBackground;
import net.hackclient.ui.component.UIComponent;
import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.LionPalette;
import net.hackclient.ui.theme.ThemeManager;
import net.hackclient.ui.util.MathUtil;
import net.hackclient.ui.util.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The new flagship ClickGUI modelled on the LionClient reference:
 *
 * <pre>
 *  ┌ LionClient v1.0.5 ─ [ COMBAT | MOVEMENT | CLIENT | RENDER | PLAYER | MISC ] ──┐
 *  ├──────────────┬─────────────────────────────────────────────────────────────────┤
 *  │ AntiFireball │ Clutch                                                        │
 *  │ Clutch  ◄──  │ Bridges blocks back to safety when knocked off an edge         │
 *  │ ...          │  □ [Enabled]                        [ Trigger ▼ ]              │
 *  │              │  Blocks                                              [ 1.0 ]   │
 *  │              │  ▣ Silent Aim                                                 │
 *  │              │  ▣ Return To Slot                                             │
 *  │              │  Clutch Move Delay                                [ 0 ]        │
 *  │              │  Max Blocks                                       [ 10 ]       │
 *  └──────────────┴─────────────────────────────────────────────────────────────────┘
 * </pre>
 *
 * <p>The GUI paints itself directly from a {@link RenderContext} so it can be
 * reused from the Minecraft HUD hook, the Swing preview, or an OpenGL
 * backend. Host code must route input via the public {@code onXxx} methods.
 */
public final class LionClickGui extends UIComponent {

    /** One category tab across the top bar. */
    public static final class Category {
        public final String name;
        public final List<Module> modules = new ArrayList<>();
        public Category(String name) { this.name = name; }
    }

    /** A module entry in the left column with zero or more settings. */
    public static final class Module {
        public final String name;
        public String description = "";
        public boolean enabled;
        public final List<Setting> settings = new ArrayList<>();
        public Module(String name) { this.name = name; }
        public Module describe(String d) { this.description = d; return this; }
        public Module add(Setting s) { settings.add(s); return this; }
    }

    /** A settings row on the right side. */
    public static abstract class Setting {
        public final String name;
        public Setting(String name) { this.name = name; }
        public abstract float preferredHeight(float width);
        public abstract void draw(RenderContext ctx, Rectangle bounds);
        public abstract boolean mouseClick(float x, float y, int btn, Rectangle bounds);
        public abstract void mouseRelease(float x, float y, int btn, Rectangle bounds);
        public abstract void mouseDrag(float x, float y, Rectangle bounds);
    }

    /** Boolean checkbox setting. */
    public static final class BoolSetting extends Setting {
        public boolean value;
        public BoolSetting(String n, boolean v) { super(n); this.value = v; }
        public float preferredHeight(float width) { return 38f; }
        public void draw(RenderContext ctx, Rectangle b) {
            FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 13f);
            ctx.fillRect(b.x, b.y, b.w, b.h, LionPalette.ROW_BG);
            ctx.drawRect(b.x + 0.5f, b.y + 0.5f, b.w - 1f, b.h - 1f, LionPalette.ROW_BORDER, 1f);
            float pad = 12f;
            float box = 16f;
            float bx = b.x + pad, by = b.y + (b.h - box) * 0.5f;
            ctx.fillRect(bx, by, box, box, value ? LionPalette.ACCENT : LionPalette.SURFACE_SUNKEN);
            ctx.drawRect(bx + 0.5f, by + 0.5f, box - 1f, box - 1f, value ? LionPalette.ACCENT_DARK : LionPalette.ROW_BORDER, 1f);
            if (value) {
                ctx.graphics().setColor(new java.awt.Color(0xFF,0xFF,0xFF));
                ctx.graphics().setStroke(new java.awt.BasicStroke(2f));
                ctx.graphics().drawLine(Math.round(bx+3), Math.round(by+box*0.55f), Math.round(bx+box*0.42f), Math.round(by+box-4));
                ctx.graphics().drawLine(Math.round(bx+box*0.42f), Math.round(by+box-4), Math.round(bx+box-3), Math.round(by+4));
            }
            ctx.drawString(f, name, bx + box + 8f,
                    ctx.textBaseline(f, b.y + (b.h - f.lineHeight()) * 0.5f),
                    LionPalette.TEXT_PRIMARY, false);
        }
        public boolean mouseClick(float x, float y, int btn, Rectangle b) {
            if (b.contains(x, y) && btn == 0) { value = !value; return true; }
            return false;
        }
        public void mouseRelease(float x, float y, int btn, Rectangle b) {}
        public void mouseDrag(float x, float y, Rectangle b) {}
    }

    /** Slider numeric setting. */
    public static final class NumberSetting extends Setting {
        public float value, min, max;
        public int decimals;
        private boolean dragging;
        public NumberSetting(String n, float v, float mn, float mx, int d) { super(n); value=v; min=mn; max=mx; decimals=d; }
        public float preferredHeight(float width) { return 42f; }
        public void draw(RenderContext ctx, Rectangle b) {
            FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 13f);
            ctx.fillRect(b.x, b.y, b.w, b.h, LionPalette.ROW_BG);
            ctx.drawRect(b.x + 0.5f, b.y + 0.5f, b.w - 1f, b.h - 1f, LionPalette.ROW_BORDER, 1f);
            float pad = 12f;
            // Label
            ctx.drawString(f, name, b.x + pad, ctx.textBaseline(f, b.y + 8f), LionPalette.TEXT_PRIMARY, false);
            // Readout box
            float rw = 80f, rh = 18f;
            float rx = b.x + b.w - rw - pad, ry = b.y + 8f;
            ctx.fillRect(rx, ry, rw, rh, LionPalette.SURFACE_SUNKEN);
            ctx.drawRect(rx + 0.5f, ry + 0.5f, rw - 1f, rh - 1f, LionPalette.ROW_BORDER, 1f);
            String s = decimals == 0 ? Long.toString(Math.round(value)) : String.format("%." + decimals + "f", value);
            float sw = f.stringWidth(s);
            ctx.drawString(f, s, rx + rw - sw - 6f, ctx.textBaseline(f, ry + (rh - f.lineHeight()) * 0.5f), LionPalette.TEXT_ACCENT, false);
            // Track
            float ty = b.y + 30f, th = 3f;
            float tx = b.x + pad, tw = b.w - pad * 2f;
            ctx.fillRect(tx, ty, tw, th, LionPalette.SURFACE_SUNKEN);
            float t = MathUtil.clamp((value - min) / Math.max(1e-5f, max - min), 0f, 1f);
            ctx.fillRect(tx, ty, tw * t, th, LionPalette.ACCENT);
            ctx.fillRect(tx + tw * t - 3f, ty - 2f, 6f, th + 4f, LionPalette.ACCENT);
        }
        public boolean mouseClick(float x, float y, int btn, Rectangle b) {
            float pad = 12f;
            float ty = b.y + 30f;
            if (b.contains(x,y) && btn == 0) {
                if (y >= ty - 2 && y <= ty + 7) { dragging = true; apply(x, b); return true; }
            }
            return false;
        }
        public void mouseRelease(float x, float y, int btn, Rectangle b) { dragging = false; }
        public void mouseDrag(float x, float y, Rectangle b) { if (dragging) apply(x, b); }
        private void apply(float x, Rectangle b) {
            float pad = 12f;
            float tx = b.x + pad, tw = b.w - pad * 2f;
            float t = MathUtil.clamp((x - tx) / Math.max(1f, tw), 0f, 1f);
            float nv = min + (max - min) * t;
            nv = Math.round(nv * (float)Math.pow(10, decimals)) / (float)Math.pow(10, decimals);
            value = nv;
        }
    }

    /** Dropdown (enum) setting. */
    public static final class EnumSetting extends Setting {
        public final List<String> values;
        public int selected;
        private boolean open;
        private final AnimationController openAnim = new AnimationController(0f);
        public EnumSetting(String n, List<String> vals, int sel) { super(n); values = vals; selected = sel; }
        public float preferredHeight(float width) { float itemH = 18f; return 42f + (open ? values.size() * itemH * openAnim.value() : 0f); }
        public void draw(RenderContext ctx, Rectangle b) {
            FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 13f);
            ctx.fillRect(b.x, b.y, b.w, 42f, LionPalette.ROW_BG);
            ctx.drawRect(b.x + 0.5f, b.y + 0.5f, b.w - 1f, 42f - 1f, LionPalette.ROW_BORDER, 1f);
            float pad = 12f;
            ctx.drawString(f, name, b.x + pad, ctx.textBaseline(f, b.y + (42f - f.lineHeight())*0.5f), LionPalette.TEXT_MUTED, false);
            float bw = b.w - 240f, bh = 24f;
            float bx = b.x + b.w - bw - pad, by = b.y + (42f - bh)*0.5f;
            ctx.fillRect(bx, by, bw, bh, LionPalette.SURFACE_SUNKEN);
            ctx.drawRect(bx+0.5f, by+0.5f, bw-1f, bh-1f, LionPalette.ROW_BORDER, 1f);
            String sel = values.get(selected);
            float sw = f.stringWidth(sel);
            ctx.drawString(f, sel, bx + 10f, ctx.textBaseline(f, by + (bh - f.lineHeight())*0.5f), LionPalette.TEXT_PRIMARY, false);
            ctx.drawString(f, "≡", bx + bw - 18f, ctx.textBaseline(f, by + (bh - f.lineHeight())*0.5f), LionPalette.TEXT_MUTED, false);
            if (openAnim.value() > 0.01f) {
                float listH = values.size() * 18f;
                float disp = listH * openAnim.value();
                ctx.pushScissor(bx, by + bh, bw, disp);
                ctx.fillRect(bx, by + bh, bw, listH, LionPalette.SURFACE_SUNKEN);
                ctx.drawRect(bx+0.5f, by+bh+0.5f, bw-1f, listH-1f, LionPalette.ROW_BORDER, 1f);
                for (int i = 0; i < values.size(); i++) {
                    float iy = by + bh + i*18f;
                    if (i == selected) ctx.fillRect(bx, iy, bw, 18f, LionPalette.ACCENT.withAlpha(70));
                    ctx.drawString(f, values.get(i), bx+10f, ctx.textBaseline(f, iy + (18f - f.lineHeight())*0.5f),
                            LionPalette.TEXT_PRIMARY, false);
                }
                ctx.popScissor();
            }
        }
        public boolean mouseClick(float x, float y, int btn, Rectangle b) {
            float pad = 12f;
            float bw = b.w - 240f, bh = 24f;
            float bx = b.x + b.w - bw - pad, by = b.y + (42f - bh)*0.5f;
            if (new Rectangle(bx, by, bw, bh).contains(x,y) && btn==0) {
                open = !open; openAnim.setTarget(open ? 1f : 0f, 0.16f); return true;
            }
            if (open && new Rectangle(bx, by+bh, bw, values.size()*18f).contains(x,y) && btn==0) {
                selected = MathUtil.clampInt((int)((y-(by+bh))/18f), 0, values.size()-1);
                open = false; openAnim.setTarget(0f, 0.16f);
                return true;
            }
            if (open && !new Rectangle(bx, by, bw, bh + values.size()*18f).contains(x,y)) {
                open = false; openAnim.setTarget(0f, 0.16f);
            }
            return false;
        }
        public void mouseRelease(float x, float y, int btn, Rectangle b) {}
        public void mouseDrag(float x, float y, Rectangle b) {}
        public void update(float dt) { openAnim.update(dt); }
    }

    // ---- State ----
    private static final String BRAND = "LionClient";
    private static final String VERSION = "v1.0.5";
    private static final float TAB_HEIGHT = 36f;
    private static final float SIDE_WIDTH = 220f;
    private static final float HEADER_HEIGHT = 54f;

    private final List<Category> categories = new ArrayList<>();
    private int activeCategory = 4; // PLAYER (matches reference screenshot)
    private Module activeModule;
    private final ConfettiBackground confetti = new ConfettiBackground();
    private final AnimationController selectAnim = new AnimationController(0f);
    private float selectedIndex;

    public LionClickGui() {
        ThemeManager.instance().register(LionPalette.lionDark());
        ThemeManager.instance().setCurrent("Lion Dark");
        buildDemoContent();
        confetti.bounds().set(0,0,1280,720);
    }

    private void buildDemoContent() {
        String[] tabs = { "COMBAT", "MOVEMENT", "CLIENT", "RENDER", "PLAYER", "MISC" };
        Map<String, List<Module>> data = new LinkedHashMap<>();
        data.put("COMBAT", Arrays.asList(
                module("KillAura", "Attacks entities within range automatically."),
                module("Reach", "Extends attack range.").add(new NumberSetting("Distance", 3.2f, 3f, 6f, 1)),
                module("Velocity", "Reduces knockback taken."),
                module("AutoClicker", "Clicks automatically.").add(new NumberSetting("CPS", 12f, 1f, 20f, 0))
        ));
        data.put("MOVEMENT", Arrays.asList(
                module("Sprint", "Always sprint."),
                module("Fly", "Creative-like flight.").add(new NumberSetting("Speed", 1.0f, 0f, 3f, 2)),
                module("Speed", "Horizontal movement boost.").add(new NumberSetting("Boost", 1.2f, 1f, 2f, 2)),
                module("NoFall", "Prevents fall damage.")
        ));
        data.put("CLIENT", Arrays.asList(
                module("HUD", "Toggle on-screen HUD.").add(new BoolSetting("Show Arraylist", true)),
                module("Notifications", "Toast notifications.").add(new BoolSetting("Enabled", true)),
                module("Performance", "Rendering optimizations.")
        ));
        data.put("RENDER", Arrays.asList(
                module("Fullbright", "Max brightness.").add(new BoolSetting("Enabled", true)),
                module("ESP", "Draw entities through walls.").add(new EnumSetting("Mode", Arrays.asList("Box","2D","Outline","Glow"), 0)),
                module("Tracers", "Lines to entities.")
        ));
        Category player = new Category("PLAYER");
        player.modules.add(module("AntiFireball", "Deflects fireballs when right-clicking."));
        Module clutch = module("Clutch", "Bridges blocks back to safety when knocked off an edge");
        clutch.add(new EnumSetting("Trigger", Arrays.asList("FALL_DISTANCE", "MOTION", "ALWAYS"), 0));
        clutch.add(new NumberSetting("Blocks", 1.0f, 0f, 12f, 1));
        clutch.add(new BoolSetting("Silent Aim", false));
        clutch.add(new BoolSetting("Return To Slot", true));
        clutch.add(new NumberSetting("Clutch Move Delay", 0f, 0f, 200f, 0));
        clutch.add(new NumberSetting("Max Blocks", 10f, 1f, 64f, 0));
        player.modules.add(clutch);
        player.modules.add(module("FastPlace", "Removes block place delay."));
        player.modules.add(module("AutoTool", "Swaps to best tool.").add(new BoolSetting("Switch Back", true)));
        data.put("PLAYER", player.modules);
        data.put("MISC", Arrays.asList(
                module("AntiBot", "Filters bots from target list."),
                module("ChatSuffix", "Adds a suffix after messages."),
                module("InventoryClean", "Automatically drops trash items.")
        ));

        for (String tab : tabs) {
            Category c = new Category(tab);
            c.modules.addAll(data.get(tab));
            categories.add(c);
        }
        activeModule = categories.get(activeCategory).modules.get(1); // Clutch
        selectedIndex = 1;
    }

    private static Module module(String name, String desc) { return new Module(name).describe(desc); }

    // ---- Input ----
    private Setting draggingSetting;
    private EnumSetting openEnum;

    @Override
    public void update(float delta) {
        selectAnim.update(delta);
        confetti.bounds().set(bounds.x, bounds.y, bounds.w, bounds.h);
        confetti.update(delta);
        for (Category c : categories) {
            for (Module m : c.modules) {
                for (Setting s : m.settings) if (s instanceof EnumSetting) ((EnumSetting)s).update(delta);
            }
        }
    }

    @Override
    protected boolean onInput(net.hackclient.ui.input.InputEvent ev) {
        if (!bounds.contains(ev.x, ev.y)) return false;
        float padX = 0, topY = bounds.y + HEADER_HEIGHT;
        Rectangle sideRect = new Rectangle(bounds.x, topY, SIDE_WIDTH, bounds.h - HEADER_HEIGHT);
        Rectangle mainRect = new Rectangle(bounds.x + SIDE_WIDTH, topY, bounds.w - SIDE_WIDTH, bounds.h - HEADER_HEIGHT);

        switch (ev.type) {
            case MOUSE_PRESS: {
                if (ev.y < bounds.y + TAB_HEIGHT) {
                    handleTabClick(ev.x);
                    return true;
                }
                if (sideRect.contains(ev.x, ev.y)) {
                    handleModuleClick(ev.y - topY);
                    return true;
                }
                if (mainRect.contains(ev.x, ev.y)) {
                    recomputeSettingRects();
                    Setting s = settingAt(ev.x, ev.y);
                    if (s != null && s.mouseClick(ev.x, ev.y, ev.button, lastSettingRect)) {
                        if (s instanceof NumberSetting) draggingSetting = s;
                        return true;
                    }
                }
                // Click outside any setting: close any open dropdown
                if (activeModule != null) {
                    for (Setting s : activeModule.settings) {
                        if (s instanceof EnumSetting) {
                            EnumSetting es = (EnumSetting) s;
                            if (es.open) { es.open = false; es.openAnim.setTarget(0f, 0.16f); }
                        }
                    }
                }
                return false;
            }
            case MOUSE_RELEASE:
                if (draggingSetting != null) {
                    draggingSetting.mouseRelease(ev.x, ev.y, ev.button, lastSettingRect);
                    draggingSetting = null;
                }
                return false;
            case MOUSE_MOVE:
                hovered = true;
                if (draggingSetting != null) draggingSetting.mouseDrag(ev.x, ev.y, lastSettingRect);
                return false;
            default: return false;
        }
    }

    private void handleTabClick(float mx) {
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 13f, true);
        float x = 240f; // skip brand area
        float tabGap = 8f;
        for (int i = 0; i < categories.size(); i++) {
            float tw = f.stringWidth(categories.get(i).name) + 30f;
            if (mx >= x && mx <= x + tw) {
                activeCategory = i;
                activeModule = categories.get(i).modules.get(0);
                selectAnim.setValueInstant(0);
                selectAnim.setTarget(1f, 0.2f);
                return;
            }
            x += tw + tabGap;
        }
    }

    private void handleModuleClick(float my) {
        Category c = categories.get(activeCategory);
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 13f, false);
        float pad = 14f;
        float rowH = 36f;
        int idx = MathUtil.clampInt((int)(my / rowH), 0, c.modules.size()-1);
        if (idx != c.modules.indexOf(activeModule)) {
            activeModule = c.modules.get(idx);
            selectedIndex = idx;
            selectAnim.setValueInstant(0);
            selectAnim.setTarget(1f, 0.2f);
        }
    }

    private final Rectangle lastSettingRect = new Rectangle();
    private final java.util.List<Rectangle> settingRects = new ArrayList<>();

    private void recomputeSettingRects() {
        settingRects.clear();
        if (activeModule == null) return;
        float topY = bounds.y + TAB_HEIGHT + 2f;
        float leftX = bounds.x + SIDE_WIDTH + 24f;
        float width = bounds.w - SIDE_WIDTH - 48f;
        float y = topY + 24f + 60f;
        for (Setting s : activeModule.settings) {
            float h = s.preferredHeight(width);
            settingRects.add(new Rectangle(leftX, y, width, h));
            y += h + 6f;
        }
    }

    private Setting settingAt(float mx, float my) {
        if (activeModule == null) return null;
        for (int i = 0; i < activeModule.settings.size() && i < settingRects.size(); i++) {
            Rectangle r = settingRects.get(i);
            if (r.contains(mx, my)) { lastSettingRect.copyFrom(r); return activeModule.settings.get(i); }
        }
        return null;
    }

    private float activeContentTop() { return 0f; }

    // ---- Drawing ----
    @Override
    protected void onDraw(RenderContext ctx) {
        // Window chrome
        ctx.fillRect(bounds.x, bounds.y, bounds.w, bounds.h, LionPalette.WINDOW_BG);
        confetti.draw(ctx);
        // Header strip (brand + version)
        drawHeader(ctx);
        drawTabs(ctx);
        // Horizontal divider under tabs
        ctx.fillRect(bounds.x, bounds.y + TAB_HEIGHT, bounds.w, 2f, LionPalette.ACCENT);
        // Side panel (module list)
        drawSide(ctx);
        // Main content (settings)
        drawMain(ctx);
    }

    private void drawHeader(RenderContext ctx) {
        FontManager.CachedFont brandF = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 18f, true);
        FontManager.CachedFont verF   = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 11f, false);
        ctx.drawString(brandF, BRAND, bounds.x + 16f,
                ctx.textBaseline(brandF, bounds.y + (TAB_HEIGHT - brandF.lineHeight())*0.5f - 2f),
                LionPalette.TEXT_PRIMARY, false);
        ctx.drawString(verF, VERSION, bounds.x + 16f,
                ctx.textBaseline(verF, bounds.y + TAB_HEIGHT - 14f),
                LionPalette.TEXT_MUTED, false);
    }

    private void drawTabs(RenderContext ctx) {
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 13f, true);
        float x = 240f;
        float y = bounds.y + 5f;
        float h = TAB_HEIGHT - 10f;
        float gap = 8f;
        for (int i = 0; i < categories.size(); i++) {
            String name = categories.get(i).name;
            float tw = f.stringWidth(name) + 30f;
            boolean active = i == activeCategory;
            Color bg = active ? LionPalette.TAB_ACTIVE : LionPalette.TAB_BG;
            ctx.fillRect(x, y, tw, h, bg);
            if (!active) ctx.drawRect(x + 0.5f, y + 0.5f, tw - 1f, h - 1f, LionPalette.ROW_BORDER, 1f);
            Color tc = active ? Color.WHITE : LionPalette.TEXT_SECONDARY;
            ctx.drawString(f, name, x + (tw - f.stringWidth(name)) * 0.5f,
                    ctx.textBaseline(f, y + (h - f.lineHeight()) * 0.5f), tc, false);
            x += tw + gap;
        }
    }

    private void drawSide(RenderContext ctx) {
        float topY = bounds.y + HEADER_HEIGHT - (TAB_HEIGHT - 2f); // adjust for tab area height
        // Wait, layout is: tabs at top (TAB_HEIGHT), then divider, then side+main below.
        float listY = bounds.y + TAB_HEIGHT + 2f;
        float sideH = bounds.h - (TAB_HEIGHT + 2f);
        ctx.fillRect(bounds.x, listY, SIDE_WIDTH, sideH, LionPalette.PANEL_BG);
        ctx.drawRect(bounds.x + SIDE_WIDTH - 0.5f, listY, 1f, sideH, LionPalette.DIVIDER, 1f);
        Category c = categories.get(activeCategory);
        FontManager.CachedFont f = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 13f, false);
        float rowH = 36f;
        float pad = 14f;
        for (int i = 0; i < c.modules.size(); i++) {
            Module m = c.modules.get(i);
            float ry = listY + i * rowH;
            if (m == activeModule) {
                float animT = selectAnim.progress();
                ctx.fillRect(bounds.x, ry, SIDE_WIDTH - 0f, rowH,
                        Color.lerp(LionPalette.TAB_HOVER, LionPalette.TAB_ACTIVE, animT));
                // Accent bar on the left
                ctx.fillRect(bounds.x, ry, 3f, rowH, LionPalette.ACCENT);
            }
            ctx.drawString(f, m.name, bounds.x + pad,
                    ctx.textBaseline(f, ry + (rowH - f.lineHeight())*0.5f),
                    m == activeModule ? Color.WHITE : LionPalette.TEXT_SECONDARY, false);
        }
    }

    private void drawMain(RenderContext ctx) {
        float topY = bounds.y + TAB_HEIGHT + 2f;
        float mainX = bounds.x + SIDE_WIDTH;
        float mainW = bounds.w - SIDE_WIDTH;
        float mainH = bounds.h - (TAB_HEIGHT + 2f);
        ctx.fillRect(mainX, topY, mainW, mainH, LionPalette.WINDOW_BG);
        if (activeModule == null) return;
        FontManager.CachedFont titleF = FontManager.instance().get(FontManager.FONT_PROXIMA_BOLD, 18f, true);
        FontManager.CachedFont descF  = FontManager.instance().get(FontManager.FONT_PROXIMA_NOVA, 12f, false);
        float px = 24f, py = 24f;
        // Module name + Enabled toggle top-right
        ctx.drawString(titleF, activeModule.name, mainX + px, ctx.textBaseline(titleF, topY + py), LionPalette.TEXT_PRIMARY, false);
        ctx.drawString(descF, activeModule.description, mainX + px, ctx.textBaseline(descF, topY + py + 22f), LionPalette.TEXT_SECONDARY, false);
        // "Enabled" toggle
        float ex = mainX + mainW - 120f, ey = topY + 24f;
        float es = 26f;
        ctx.fillRect(ex, ey, es, es, activeModule.enabled ? LionPalette.ACCENT : LionPalette.SURFACE_SUNKEN);
        ctx.drawRect(ex+0.5f, ey+0.5f, es-1f, es-1f, activeModule.enabled ? LionPalette.ACCENT_DARK : LionPalette.ROW_BORDER, 1f);
        ctx.drawString(descF, "Enabled", ex + es + 8f,
                ctx.textBaseline(descF, ey + (es - descF.lineHeight())*0.5f), LionPalette.TEXT_PRIMARY, false);

        // Settings list
        recomputeSettingRects();
        for (int i = 0; i < activeModule.settings.size() && i < settingRects.size(); i++) {
            activeModule.settings.get(i).draw(ctx, settingRects.get(i));
        }
    }
}
