package net.hackclient.ui.preview;

import net.hackclient.ui.UIController;
import net.hackclient.ui.hud.ArrayListElement;
import net.hackclient.ui.hud.HudConfig;
import net.hackclient.ui.hud.HudRenderer;
import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.LionPalette;
import net.hackclient.ui.theme.ThemeManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Standalone preview showing the new LionClient-style ClickGUI. Run with
 * {@code java -cp out net.hackclient.ui.preview.Preview}. Press RIGHT_SHIFT
 * to toggle the GUI; mouse/trackpad clicks drive the interface.
 */
public final class Preview extends JPanel {

    private static final int W = 1280, H = 720;

    private final BufferedImage buffer = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
    private final UIController ui = UIController.instance();
    private long lastFrame;

    private Preview() {
        setPreferredSize(new Dimension(W, H));
        setBackground(new java.awt.Color(20, 30, 42));
        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e)  { ui.onMousePress(e.getX(), e.getY(), e.getButton()); }
            @Override public void mouseReleased(MouseEvent e) { ui.onMouseRelease(e.getX(), e.getY(), e.getButton()); }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseMoved(MouseEvent e)   { ui.onMouseMove(e.getX(), e.getY()); }
            @Override public void mouseDragged(MouseEvent e) { ui.onMouseMove(e.getX(), e.getY()); }
        });
        addMouseWheelListener((MouseWheelEvent e) -> ui.onMouseScroll(e.getX(), e.getY(), -e.getWheelRotation()));
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                // Toggle on SHIFT (any) or CONTROL for safety
                if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ui.toggleScreen();
                }
                ui.onKeyPress(e.getKeyCode());
                if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED && e.getKeyChar() >= 32) {
                    ui.onCharType(e.getKeyChar());
                }
            }
        });
        setFocusable(true);

        ThemeManager.instance().register(LionPalette.lionDark());
        ThemeManager.instance().setCurrent("Lion Dark");
        HudConfig.loadFromResource("/hud.json");

        ArrayListElement al = HudRenderer.instance().arrayList();
        al.entries().clear();
        al.setEntries(Arrays.asList(
                new ArrayListElement.Entry("AutoClicker", null, 5),
                new ArrayListElement.Entry("Fullbright",   "[7]", 4),
                new ArrayListElement.Entry("AntiBot",      null, 3),
                new ArrayListElement.Entry("Aimbot",       null, 2),
                new ArrayListElement.Entry("Sprint",       null, 1)
        ));

        ui.openScreen();
        lastFrame = System.nanoTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long now = System.nanoTime();
        float dt = Math.min(0.05f, (now - lastFrame) / 1_000_000_000f);
        lastFrame = now;

        Graphics2D bg = buffer.createGraphics();
        // Dark game-like gradient backdrop
        java.awt.GradientPaint gp = new java.awt.GradientPaint(
                0, 0, new java.awt.Color(0x0E, 0x1A, 0x28),
                0, H, new java.awt.Color(0x1B, 0x2B, 0x3D));
        bg.setPaint(gp);
        bg.fillRect(0, 0, W, H);
        RenderContext ctx = new RenderContext(bg, W, H) { };

        ui.renderHud(ctx, W, H, dt);
        if (ui.isScreenOpen()) ui.renderScreen(ctx, W, H, dt);
        ctx.close();

        g.drawImage(buffer, 0, 0, null);
        repaint(16);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LionClient UI Preview (RIGHT_SHIFT to toggle GUI)");
        Preview preview = new Preview();
        frame.setContentPane(preview);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        frame.setVisible(true);
        preview.requestFocusInWindow();
    }
}
