package com.starvinelonya.slowmadism.registry;

import com.starvinelonya.slowmadism.Slowmadism;
import com.starvinelonya.slowmadism.block.SandGingerCrop;
import com.starvinelonya.slowmadism.group.SlowmadismItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * 注册 Block 及其对应的 BlockItem
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Slowmadism.MOD_ID);

    public static final RegistryObject<Block> SAND_GINGER_CROP = BLOCKS.register("sand_ginger_crop",
            () -> new SandGingerCrop(AbstractBlock.Properties.from(Blocks.WHEAT)));

    private static <T extends Block > RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block > void registerBlockItem(String name, RegistryObject<T> block) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
