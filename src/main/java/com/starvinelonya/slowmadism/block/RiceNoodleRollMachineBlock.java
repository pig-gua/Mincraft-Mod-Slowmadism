package com.starvinelonya.slowmadism.block;

import com.starvinelonya.slowmadism.block.entity.RiceNoodleRollMachineTile;
import com.starvinelonya.slowmadism.container.RiceNoodleRollMachineContainer;
import com.starvinelonya.slowmadism.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * RiceNoodleRollMachineBlock
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class RiceNoodleRollMachineBlock extends HorizontalBlock {

    private static final BooleanProperty OPEN = BooleanProperty.create("open");
    private static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");

    public RiceNoodleRollMachineBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull ActionResultType onBlockActivated(@NotNull BlockState state,
                                                      World worldIn,
                                                      @NotNull BlockPos pos,
                                                      @NotNull PlayerEntity player,
                                                      @NotNull Hand handIn,
                                                      @NotNull BlockRayTraceResult hit) {
        // 获取实体
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(!(tileEntity instanceof RiceNoodleRollMachineTile)) {
            return ActionResultType.SUCCESS;
        }

        // 非服务端 -> 右键打开GUI
        if(!worldIn.isRemote()) {
            INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
            NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getPos());
        }

        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public @NotNull ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.slowmadism.rice_noodle_roll_machine");
            }

            @Override
            public Container createMenu(int i, @NotNull PlayerInventory playerInventory, @NotNull PlayerEntity playerEntity) {
                return new RiceNoodleRollMachineContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    public static Consumer<List<ITextComponent>> addToolTip() {
        return tooltip -> {
            if(Screen.hasShiftDown()) {
                tooltip.add(new TranslationTextComponent("tooltip.slowmadism.rice_noodle_roll_machine_shift"));
            } else {
                tooltip.add(new TranslationTextComponent("tooltip.slowmadism.rice_noodle_roll_machine"));
            }
        };
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.RICE_NOODLE_ROLL_MACHINE_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        // 向 BlockState 构建器添加 HorizontalBlock.FACING 属性。
        builder.add(HorizontalBlock.HORIZONTAL_FACING);
        // 向 BlockState 构建器添加 OPEN 属性，表示是否打开。
        builder.add(OPEN);
        // 向 BlockState 构建器添加 HAS_ITEM 属性，表示是否有物品。
        builder.add(HAS_ITEM);
        // 调用父类的 createBlockStateDefinition 方法，完成 BlockState 定义。
        super.fillStateContainer(builder);
    }

    /**
     * 获取放置 RiceNoodleRollMachineBlock 时的初始 BlockState。
     *
     * @param context 使用方块物品放置时的上下文信息。
     * @return 初始 BlockState，包含方向、打开状态和是否有物品的状态。
     */
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        // 获取默认的 BlockState。
        return this.getDefaultState()
                // 设置 FACING 属性为玩家放置方块时的相反方向。
                .with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite())
                // 设置 OPEN 属性为 false，表示初始状态下方块是关闭的。
                .with(OPEN, Boolean.FALSE)
                // 设置 HAS_ITEM 属性为 false，表示初始状态下方块中没有物品。
                .with(HAS_ITEM, Boolean.FALSE);
    }

}
