package com.starvinelonya.slowmadism.registry;

import com.starvinelonya.slowmadism.Slowmadism;
import com.starvinelonya.slowmadism.group.SlowmadismItemGroup;
import com.starvinelonya.slowmadism.item.CheungFunItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 注册 Item
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Slowmadism.MOD_ID);

    public static final RegistryObject<Item> SAND_GINGER = ITEMS.register("sand_ginger",
            () -> new BlockItem(BlockRegistry.SAND_GINGER_CROP.get(), new Item.Properties()
                    .food(new Food.Builder().hunger(1).saturation(0.1f).fastToEat().build())
                    .group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)));

    public static final RegistryObject<Item> RICE = ITEMS.register("rice",
            () -> new Item(new Item.Properties()
                    .group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)));

    public static final RegistryObject<Item> CHEUNG_FUN_DISH = ITEMS.register("cheung_fun_dish",
            () -> new Item(new Item.Properties()
                    .group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)));

    public static final RegistryObject<Item> PLAIN_CHEUNG_FUN = ITEMS.register("plain_cheung_fun",
            () -> new CheungFunItem(new Item.Properties()
                    .food((new Food.Builder()).hunger(6).saturation(10.0f).build())
                    .group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)));


    public static final RegistryObject<Item> RICE_MILK_BUCKET = ITEMS.register("rice_milk_bucket",
            () -> new BucketItem(() -> FluidRegistry.RICE_MILK_FLUID.get(), new Item.Properties()
                    .maxStackSize(1)
                    .group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)));

    public static final RegistryObject<Item> STONE_MILL_UPPER = ITEMS.register("stone_mill_upper",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
