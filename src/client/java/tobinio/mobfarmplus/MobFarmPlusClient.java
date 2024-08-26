package tobinio.mobfarmplus;

import net.fabricmc.api.ClientModInitializer;
import tobinio.mobfarmplus.particle.ModParticles;

public class MobFarmPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModParticles.initialize();
    }
}