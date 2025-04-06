package com.starvinelonya.slowmadism.data.recipes;

import com.starvinelonya.slowmadism.Slowmadism;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * IStoneMillRecipe
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public interface IStoneMillRecipe extends IRecipe<IInventory> {

    ResourceLocation TYPE_ID = new ResourceLocation(Slowmadism.MOD_ID, "stone_mill");

    @Override
    default IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return true;
    }

    @Override
    default boolean isDynamic(){
        return true;
    }

}
