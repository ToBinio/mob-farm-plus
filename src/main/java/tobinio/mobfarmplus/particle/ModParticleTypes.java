package tobinio.mobfarmplus.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tobinio.mobfarmplus.MobFarmPlus;

/**
 * Created: 26.08.24
 *
 * @author Tobias Frischmann
 */
public class ModParticleTypes {
    public static final SimpleParticleType FAN = FabricParticleTypes.simple();
    public static final SimpleParticleType VACUUM = FabricParticleTypes.simple();

    public static void initialize() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MobFarmPlus.MOD_ID, "fan"), FAN);
        Registry.register(Registries.PARTICLE_TYPE,
                Identifier.of(MobFarmPlus.MOD_ID, "vacuum"),
                VACUUM);
    }
}
