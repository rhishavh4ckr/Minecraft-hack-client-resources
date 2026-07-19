package net.hackclient.ui.input;

/**
 * Lightweight, GC-free input event dispatched from the host mod (Minecraft,
 * LabyMod, Fabric) into the UI tree. Instances can be reused to avoid
 * allocation on high-frequency mouse-move events – use the setters instead
 * of constructing a new event each frame.
 */
public final class InputEvent {

    public enum Type {
        MOUSE_MOVE,
        MOUSE_PRESS,
        MOUSE_RELEASE,
        MOUSE_SCROLL,
        KEY_PRESS,
        KEY_RELEASE,
        CHAR_TYPE
    }

    public Type type;
    public float x;
    public float y;
    public int button;
    public float scrollDelta;
    public int keyCode;
    public char character;
    public boolean consumed;

    public InputEvent() { }

    public InputEvent(Type type, float x, float y) {
        this.type = type; this.x = x; this.y = y;
    }

    public InputEvent set(Type type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.button = 0;
        this.scrollDelta = 0f;
        this.keyCode = 0;
        this.character = 0;
        this.consumed = false;
        return this;
    }

    public InputEvent mouse(int button) { this.button = button; return this; }
    public InputEvent scroll(float d) { this.scrollDelta = d; return this; }
    public InputEvent key(int code) { this.keyCode = code; return this; }
    public InputEvent character(char c) { this.character = c; return this; }

    public void consume() { this.consumed = true; }
    public boolean isConsumed() { return consumed; }
}
