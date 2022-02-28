package net.test.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.test.mod.scratchpack.PackScreenHandler;
import net.test.mod.scratchpack.ScratchpackClientScreen;
import net.test.mod.scratchpack.ScratchpackScreenHandler;

@Environment(EnvType.CLIENT)
public class ScratchpackClient implements ClientModInitializer
{
    public static final ScreenHandlerType<PackScreenHandler> SCRATCH_PACK = ScreenHandlerRegistry.registerExtended(id("scratchpack"), (syncId, inventory, buf) -> new PackScreenHandler(syncId, inventory, buf.readBlockPos(), buf.readBoolean()));

     @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.<ScratchpackScreenHandler>registerFactory(TestMod.BACKPACK_IDENTIFIER, (container -> new ScratchpackClientScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText(TestMod.BACKPACK_TRANSLATION_KEY))));
        // ScreenProviderRegistry.INSTANCE.<ScratchpackScreenHandler>registerFactory(TestMod.BACKPACK_IDENTIFIER, (container -> new ScratchpackClientScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText("TestMod.scratchpack"))));
    }

    private static Identifier id(String path) {
        return null;
    }
}
