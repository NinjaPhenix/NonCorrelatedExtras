package ninjaphenix.noncorrelatedextras.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

@SuppressWarnings("deprecation")
public abstract class WaterFillableBlock extends Block implements Waterloggable
{
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public WaterFillableBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public boolean canFillWithFluid(BlockView view, BlockPos pos, BlockState state, Fluid fluid)
    {
        return fluid == Fluids.WATER;
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) { builder.add(WATERLOGGED); }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        return this.getDefaultState().with(Properties.WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).getFluid() == Fluids.WATER);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState nState, IWorld world, BlockPos pos, BlockPos nPos)
    {
        if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) { return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state); }
}
