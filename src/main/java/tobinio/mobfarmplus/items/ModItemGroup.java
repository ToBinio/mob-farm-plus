package tobinio.mobfarmplus.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import tobinio.mobfarmplus.MobFarmPlus;
import tobinio.mobfarmplus.blocks.ModBlocks;

/**
 * Created: 29.08.24
 *
 * @author Tobias Frischmann
 */
public class ModItemGroup {
    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),
            Identifier.of(MobFarmPlus.MOD_ID, "item_group"));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.FAN.asItem()))
            .displayName(Text.translatable("itemGroup.mob_farm_plus"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModBlocks.FAN.asItem());
            itemGroup.add(ModBlocks.VACUUM.asItem());
            itemGroup.add(ModBlocks.SPIKE.asItem());
        });
    }
}
