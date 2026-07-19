package org.json;

import java.util.ArrayList;
import java.util.List;

/** Minimal JSON array implementation sufficient for HudConfig parsing. */
public class JSONArray {
    private final List<Object> items = new ArrayList<>();
    public JSONArray() {}
    public JSONArray(String json) {
        Parser p = new Parser(json);
        p.skipWs();
        if (p.peek() != '[') throw new IllegalArgumentException("Not an array: " + json);
        p.next();
        p.skipWs();
        if (p.peek() == ']') { p.next(); return; }
        while (true) {
            items.add(p.readValue());
            p.skipWs();
            char c = p.next();
            if (c == ',') continue;
            if (c == ']') break;
            throw new IllegalArgumentException("Expected , or ] at " + p.pos);
        }
    }
    public int length() { return items.size(); }
    public Object get(int i) { return items.get(i); }
    public JSONObject optJSONObject(int i) { Object o = items.get(i); return o instanceof JSONObject ? (JSONObject)o : null; }
}
