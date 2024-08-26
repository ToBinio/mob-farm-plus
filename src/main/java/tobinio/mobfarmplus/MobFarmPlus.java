package tobinio.mobfarmplus;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tobinio.mobfarmplus.blocks.ModBlockEntityTypes;
import tobinio.mobfarmplus.blocks.ModBlocks;
import tobinio.mobfarmplus.particle.ModParticleTypes;

public class MobFarmPlus implements ModInitializer {
    public static final String MOD_ID = "mob-farm-plus";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModBlockEntityTypes.initialize();

        ModParticleTypes.initialize();
    }
}