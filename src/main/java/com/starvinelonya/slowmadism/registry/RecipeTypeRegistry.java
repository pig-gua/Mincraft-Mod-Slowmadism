package com.starvinelonya.slowmadism.registry;

import com.starvinelonya.slowmadism.Slowmadism;
import com.starvinelonya.slowmadism.data.recipes.RiceNoodleRollMachineRecipe;
import com.starvinelonya.slowmadism.data.recipes.StoneMillRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * RecipeTypeRegistry
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class RecipeTypeRegistry {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Slowmadism.MOD_ID);

    public static final RegistryObject<StoneMillRecipe.Serializer> STONE_MILL_SERIALIZER = RECIPE_SERIALIZER.register("stone_mill", StoneMillRecipe.Serializer::new);
    public static final RegistryObject<RiceNoodleRollMachineRecipe.Serializer> RICE_NOODLE_ROLL_MACHINE_SERIALIZER = RECIPE_SERIALIZER.register("rice_noodle_roll_machine", RiceNoodleRollMachineRecipe.Serializer::new);

    public static IRecipeType<StoneMillRecipe> STONE_MILL_RECIPE = new StoneMillRecipe.StoneMillRecipeType();
    public static IRecipeType<RiceNoodleRollMachineRecipe> RICE_NOODLE_ROLL_MACHINE_RECIPE = new RiceNoodleRollMachineRecipe.RiceNoodleRollMachineRecipeType();

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, StoneMillRecipe.TYPE_ID, STONE_MILL_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, RiceNoodleRollMachineRecipe.TYPE_ID, RICE_NOODLE_ROLL_MACHINE_RECIPE);
    }

}
