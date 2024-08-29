package tobinio.mobfarmplus.blocks.custom.fan;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import tobinio.mobfarmplus.blocks.ModBlockEntityTypes;

/**
 * Created: 26.08.24
 *
 * @author Tobias Frischmann
 */
public class FanBlockEntity extends BlockEntity {
    public FanBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.FAN_BLOCK_ENTITY_TYPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {
        if (!FanBlock.isActive(state)) {
            return;
        }

        int maxDistance = FanBlock.getMaxDistance();
        Direction direction = state.get(FanBlock.FACING);

        //TODO optimize function
        // only update on woldEvent
        var affectiveDistance = blockEntity.getNonBlockedDistance(maxDistance, direction);

        var strengthFallOff = (getStrength() - (getStrength() * 0.3)) / maxDistance;

        //todo fix if multiple fans affecting 1 entity at the same time
        var entities = world.getOtherEntities(null,
                new Box(pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        pos.getX() + direction.getOffsetX() * affectiveDistance + 1,
                        pos.getY() + direction.getOffsetY() * affectiveDistance + 1,
                        pos.getZ() + direction.getOffsetZ() * affectiveDistance + 1));

        var center = pos.toCenterPos();
        for (Entity entity : entities) {
            var strength = getStrength() - strengthFallOff * Math.sqrt(entity.squaredDistanceTo(center.getX(),
                    center.getY(),
                    center.getZ()));

            entity.addVelocity(direction.getOffsetX() * strength,
                    direction.getOffsetY() * strength,
                    direction.getOffsetZ() * strength);
        }
    }

    public int getNonBlockedDistance(int maxDistance, Direction direction) {
        var position = this.pos;

        if (world == null) return 0;

        for (int i = 0; i < maxDistance; i++) {
            position = position.add(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());

            if (world.getBlockState(position).isSideSolidFullSquare(world, position, direction) || world.getBlockState(
                    position).isSideSolidFullSquare(world, position, direction.getOpposite())) {
                return i;
            }
        }

        return maxDistance;
    }

    public static double getStrength() {
        return 0.12;
    }
}
