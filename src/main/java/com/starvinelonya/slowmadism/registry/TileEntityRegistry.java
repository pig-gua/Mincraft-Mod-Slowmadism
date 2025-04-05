package com.starvinelonya.slowmadism.registry;

import com.starvinelonya.slowmadism.Slowmadism;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 注册 TileEntity
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class TileEntityRegistry {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Slowmadism.MOD_ID);

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
