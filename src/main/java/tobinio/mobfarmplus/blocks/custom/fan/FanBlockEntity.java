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

        int maxDistance = FanBlock.getEffectDistance();

        var strengthFallOff = (getStrength() - (getStrength() * 0.3)) / maxDistance;

        Direction direction = state.get(FanBlock.FACING);
        //todo fix if multiple fans affecting 1 entity at the same time
        var entities = world.getOtherEntities(null,
                new Box(pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        pos.getX() + direction.getOffsetX() * maxDistance + 1,
                        pos.getY() + direction.getOffsetY() * maxDistance + 1,
                        pos.getZ() + direction.getOffsetZ() * maxDistance + 1));

        for (Entity entity : entities) {
            var strength = getStrength() - strengthFallOff * Math.sqrt(entity.squaredDistanceTo(pos.getX(),
                    pos.getY(),
                    pos.getZ()));

            entity.addVelocity(direction.getOffsetX() * strength,
                    direction.getOffsetY() * strength,
                    direction.getOffsetZ() * strength);
        }
    }

    public static double getStrength() {
        return 0.12;
    }
}
