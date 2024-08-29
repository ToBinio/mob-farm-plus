package tobinio.mobfarmplus.blocks.custom.vacuum;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import tobinio.mobfarmplus.blocks.ModBlockEntityTypes;

import java.util.List;

/**
 * Created: 28.08.24
 *
 * @author Tobias Frischmann
 */
public class VacuumBlockEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);

    public VacuumBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.VACUUM_BLOCK_ENTITY_TYPE, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, VacuumBlockEntity blockEntity) {
        var items = world.getEntitiesByClass(ItemEntity.class,
                Box.enclosing(pos.add(-3, -3, -3), pos.add(3, 3, 3)),
                (entity) -> true);

        for (ItemEntity item : items) {
            Vec3d vector = pos.toCenterPos().subtract(item.getPos()).normalize().multiply(0.1);
            item.addVelocity(vector.x, vector.y, vector.z);
        }
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, VacuumBlockEntity blockEntity) {
        spawnNautilusParticles(world, pos);
    }

    private static void spawnNautilusParticles(World world, BlockPos pos) {
        Random random = world.random;
        Vec3d vec3d = new Vec3d((double) pos.getX() + 0.5, (double) pos.getY() + 1.5, (double) pos.getZ() + 0.5);

        for (int i = 0; i < 10; i++) {
            if (random.nextInt(50) == 0) {
                float f = random.nextFloat() * 3 - 1.5f;
                float g = random.nextFloat() * 3 - 2.5f;
                float h = random.nextFloat() * 3 - 1.5f;
                world.addParticle(ParticleTypes.NAUTILUS,
                        vec3d.x,
                        vec3d.y,
                        vec3d.z,
                        f,
                        g,
                        h);
            }
        }
    }

    public static void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity,
            VacuumBlockEntity vacuumBlockEntity) {
        if (entity instanceof ItemEntity item) {
            InventoryStorage storageViews = InventoryStorage.of(vacuumBlockEntity, null);

            System.out.println(storageViews.getSlotCount());

            try (Transaction transaction = Transaction.openOuter()) {
                ItemStack stack = item.getStack();

                long insert = storageViews.insert(ItemVariant.of(stack), stack.getCount(), transaction);

                if (insert == stack.getCount()) {
                    entity.discard();
                    transaction.commit();
                }
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, items, registryLookup);
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, items, registryLookup);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected Text getContainerName() {
        return Text.of("Vacuum");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return items;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.items = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int size() {
        return 5;
    }
}
