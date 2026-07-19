package org.json;

class Parser {
    final String s;
    int pos;
    Parser(String s) { this.s = s; this.pos = 0; }
    char peek() { return pos < s.length() ? s.charAt(pos) : '\0'; }
    char next() { return pos < s.length() ? s.charAt(pos++) : '\0'; }
    void skipWs() { while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) pos++; }

    Object readValue() {
        skipWs();
        char c = peek();
        if (c == '"') return readString();
        if (c == '{') return readObject();
        if (c == '[') return readArray();
        if (c == 't' || c == 'f') return readBoolean();
        if (c == 'n') { pos += 4; return null; }
        return readNumber();
    }

    String readString() {
        if (peek() != '"') throw new IllegalStateException("Expected string");
        next();
        StringBuilder sb = new StringBuilder();
        while (pos < s.length()) {
            char c = next();
            if (c == '"') return sb.toString();
            if (c == '\\') {
                char e = next();
                switch (e) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/': sb.append('/'); break;
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case 'r': sb.append('\r'); break;
                    case 'b': sb.append('\b'); break;
                    case 'f': sb.append('\f'); break;
                    case 'u':
                        sb.append((char)Integer.parseInt(s.substring(pos, pos+4), 16)); pos += 4; break;
                    default: sb.append(e);
                }
            } else sb.append(c);
        }
        throw new IllegalStateException("Unterminated string");
    }

    JSONObject readObject() {
        int start = pos;
        int depth = 0;
        int start0 = pos;
        while (pos < s.length()) {
            char c = s.charAt(pos++);
            if (c == '{') depth++;
            else if (c == '}') { depth--; if (depth == 0) return new JSONObject(s.substring(start0, pos)); }
            else if (c == '"') { readStringBody(); }
        }
        throw new IllegalStateException("Unterminated object");
    }

    private void readStringBody() {
        while (pos < s.length()) {
            char c = next();
            if (c == '"') return;
            if (c == '\\') next();
        }
    }

    JSONArray readArray() {
        int start0 = pos;
        int depth = 0;
        while (pos < s.length()) {
            char c = s.charAt(pos++);
            if (c == '[') depth++;
            else if (c == ']') { depth--; if (depth == 0) return new JSONArray(s.substring(start0, pos)); }
            else if (c == '"') { readStringBody(); }
        }
        throw new IllegalStateException("Unterminated array");
    }

    Boolean readBoolean() {
        if (s.startsWith("true", pos)) { pos += 4; return Boolean.TRUE; }
        if (s.startsWith("false", pos)) { pos += 5; return Boolean.FALSE; }
        throw new IllegalStateException("Bad bool");
    }

    Number readNumber() {
        int start = pos;
        if (peek() == '-') next();
        while (pos < s.length() && (Character.isDigit(peek()) || peek() == '.' || peek() == 'e' || peek() == 'E' || peek() == '+' || peek() == '-')) next();
        String num = s.substring(start, pos);
        if (num.contains(".") || num.contains("e") || num.contains("E")) return Double.parseDouble(num);
        try { return Long.parseLong(num); } catch (Exception e) { return Double.parseDouble(num); }
    }
}
