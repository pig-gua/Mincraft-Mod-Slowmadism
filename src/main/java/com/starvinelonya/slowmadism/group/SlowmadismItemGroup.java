package com.starvinelonya.slowmadism.group;

import com.starvinelonya.slowmadism.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * SlowmadismItemGroup 物品分组
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class SlowmadismItemGroup extends ItemGroup {

    public static final SlowmadismItemGroup SLOWMADISM_ITEM_GROUP = new SlowmadismItemGroup();

    public SlowmadismItemGroup() {
        super("slowmadismTab");
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ItemRegistry.SAND_GINGER.get());
    }

}
