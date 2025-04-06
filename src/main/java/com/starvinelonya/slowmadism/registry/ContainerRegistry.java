package com.starvinelonya.slowmadism.registry;

import com.starvinelonya.slowmadism.Slowmadism;
import com.starvinelonya.slowmadism.container.RiceNoodleRollMachineContainer;
import com.starvinelonya.slowmadism.container.StoneMillContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * ContainerRegistry
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class ContainerRegistry {

    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Slowmadism.MOD_ID);

    public static final RegistryObject<ContainerType<StoneMillContainer>> STONE_MILL_CONTAINER
            = CONTAINERS.register("stone_mill_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new StoneMillContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<RiceNoodleRollMachineContainer>> RICE_NOODLE_ROLL_MACHINE_CONTAINER
            = CONTAINERS.register("rice_noodle_roll_machine_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new RiceNoodleRollMachineContainer(windowId, world, pos, inv, inv.player);
            })));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }

}
