package net.hackclient.ui;

import net.hackclient.ui.animation.AnimationController;
import net.hackclient.ui.gui.LionClickGui;
import net.hackclient.ui.hud.HudRenderer;
import net.hackclient.ui.input.InputEvent;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.theme.ThemeManager;

/**
 * Top-level UI controller: owns the ClickGUI root and the HUD renderer.
 * The host mod should call {@link #renderHud} every frame from the in-game
 * overlay hook and {@link #renderScreen} whenever the ClickGUI screen is
 * open. Input is forwarded from the host's mouse/key handlers.
 */
public final class UIController {

    private static final UIController INSTANCE = new UIController();

    private final LionClickGui clickGui = new LionClickGui();
    private final AnimationController screenFade = new AnimationController(0f);
    private boolean screenOpen = false;
    private final InputEvent reusableEvent = new InputEvent();
    private int screenW = 1280, screenH = 720;

    private UIController() { }

    public static UIController instance() { return INSTANCE; }

    /** Open the ClickGUI screen (typically bound to RIGHT_SHIFT). */
    public void openScreen() {
        screenOpen = true;
        screenFade.setTarget(1f, 0.18f);
    }

    /** Close the ClickGUI screen. */
    public void closeScreen() {
        screenOpen = false;
        screenFade.setTarget(0f, 0.18f);
    }

    public boolean isScreenOpen() { return screenOpen; }
    public void toggleScreen() { if (screenOpen) closeScreen(); else openScreen(); }

    public LionClickGui clickGui() { return clickGui; }

    /** Called from the in-game HUD overlay every frame while playing. */
    public void renderHud(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        this.screenW = screenW; this.screenH = screenH;
        HudRenderer.instance().render(ctx, screenW, screenH, partialTicks);
    }

    /** Called from the ClickGUI screen draw routine. */
    public void renderScreen(RenderContext ctx, int screenW, int screenH, float partialTicks) {
        this.screenW = screenW; this.screenH = screenH;
        screenFade.update(partialTicks);
        float fade = screenFade.value();
        if (fade <= 0f && !screenOpen) return;
        // Dimming backdrop
        ctx.fillRect(0, 0, screenW, screenH, new Color(0, 0, 0, 0.4f * fade));
        // ClickGUI window centered on screen
        float w = Math.min(screenW * 0.85f, 1100f);
        float h = Math.min(screenH * 0.80f, 640f);
        float x = (screenW - w) * 0.5f;
        float y = (screenH - h) * 0.5f;
        clickGui.bounds().set(x, y, w, h);
        clickGui.update(partialTicks);
        clickGui.draw(ctx);
    }

    // --- Input dispatch ---

    public void onMouseMove(float x, float y) {
        reusableEvent.set(InputEvent.Type.MOUSE_MOVE, x, y);
        clickGui.dispatch(reusableEvent);
    }

    public void onMousePress(float x, float y, int button) {
        reusableEvent.set(InputEvent.Type.MOUSE_PRESS, x, y).mouse(button);
        clickGui.dispatch(reusableEvent);
    }

    public void onMouseRelease(float x, float y, int button) {
        reusableEvent.set(InputEvent.Type.MOUSE_RELEASE, x, y).mouse(button);
        clickGui.dispatch(reusableEvent);
    }

    public void onMouseScroll(float x, float y, float delta) {
        reusableEvent.set(InputEvent.Type.MOUSE_SCROLL, x, y).scroll(delta);
        clickGui.dispatch(reusableEvent);
    }

    public void onKeyPress(int keyCode) {
        reusableEvent.set(InputEvent.Type.KEY_PRESS, -1, -1).key(keyCode);
        clickGui.dispatch(reusableEvent);
    }

    public void onCharType(char c) {
        reusableEvent.set(InputEvent.Type.CHAR_TYPE, -1, -1).character(c);
        clickGui.dispatch(reusableEvent);
    }
}
