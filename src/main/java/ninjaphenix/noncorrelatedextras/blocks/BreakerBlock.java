package ninjaphenix.noncorrelatedextras.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import ninjaphenix.noncorrelatedextras.blocks.entities.BreakerBlockEntity;

public class BreakerBlock extends FacingBlock implements BlockEntityProvider
{
    private static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public BreakerBlock()
    {
        super(Block.Settings.copy(Blocks.COBBLESTONE));
        setDefaultState(getDefaultState().with(ACTIVE, false).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(FACING, ACTIVE);
    }


    @Override
    public BlockEntity createBlockEntity(BlockView view) { return new BreakerBlockEntity(); }
}
