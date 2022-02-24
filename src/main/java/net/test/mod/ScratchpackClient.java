package net.test.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class ScratchpackClient implements ClientModInitializer
{
    public static final ScreenHandlerType<ScratchpackScreenHandler> OVEN = ScreenHandlerRegistry.registerSimple(TestMod.BACKPACK_IDENTIFIER, PackScreenHandler::new);

     @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.<ScratchpackScreenHandler>registerFactory(TestMod.BACKPACK_IDENTIFIER, (container -> new ScratchpackClientScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText("TestMod.scratchpack"))));
    }
}
