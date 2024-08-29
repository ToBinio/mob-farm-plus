package tobinio.mobfarmplus.blocks.custom.fan;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public FanBlock(Settings settings) {
        super(settings);

        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ACTIVE, false));

    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(FanBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(ACTIVE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerLookDirection().getOpposite();

        if (ctx.getPlayer() != null && ctx.getPlayer().isSneaking()) {
            direction = direction.getOpposite();
        }

        BlockState state = this.getDefaultState().with(FACING, direction);

        if (ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())) {
            state = state.with(ACTIVE, true);
        }

        return state;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos,
            boolean notify) {
        if (!world.isClient) {
            boolean bl = state.get(ACTIVE);
            if (bl != world.isReceivingRedstonePower(pos)) {
                world.setBlockState(pos, state.cycle(ACTIVE), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

        if (!isActive(state)) {
            return;
        }

        Direction direction = state.get(FACING);

        Vec3d centerPos = pos.toCenterPos();

        world.addImportantParticle(ModParticleTypes.FAN,
                centerPos.getX() + (direction.getOffsetX() == 0 ? random.nextDouble() * 0.8 - 0.4 : 0),
                centerPos.getY() + (direction.getOffsetY() == 0 ? random.nextDouble() * 0.8 - 0.4 : 0),
                centerPos.getZ() + (direction.getOffsetZ() == 0 ? random.nextDouble() * 0.8 - 0.4 : 0),
                direction.getOffsetX() * (getMaxDistance() + 1),
                direction.getOffsetY() * (getMaxDistance() + 1),
                direction.getOffsetZ() * (getMaxDistance() + 1));
    }

    public static boolean isActive(BlockState state) {
        return state.get(ACTIVE);
    }

    public static int getMaxDistance() {
        return 20;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FanBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntityTypes.FAN_BLOCK_ENTITY_TYPE, FanBlockEntity::tick);
    }
}
