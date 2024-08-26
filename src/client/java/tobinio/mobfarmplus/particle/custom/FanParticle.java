package tobinio.mobfarmplus.particle.custom;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import tobinio.mobfarmplus.blocks.custom.fan.FanBlock;

/**
 * Created: 26.08.24
 *
 * @author Tobias Frischmann
 */
public class FanParticle extends SpriteBillboardParticle {

    public static final Double speed = 0.1;

    private float alphaDrop;

    protected FanParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY,
            double velocityZ) {
        super(world, x, y, z);

        var distance = Math.abs(velocityX + velocityY + velocityZ);

        this.maxAge = (int) (distance / speed);
        this.velocityMultiplier = 0.99f;

        var speedOffset = 0.;
        for (int i = this.maxAge; i > 0; i--) {
            speedOffset += Math.pow(this.velocityMultiplier, i);
        }

        this.maxAge += world.random.nextBetween(-10, 10);
        this.alphaDrop = this.alpha / this.maxAge;

        var speed = distance / speedOffset;

        this.velocityX = getSpeed(velocityX, speed);
        this.velocityY = getSpeed(velocityY, speed);
        this.velocityZ = getSpeed(velocityZ, speed);
    }

    private double getSpeed(double direction, double speed) {
        if (direction > 0) return speed;
        if (direction < 0) return -speed;

        return 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (alpha > 0.01f) {
            this.alpha -= alphaDrop;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class FanFactory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public FanFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d,
                double e, double f, double g, double h, double i) {
            FanParticle particle = new FanParticle(clientWorld, d, e, f, g, h, i);
            particle.setAlpha(0.9F);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
