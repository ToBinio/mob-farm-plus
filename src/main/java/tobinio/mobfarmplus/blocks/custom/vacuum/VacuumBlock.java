package tobinio.mobfarmplus.blocks.custom.vacuum;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tobinio.mobfarmplus.blocks.ModBlockEntityTypes;

/**
 * Created: 28.08.24
 *
 * @author Tobias Frischmann
 */
public class VacuumBlock extends BlockWithEntity{
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 4.0, 4.0, 12.0, 12.0, 12.0);

    public VacuumBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(VacuumBlock::new);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VacuumBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return validateTicker(type,
                ModBlockEntityTypes.VACUUM_BLOCK_ENTITY_TYPE,
                world.isClient ? VacuumBlockEntity::clientTick : VacuumBlockEntity::serverTick);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof VacuumBlockEntity vacuumBlockEntity) {
            VacuumBlockEntity.onEntityCollision(world, pos, state, entity, vacuumBlockEntity);
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ItemScatterer.onStateReplaced(state, newState, world, pos);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
