package tobinio.mobfarmplus.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.util.Identifier;
import tobinio.mobfarmplus.MobFarmPlus;
import tobinio.mobfarmplus.blocks.ModBlocks;

/**
 * Created: 29.08.24
 *
 * @author Tobias Frischmann
 */
public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerParentedItemModel(ModBlocks.FAN, Identifier.of(MobFarmPlus.MOD_ID, "block/fan_still"));
        blockStateModelGenerator.registerParentedItemModel(ModBlocks.VACUUM,
                Identifier.of(MobFarmPlus.MOD_ID, "block/vacuum"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
