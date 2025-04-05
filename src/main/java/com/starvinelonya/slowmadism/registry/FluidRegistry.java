package com.starvinelonya.slowmadism.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.starvinelonya.slowmadism.Slowmadism;

/**
 * 注册 Fluid
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class FluidRegistry {

    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Slowmadism.MOD_ID);

    public static final RegistryObject<FlowingFluid> RICE_MILK_FLUID = FLUIDS.register("rice_milk_fluid",
            () -> new ForgeFlowingFluid.Source(FluidRegistry.RICE_MILK_PROPERTIES));

    public static final RegistryObject<FlowingFluid> RICE_MILK_FLOWING = FLUIDS.register("rice_milk_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidRegistry.RICE_MILK_PROPERTIES));

    public static final RegistryObject<FlowingFluidBlock> RICE_MILK_BLOCK = BlockRegistry.BLOCKS.register("rice_milk",
            () -> new FlowingFluidBlock(() -> FluidRegistry.RICE_MILK_FLUID.get(), AbstractBlock.Properties.create(Material.WATER)
                    .doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));

    public static final ForgeFlowingFluid.Properties RICE_MILK_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> RICE_MILK_FLUID.get(),
            () -> RICE_MILK_FLOWING.get(),
            FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
                    .density(15)
                    .luminosity(2)
                    .viscosity(5)
                    .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
                    .overlay(WATER_OVERLAY_RL)
                    .color(0xbff2d7b8))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .block(() -> FluidRegistry.RICE_MILK_BLOCK.get())
            .bucket(() -> ItemRegistry.RICE_MILK_BUCKET.get());

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

}
