package com.starvinelonya.slowmadism;

import com.starvinelonya.slowmadism.registry.BlockRegistry;
import com.starvinelonya.slowmadism.registry.ItemRegistry;
import com.starvinelonya.slowmadism.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static com.starvinelonya.slowmadism.Slowmadism.MOD_ID;


/**
 * Slowmadism
 * Minecraft Forge mod, Mod_ID要和 META-INF/mods.toml文件里一致
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
@Mod(MOD_ID)
public class Slowmadism {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "slowmadism";

    public Slowmadism() {
        // 注册 setup 方法到模组加载事件总线中，用于在模组初始化阶段执行通用设置逻辑。
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // 注册 enqueueIMC 方法到模组加载事件总线中，用于在模组间通信（Inter-Mod Communication）阶段发送消息。
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // 注册 processIMC 方法到模组加载事件总线中，用于处理接收到的模组间通信消息。
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // 注册 doClientStuff 方法到模组加载事件总线中，用于在客户端初始化阶段执行特定于客户端的设置逻辑。
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // 将当前类注册到 MinecraftForge 的事件总线中，以便监听和响应服务器及其他游戏事件。
        MinecraftForge.EVENT_BUS.register(this);

        // 获取模组加载事件总线的引用，用于注册各种注册表。
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // 注册物品（Items）到模组的注册表中，确保自定义物品能够在游戏加载时正确注册。
        ItemRegistry.register(modEventBus);
        // 注册方块（Blocks）到模组的注册表中，确保自定义方块能够在游戏加载时正确注册。
        BlockRegistry.register(modEventBus);
        // 注册方块实体（Tile Entities）到模组的注册表中，确保自定义方块实体能够在游戏加载时正确注册。
        TileEntityRegistry.register(modEventBus);
    }

    /**
     * 在模组的通用设置阶段执行初始化逻辑。
     *
     * 该方法通过监听 {@link FMLCommonSetupEvent} 事件被调用，适用于在游戏加载的早期阶段执行通用设置任务。
     * 当前实现中，此方法主要用于记录日志信息，便于调试和确认加载流程。
     *
     * @param event FMLCommonSetupEvent 事件对象，提供对通用设置阶段的访问。
     */
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PRE INIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    /**
     * 在模组间通信（Inter-Mod Communication, IMC）阶段发送消息。
     *
     * 该方法通过监听 {@link InterModEnqueueEvent} 事件被调用，用于向其他模组发送通信消息。
     * 当前实现中，此方法向名为 "slow_madism" 的模组发送了一条示例消息 "Hello world"，并在日志中记录相关信息。
     *
     * @param event InterModEnqueueEvent 事件对象，提供对模组间通信阶段的访问。
     */
    private void enqueueIMC(final InterModEnqueueEvent event) {
        // 接收方ModID, 消息类型标识, 消息内容
        InterModComms.sendTo(MOD_ID, "helloWorld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    /**
     * 在模组间通信（Inter-Mod Communication, IMC）阶段处理接收到的消息。
     *
     * 该方法通过监听 {@link InterModProcessEvent} 事件被调用，用于处理接收到的模组间通信消息。
     * 当前实现中，此方法仅记录接收到的消息内容，并在日志中记录相关信息。
     *
     * @param event InterModProcessEvent 事件对象，提供对模组间通信阶段的访问。
     */
    private void processIMC(final InterModProcessEvent event) {
        // 从事件中获取接收到的消息列表。
        List<Object> messageList = event.getIMCStream()
                .map(m -> m.getMessageSupplier().get())
                .collect(Collectors.toList());
        LOGGER.info("Got IMC {}", messageList);
    }

    /**
     * 在客户端初始化阶段执行特定于客户端的设置逻辑。
     *
     * 该方法通过监听 {@link FMLClientSetupEvent} 事件被调用，适用于在游戏加载的早期阶段执行客户端设置任务。
     * 当前实现中，此方法主要用于注册客户端相关的渲染逻辑，如方块的渲染类型设置等。
     *
     * @param event FMLClientSetupEvent 事件对象，提供对客户端设置的访问。
     */
    private void doClientStuff(final FMLClientSetupEvent event) {
        // 客户端初始化阶段（FMLClientSetupEvent）执行异步任务
        event.enqueueWork(() -> {
            // 农作物渲染
            RenderTypeLookup.setRenderLayer(BlockRegistry.SAND_GINGER_CROP.get(), RenderType.getCutout());
        });
    }

    /**
     * 在服务器启动时执行相关逻辑。
     *
     * 该方法通过监听 {@link FMLServerStartingEvent} 事件被调用，适用于在服务器启动阶段执行初始化任务或注册命令。
     * 当前实现中，此方法尚未包含具体逻辑，但可以扩展用于：
     * - 注册自定义服务器命令。
     * - 初始化服务器端特定的数据或配置。
     *
     * @param event FMLServerStartingEvent 事件对象，提供对服务器启动阶段的访问。
     */
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info(String.format("HELLO %s from server starting", MOD_ID));
    }

    /**
     * 用于监听和处理模组注册表事件的静态内部类。
     *
     * 该类通过 {@link Mod.EventBusSubscriber} 注解订阅到模组事件总线（MOD Event Bus），专门用于处理注册表相关的事件。
     * 当前实现中，该类包含一个方法用于在方块注册事件中执行相关逻辑。
     */
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {


        /**
         * 在方块注册事件中执行相关逻辑。
         *
         * 该方法通过监听 {@link RegistryEvent.Register<Block>} 事件被调用，适用于在游戏加载时注册自定义方块。
         * 当前实现中，此方法尚未包含具体逻辑，但可以扩展用于：
         * - 注册新的方块。
         * - 对已注册的方块进行额外配置或初始化。
         *
         * @param blockRegistryEvent RegistryEvent.Register<Block> 事件对象，提供对方块注册阶段的访问。
         */
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {

        }

    }

}
