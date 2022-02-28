package net.test.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class ScratchpackClient implements ClientModInitializer
{
    // public static final ScreenHandlerType<PackScreenHandler> SCRATCH_PACK = ScreenHandlerRegistry.registerSimple(TestMod.BACKPACK_IDENTIFIER, PackScreenHandler::new);

     @Override
    public void onInitializeClient()
    {
        ScreenRegistry.register(TestMod.BAG_SCREEN_HANDLER, ScratchpackClientScreen::new);
        // ScreenProviderRegistry.INSTANCE.<ScratchpackScreenHandler>registerFactory(TestMod.BACKPACK_IDENTIFIER, (container -> new ScratchpackClientScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText("TestMod.scratchpack"))));
    }
}
