package net.test.mod.scratchpack;

import java.util.List;

import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.test.mod.TestMod;

public class ScratchpackItem extends TrinketItem
{
    public ScratchpackItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        user.setCurrentHand(hand);

        if(!world.isClient)
        {
            ContainerProviderRegistry.INSTANCE.openContainer(TestMod.BACKPACK_IDENTIFIER, user, buf -> {
                ItemStack stack = user.getStackInHand(hand);
                buf.writeItemStack(stack);
                buf.writeInt(hand == Hand.MAIN_HAND ? 0 : 1);
                buf.writeString(stack.getName().asString());
            });
        }

        return super.use(world, user, hand);
    }

    public static ScratchpackInventory getInventory(ItemStack stack, PlayerEntity player)
    {
        if(!stack.hasNbt())
        {
            stack.setNbt(new NbtCompound());
        }

        if(!stack.getNbt().contains("scratchpack"))
        {
            stack.getNbt().put("scratchpack", new NbtCompound());
        }

        return new ScratchpackInventory(stack.getNbt().getCompound("scratchpack"), player);
    }

    @Override
    public boolean canBeNested()
    {
        return false;
    }

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
	{
	    tooltip.add(new TranslatableText("item.scratch_pack.backpack").formatted(Formatting.YELLOW));
	}
}