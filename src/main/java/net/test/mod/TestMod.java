package net.test.mod;

import java.lang.System.Logger;
import java.util.List;
import java.util.Optional;
import java.util.logging.LogManager;

import org.lwjgl.glfw.GLFW;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class TestMod implements ModInitializer {

    public static LogManager LOGGER = LogManager.getLogManager();

    public static final String MOD_ID = "test_mod";
    public static final String MOD_NAME = "TestMod";

    public static final Item SCRATCH_PACK = new Item(new Item.Settings ().group(ItemGroup.MISC).maxCount(1));
    public static final Identifier BACKPACK_IDENTIFIER = new Identifier(MOD_ID, "scratch_pack");
    private static KeyBinding keyBinding;
    public static final String BACKPACK_TRANSLATION_KEY = Util.createTranslationKey("container", BACKPACK_IDENTIFIER);

    public static final Identifier OPEN_PACK_PACKET = new Identifier("testmod", "open_pack");

    public static final ScreenHandlerType<BagScreenHandler> BAG_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier("testmod", "bag"), BagScreenHandler::new);

    @Override
    public void onInitialize() 
    {
        Registry.register(Registry.ITEM, new Identifier("testmod", "scratch_pack"), SCRATCH_PACK);

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.testmod.scratch_pack", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_B, // The keycode of the key
            "category.testmod.scratch_pack" // The translation key of the keybinding's category.
        ));

        ServerPlayNetworking.registerGlobalReceiver(OPEN_PACK_PACKET, (server, player, handler, buffer, responseSender) -> {
            Optional<TrinketComponent> trinketcomponent = TrinketsApi.getTrinketComponent(player);
            
            if (trinketcomponent.get().isEquipped(SCRATCH_PACK)) {
                List<Pair<SlotReference, ItemStack>> pairs = trinketcomponent.get().getEquipped(SCRATCH_PACK);
                ItemStack pack = pairs.get(0).getRight();
                player.openHandledScreen(createScreenHandlerFactory(pack));
                
                // ContainerProviderRegistry.INSTANCE.openContainer(TestMod.BACKPACK_IDENTIFIER, player, buf -> {
                //     buf.writeItemStack(pack);
                //     buf.writeInt(0);
                //     buf.writeString(pack.getName().asString());
                // });
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                ClientPlayerEntity player = client.player;
                Optional<TrinketComponent> trinketcomponent = TrinketsApi.getTrinketComponent(player);

                if (trinketcomponent.get().isEquipped(SCRATCH_PACK)) {
                    ClientPlayNetworking.send(OPEN_PACK_PACKET, PacketByteBufs.create());
                }
            }
        });
    }

    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
			return new BagScreenHandler(syncId, inventory, new ScratchpackInventory(stack));
		}, stack.getName());
    }
    
    public static void log(java.lang.System.Logger.Level level, String message)
    {
        ((Logger) LOGGER).log(level, "["+MOD_NAME+"] " + message);
    }
}