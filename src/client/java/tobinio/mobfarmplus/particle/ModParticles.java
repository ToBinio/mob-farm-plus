package tobinio.mobfarmplus.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ConnectionParticle;
import tobinio.mobfarmplus.particle.custom.FanParticle;

/**
 * Created: 26.08.24
 *
 * @author Tobias Frischmann
 */
public class ModParticles {
    public static void initialize() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.FAN, FanParticle.FanFactory::new);
        ParticleFactoryRegistry.getInstance()
                .register(ModParticleTypes.VACUUM, ConnectionParticle.NautilusFactory::new);
    }
}
