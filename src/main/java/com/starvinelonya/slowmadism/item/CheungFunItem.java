package com.starvinelonya.slowmadism.item;

import com.starvinelonya.slowmadism.registry.ItemRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * CheungFunItem
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class CheungFunItem extends Item {

    public CheungFunItem(Properties properties) {
        super(properties);
    }

    /**
     * 当玩家使用完肠粉（CheungFun）物品时触发此方法。
     * 该方法处理肠粉的消耗逻辑，并在特定条件下返回一个空盘子（CheungFunDish）。
     *
     * @param stack  被使用的物品堆栈。
     * @param world  物品被使用时所在的当前世界。
     * @param entity 使用物品的生物实体（通常是玩家）。
     * @return 返回使用后的结果物品（如空盘子）或原物品堆栈。
     */
    @Override
    public @NotNull ItemStack onItemUseFinish(@NotNull ItemStack stack, @NotNull World world, @NotNull LivingEntity entity) {
        super.onItemUseFinish(stack, world, entity);
        // 如果是玩家使用了物品，触发“消耗物品”成就并记录统计信息
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.addStat(Stats.ITEM_USED.get(this));
        }

        // 如果当前物品堆栈为空，则返回一个空盘子（CheungFunDish）
        if (stack.isEmpty()) {
            return new ItemStack(ItemRegistry.CHEUNG_FUN_DISH.get());
        }

        // 如果实体是玩家且不具备创造模式能力，则尝试将空盘子（CheungFunDish）添加到玩家的库存中
        // 如果无法添加到库存，则将空盘子丢弃在玩家附近
        if (entity instanceof PlayerEntity && !((PlayerEntity) entity).abilities.isCreativeMode) {
            ItemStack itemStack = new ItemStack(ItemRegistry.CHEUNG_FUN_DISH.get());
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.inventory.addItemStackToInventory(itemStack)) {
                player.dropItem(itemStack, false);
            }
        }

        // 返回原物品堆栈
        return stack;
    }

}
