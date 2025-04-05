package com.starvinelonya.slowmadism.block;

import com.starvinelonya.slowmadism.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;

/**
 * SandGingerBlock 沙姜
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class SandGingerCrop extends CropsBlock {

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)
    };

    public SandGingerCrop(Properties builder) {
        super(builder);
    }

    @Override
    protected @NotNull IItemProvider getSeedsItem() {
        return ItemRegistry.SAND_GINGER.get();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }

}
