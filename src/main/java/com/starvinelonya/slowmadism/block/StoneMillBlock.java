package com.starvinelonya.slowmadism.block;

import com.starvinelonya.slowmadism.block.entity.StoneMillTile;
import com.starvinelonya.slowmadism.container.StoneMillContainer;
import com.starvinelonya.slowmadism.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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

/**
 * StoneMillBlock 石磨
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class StoneMillBlock extends Block {

    public StoneMillBlock(Properties properties) {
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
        // 获取石磨实体
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(!(tileEntity instanceof StoneMillTile)) {
            return ActionResultType.SUCCESS;
        }

        // 非服务端 && 非潜行状态 -> 右键打开GUI
        if(!worldIn.isRemote() && !player.isCrouching()) {
            INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
            NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getPos());
        }

        // 潜行状态 -> 右键旋转石磨
        if (player.isCrouching()) {
            StoneMillTile stoneMillTile = (StoneMillTile) tileEntity;
            stoneMillTile.rotate();
        }

        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public @NotNull ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.slowmadism.stone_mill");
            }

            @Override
            public Container createMenu(int i, @NotNull PlayerInventory playerInventory, @NotNull PlayerEntity playerEntity) {
                return new StoneMillContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.STONE_MILL_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

}
