package com.starvinelonya.slowmadism.block.entity;

import com.starvinelonya.slowmadism.data.recipes.RiceNoodleRollMachineRecipe;
import com.starvinelonya.slowmadism.data.recipes.StoneMillRecipe;
import com.starvinelonya.slowmadism.registry.RecipeTypeRegistry;
import com.starvinelonya.slowmadism.registry.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * RiceNoodleRollMachineTile
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class RiceNoodleRollMachineTile extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public RiceNoodleRollMachineTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public RiceNoodleRollMachineTile() {
        this(TileEntityRegistry.RICE_NOODLE_ROLL_MACHINE_TILE.get());
    }

    @Override
    public void read(@NotNull BlockState state, @NotNull CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public @NotNull CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(8) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return super.extractItem(slot, amount, simulate);
            }
        };
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        assert world != null;
        if(world.isRemote) {
            return;
        }

        // 如果第六个slot存在燃料，则制作物品
        ItemStack fuelItemStack = itemHandler.getStackInSlot(6);
        if(!fuelItemStack.isEmpty() && ForgeHooks.getBurnTime(fuelItemStack, null) > 0) {
            craft();
        }
    }

    public void craft() {
        Inventory inv = new Inventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setInventorySlotContents(i, itemHandler.getStackInSlot(i));
        }

        assert world != null;
        Optional<RiceNoodleRollMachineRecipe> recipe = world.getRecipeManager()
                .getRecipe(RecipeTypeRegistry.RICE_NOODLE_ROLL_MACHINE_RECIPE, inv, world);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getRecipeOutput();
            craftTheItem(output);
            markDirty();
        });
    }

    private void craftTheItem(ItemStack output) {
        ItemStack itemStack = itemHandler.insertItem(7, output, false);
        if (itemStack == ItemStack.EMPTY) {
            itemHandler.extractItem(0, 1, false);
            itemHandler.extractItem(1, 1, false);
            itemHandler.extractItem(2, 1, false);
            itemHandler.extractItem(3, 1, false);
            itemHandler.extractItem(4, 1, false);
            itemHandler.extractItem(5, 1, false);
            itemHandler.extractItem(6, 1, false);
        }
    }

}
