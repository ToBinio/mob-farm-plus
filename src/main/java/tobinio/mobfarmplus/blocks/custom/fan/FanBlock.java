package tobinio.mobfarmplus.blocks.custom.fan;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tobinio.mobfarmplus.blocks.ModBlockEntityTypes;
import tobinio.mobfarmplus.particle.ModParticleTypes;


/**
 * Created: 26.08.24
 *
 * @author Tobias Frischmann
 */
public class FanBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.FACING;

    public FanBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(FanBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerLookDirection().getOpposite();

        if (ctx.getPlayer() != null && ctx.getPlayer().isSneaking()) {
            direction = direction.getOpposite();
        }

        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FanBlockEntity(pos, state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction direction = state.get(FACING);

        Vec3d centerPos = pos.toCenterPos();

        world.addImportantParticle(ModParticleTypes.FAN_PARTICLE,
                centerPos.getX() + (direction.getOffsetX() == 0 ? random.nextDouble() * 0.8 - 0.4 : 0),
                centerPos.getY() + (direction.getOffsetY() == 0 ? random.nextDouble() * 0.8 - 0.4 : 0),
                centerPos.getZ() + (direction.getOffsetZ() == 0 ? random.nextDouble() * 0.8 - 0.4 : 0),
                direction.getOffsetX() * (getEffectDistance() + 1),
                direction.getOffsetY() * (getEffectDistance() + 1),
                direction.getOffsetZ() * (getEffectDistance() + 1));
    }

    public static int getEffectDistance() {
        return 20;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntityTypes.FAN_BLOCK_ENTITY_TYPE, FanBlockEntity::tick);
    }
}
