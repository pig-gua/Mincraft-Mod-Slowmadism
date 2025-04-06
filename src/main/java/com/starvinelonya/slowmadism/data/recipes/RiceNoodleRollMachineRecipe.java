package com.starvinelonya.slowmadism.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.starvinelonya.slowmadism.registry.BlockRegistry;
import com.starvinelonya.slowmadism.registry.RecipeTypeRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * CheungFunRecipe
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class RiceNoodleRollMachineRecipe implements IRiceNoodleRollMachineRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public RiceNoodleRollMachineRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(@NotNull IInventory inv, @NotNull World worldIn) {
        List<ItemStack> inputs = new ArrayList<>();

        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (!itemstack.isEmpty() && j != 6) {
                inputs.add(itemstack);
            }
        }

        System.out.println(inputs);
        System.out.println(recipeItems);
        System.out.println(inputs.size() == this.recipeItems.size() && (RecipeMatcher.findMatches(inputs,  this.recipeItems) != null));

        return inputs.size() == this.recipeItems.size() && (RecipeMatcher.findMatches(inputs,  this.recipeItems) != null);
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public @NotNull ItemStack getCraftingResult(@NotNull IInventory inv) {
        return output;
    }

    @Override
    public @NotNull ItemStack getRecipeOutput() {
        return output.copy();
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return new ItemStack(BlockRegistry.RICE_NOODLE_ROLL_MACHINE_BLOCK.get());
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull IRecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.RICE_NOODLE_ROLL_MACHINE_SERIALIZER.get();
    }

    public static class RiceNoodleRollMachineRecipeType implements IRecipeType<RiceNoodleRollMachineRecipe> {
        @Override
        public String toString() {
            return RiceNoodleRollMachineRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RiceNoodleRollMachineRecipe> {

        @Override
        public @NotNull RiceNoodleRollMachineRecipe read(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));

            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.deserialize(ingredients.get(i)));
            }

            return new RiceNoodleRollMachineRecipe(recipeId, output, inputs);
        }

        @Override
        public RiceNoodleRollMachineRecipe read(@NotNull ResourceLocation recipeId, @NotNull PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.read(buffer));
            }

            ItemStack output = buffer.readItemStack();
            return new RiceNoodleRollMachineRecipe(recipeId, output, inputs);
        }

        @Override
        public void write(PacketBuffer buffer, RiceNoodleRollMachineRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buffer);
            }
            buffer.writeItemStack(recipe.getRecipeOutput(), false);
        }
    }

}
