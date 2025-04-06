package com.starvinelonya.slowmadism.registry;

import com.starvinelonya.slowmadism.Slowmadism;
import com.starvinelonya.slowmadism.block.SandGingerCrop;
import com.starvinelonya.slowmadism.block.StoneMillBlock;
import com.starvinelonya.slowmadism.group.SlowmadismItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
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

    public static final RegistryObject<Block> STONE_MILL_BLOCK = registerBlock("stone_mill",
            () -> new StoneMillBlock(AbstractBlock.Properties.create(Material.ROCK).notSolid()),
            StoneMillBlock.addToolTip());

    private static <T extends Block > RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block > RegistryObject<T> registerBlock(String name, Supplier<T> block, Consumer<List<ITextComponent>> tooltipConsumer) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tooltipConsumer);
        return toReturn;
    }

    private static <T extends Block > void registerBlockItem(String name, RegistryObject<T> block) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)));
    }

    private static <T extends Block > void registerBlockItem(String name, RegistryObject<T> block, Consumer<List<ITextComponent>> tooltipConsumer) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(SlowmadismItemGroup.SLOWMADISM_ITEM_GROUP)) {
            @Override
            public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
                tooltipConsumer.accept(tooltip);
                super.addInformation(stack, worldIn, tooltip, flagIn);
            }
        });
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
