package tobinio.mobfarmplus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import tobinio.mobfarmplus.blocks.ModBlocks;
import tobinio.mobfarmplus.particle.ModParticles;

public class MobFarmPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModParticles.initialize();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FAN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPIKE, RenderLayer.getCutout());
    }
}