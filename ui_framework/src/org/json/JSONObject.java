package org.json;

import java.util.LinkedHashMap;
import java.util.Map;

/** Minimal JSON object implementation sufficient for HudConfig parsing. */
public class JSONObject {
    private final Map<String, Object> map = new LinkedHashMap<>();
    public JSONObject() {}
    public JSONObject(String json) {
        Parser p = new Parser(json);
        p.skipWs();
        if (p.peek() != '{') throw new IllegalArgumentException("Not an object");
        p.next();
        p.skipWs();
        if (p.peek() == '}') { p.next(); return; }
        while (true) {
            p.skipWs();
            String key = p.readString();
            p.skipWs();
            if (p.next() != ':') throw new IllegalArgumentException("Expected :");
            p.skipWs();
            map.put(key, p.readValue());
            p.skipWs();
            char c = p.next();
            if (c == ',') continue;
            if (c == '}') break;
            throw new IllegalArgumentException("Expected , or }");
        }
    }
    public Object opt(String k) { return map.get(k); }
    public String optString(String k, String def) { Object o = map.get(k); return o instanceof String ? (String)o : def; }
    public String optString(String k) { return optString(k, ""); }
    public int optInt(String k, int def) { Object o = map.get(k); return o instanceof Number ? ((Number)o).intValue() : def; }
    public double optDouble(String k, double def) { Object o = map.get(k); return o instanceof Number ? ((Number)o).doubleValue() : def; }
    public boolean optBoolean(String k) { return optBoolean(k, false); }
    public boolean optBoolean(String k, boolean def) { Object o = map.get(k); return o instanceof Boolean ? (Boolean)o : def; }
    public JSONObject optJSONObject(String k) { Object o = map.get(k); return o instanceof JSONObject ? (JSONObject)o : null; }
    public JSONArray optJSONArray(String k) { Object o = map.get(k); return o instanceof JSONArray ? (JSONArray)o : null; }
}
