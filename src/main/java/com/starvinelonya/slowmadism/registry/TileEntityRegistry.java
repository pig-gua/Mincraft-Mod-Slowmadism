package com.starvinelonya.slowmadism.registry;

import com.starvinelonya.slowmadism.Slowmadism;
import com.starvinelonya.slowmadism.block.entity.RiceNoodleRollMachineTile;
import com.starvinelonya.slowmadism.block.entity.StoneMillTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
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

    public static RegistryObject<TileEntityType<StoneMillTile>> STONE_MILL_TILE = TILE_ENTITIES.register("stone_mill_tile",
            () -> TileEntityType.Builder.create(StoneMillTile::new, BlockRegistry.STONE_MILL_BLOCK.get()).build(null));

    public static RegistryObject<TileEntityType<RiceNoodleRollMachineTile>> RICE_NOODLE_ROLL_MACHINE_TILE = TILE_ENTITIES.register("rice_noodle_roll_machine_tile",
            () -> TileEntityType.Builder.create(RiceNoodleRollMachineTile::new, BlockRegistry.RICE_NOODLE_ROLL_MACHINE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
