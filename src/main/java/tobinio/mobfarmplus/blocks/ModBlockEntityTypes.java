package tobinio.mobfarmplus.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tobinio.mobfarmplus.MobFarmPlus;
import tobinio.mobfarmplus.blocks.custom.fan.FanBlockEntity;

/**
 * Created: 26.08.24
 *
 * @author Tobias Frischmann
 */
public class ModBlockEntityTypes {

    public static final BlockEntityType<FanBlockEntity> FAN_BLOCK_ENTITY_TYPE = register("fan_block",
            BlockEntityType.Builder.create(FanBlockEntity::new, ModBlocks.FAN).build());

    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MobFarmPlus.MOD_ID, path),
                blockEntityType);
    }

    public static void initialize() {
    }
}
