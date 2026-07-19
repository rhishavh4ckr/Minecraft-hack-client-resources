package net.hackclient.ui.hud;

import net.hackclient.ui.font.FontManager;
import net.hackclient.ui.theme.Color;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Parses the LiquidBounce-style {@code hud.json} format shown in files.zip
 * and applies it to the current {@link HudRenderer}. This makes our HUD
 * fully compatible with the reference layout without relying on the old
 * obfuscated renderer.
 */
public final class HudConfig {

    private HudConfig() { }

    public static void loadFromJson(String json) {
        HudRenderer hud = HudRenderer.instance();
        hud.elements().clear();
        JSONArray arr = new JSONArray(json);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.optJSONObject(i);
            if (o == null) continue;
            String type = o.optString("Type", "");
            HudElement e = null;
            switch (type) {
                case "Scoreboard":
                    e = parseScoreboard(o);
                    break;
                case "Text":
                    e = parseText(o);
                    break;
                case "Arraylist":
                    e = parseArrayList(o);
                    break;
                default:
                    // Unknown types are ignored so that the renderer stays
                    // forward-compatible with future hud.json additions.
                    continue;
            }
            applyBase(o, e);
            hud.add(e);
        }
    }

    public static void loadFromResource(String resourcePath) {
        try (InputStream in = HudConfig.class.getResourceAsStream(resourcePath)) {
            if (in == null) return;
            try (Scanner s = new Scanner(in, StandardCharsets.UTF_8.name())) {
                s.useDelimiter("\\A");
                loadFromJson(s.hasNext() ? s.next() : "[]");
            }
        } catch (Exception ignored) {
            // Fallback to default layout if the config is missing/corrupt.
        }
    }

    private static void applyBase(JSONObject o, HudElement e) {
        if (e == null) return;
        e.anchor(
                (float) o.optDouble("X", 0.0) / 100.0f,
                (float) o.optDouble("Y", 0.0) / 100.0f);
        e.scale((float) o.optDouble("Scale", 1.0));
        String h = o.optString("HorizontalFacing", "Left");
        String v = o.optString("VerticalFacing", "Up");
        e.facing(
                "Right".equalsIgnoreCase(h) ? HudElement.HorizontalFacing.RIGHT : HudElement.HorizontalFacing.LEFT,
                parseVFacing(v));
    }

    private static HudElement.VerticalFacing parseVFacing(String v) {
        if ("Middle".equalsIgnoreCase(v)) return HudElement.VerticalFacing.MIDDLE;
        if ("Down".equalsIgnoreCase(v))   return HudElement.VerticalFacing.DOWN;
        return HudElement.VerticalFacing.UP;
    }

    private static ScoreboardElement parseScoreboard(JSONObject o) {
        ScoreboardElement sb = new ScoreboardElement("scoreboard");
        int tr = o.optInt("Text-R", 255), tg = o.optInt("Text-G", 255), tb = o.optInt("Text-B", 255);
        int br = o.optInt("Background-R", 0), bg = o.optInt("Background-G", 0),
                bb = o.optInt("Background-B", 0), ba = o.optInt("Background-Alpha", 95);
        sb.textColor(new Color(tr, tg, tb, 255));
        sb.bgColor(new Color(br, bg, bb, ba));
        sb.drawRect(o.optBoolean("Rect", false) == false ? true : o.optBoolean("Rect"));
        JSONObject f = o.optJSONObject("Font");
        if (f != null) {
            float size = (float) f.optDouble("fontSize", -1);
            String font = mapFont(f.optString("fontName", "Minecraft Font"), size);
            sb.font(font, size <= 0 ? 14f : size);
        }
        return sb;
    }

    private static TextElement parseText(JSONObject o) {
        TextElement t = new TextElement("text-" + System.identityHashCode(o));
        t.text(o.optString("DisplayText", ""));
        int r = o.optInt("Red", 255), g = o.optInt("Green", 255), b = o.optInt("Blue", 255);
        t.color(new Color(r, g, b, 255));
        t.shadow(o.optBoolean("Shadow", true));
        t.rainbow(o.optBoolean("Rainbow", false));
        JSONObject f = o.optJSONObject("Font");
        if (f != null) {
            float size = (float) f.optDouble("fontSize", 80);
            String font = mapFont(f.optString("fontName", ""), size);
            t.font(font, size, true);
        }
        return t;
    }

    private static ArrayListElement parseArrayList(JSONObject o) {
        ArrayListElement al = new ArrayListElement("arraylist");
        al.shadow(o.optBoolean("ShadowText", true));
        al.rainbow("Rainbow".equalsIgnoreCase(o.optString("Text-Color", "")));
        al.upperCase(o.optBoolean("UpperCase", false));
        int br = o.optInt("Background-R", 0), bg = o.optInt("Background-G", 0),
                bb = o.optInt("Background-B", 0), ba = o.optInt("Background-Alpha", 150);
        al.bgAlpha(ba / 255f);
        al.bgColorWorkaround(new Color(br, bg, bb, ba));
        String rect = o.optString("Rect", "Left");
        switch (rect.toLowerCase()) {
            case "right": al.rectSide(ArrayListElement.RectSide.RIGHT); break;
            case "false": case "off": case "": al.rectSide(ArrayListElement.RectSide.OFF); break;
            default: al.rectSide(ArrayListElement.RectSide.LEFT); break;
        }
        al.textHeight((float) o.optDouble("TextHeight", 15f));
        al.textYOffset((float) o.optDouble("TextY", 0f));
        al.rowPadding((float) o.optDouble("Space", 0f) + 6f);
        JSONObject f = o.optJSONObject("Font");
        if (f != null) {
            float size = (float) f.optDouble("fontSize", 64);
            String font = mapFont(f.optString("fontName", ""), size);
            // Rendered font size in-game is roughly fontSize / 3.5 for TTFs at res 1080
            al.font(font, Math.max(11f, size / 3.5f));
        }
        return al;
    }

    private static String mapFont(String name, float size) {
        if (name == null) return FontManager.FONT_PROXIMA_NOVA;
        String lc = name.toLowerCase();
        if (lc.contains("assetsio")) return FontManager.FONT_ASSETSIO;
        if (lc.contains("bold") || lc.contains("bd")) return FontManager.FONT_PROXIMA_BOLD;
        if (lc.contains("minecraft")) return FontManager.FONT_MINECRAFT;
        if (lc.contains("proxima")) return FontManager.FONT_PROXIMA_NOVA;
        return FontManager.FONT_PROXIMA_NOVA;
    }
}
