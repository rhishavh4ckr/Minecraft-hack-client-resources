package net.hackclient.ui.component;

import net.hackclient.ui.render.RenderContext;
import net.hackclient.ui.theme.Color;
import net.hackclient.ui.util.MathUtil;
import net.hackclient.ui.util.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Animated floating-square "confetti" background seen behind LionClient's
 * GUI. Squares drift slowly down-right with low alpha; they are regenerated
 * as they leave the viewport. The renderer allocates particles lazily and
 * recycles them to keep per-frame GC pressure near zero.
 */
public class ConfettiBackground extends UIComponent {

    private static final int PARTICLE_COUNT = 40;
    private final List<Particle> particles = new ArrayList<>(PARTICLE_COUNT);
    private final Random rng = new Random(0xCAFEBABE);

    public ConfettiBackground() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            particles.add(Particle.random(rng, 0, 0, 1920, 1080));
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (Particle p : particles) {
            p.x += p.vx * delta;
            p.y += p.vy * delta;
            p.life -= delta;
            if (p.x > bounds.x + bounds.w + 20 || p.y > bounds.y + bounds.h + 20 || p.life <= 0f) {
                Particle.randomInto(p, rng, bounds);
            }
        }
    }

    @Override
    protected void onDraw(RenderContext ctx) {
        for (Particle p : particles) {
            float a = MathUtil.clamp(p.life / p.maxLife, 0f, 1f) * 0.55f;
            ctx.fillRect(p.x, p.y, p.size, p.size, new Color(0.7f, 0.8f, 0.95f, a));
        }
    }

    private static final class Particle {
        float x, y, vx, vy, size, life, maxLife;
        static Particle random(Random r, float x, float y, float w, float h) {
            Particle p = new Particle();
            randomInto(p, r, x, y, w, h);
            return p;
        }
        static Particle randomInto(Particle p, Random r, Rectangle bounds) {
            return randomInto(p, r, bounds.x, bounds.y, bounds.w, bounds.h);
        }
        static Particle randomInto(Particle p, Random r, float x0, float y0, float w, float h) {
            p.x = x0 + r.nextFloat() * w;
            p.y = y0 + r.nextFloat() * h;
            p.vx = 8f + r.nextFloat() * 18f;
            p.vy = 8f + r.nextFloat() * 22f;
            p.size = 2 + r.nextInt(4);
            p.maxLife = 3f + r.nextFloat() * 5f;
            p.life = p.maxLife * (0.3f + r.nextFloat() * 0.7f);
            return p;
        }
    }
}
