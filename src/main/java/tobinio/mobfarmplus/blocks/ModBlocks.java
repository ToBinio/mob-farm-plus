package tobinio.mobfarmplus.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tobinio.mobfarmplus.MobFarmPlus;
import tobinio.mobfarmplus.blocks.custom.SpikeBlock;
import tobinio.mobfarmplus.blocks.custom.fan.FanBlock;
import tobinio.mobfarmplus.blocks.custom.vacuum.VacuumBlock;

/**
 * Created: 26.08.24
 *
 * @author Tobias Frischmann
 */
public class ModBlocks {
    public static final Block FAN = register(new FanBlock(AbstractBlock.Settings.create()), "fan", true);
    public static final Block VACUUM = register(new VacuumBlock(AbstractBlock.Settings.create()), "vacuum", true);
    public static final Block SPIKE = register(new SpikeBlock(AbstractBlock.Settings.create()), "spike", true);

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(MobFarmPlus.MOD_ID, name);

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {
    }
}
