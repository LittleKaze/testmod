package net.test.mod.scratchpack;

import java.util.List;
import java.util.Optional;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.test.mod.TestMod;

public class ScratchpackInventory implements Inventory, ScratchpackInventoryInterface
{
    public DefaultedList<ItemStack> items;
    public int inventory_width;
    public int inventory_height;
    
    public ScratchpackInventory(NbtCompound items_tag, PlayerEntity player)
    {
        this.fromTag(items_tag);
    }

    @Override
    public void clear()
    {
        this.items.clear();
    }

    @Override
    public int getInventoryWidth()
    {
        return inventory_width;
    }

    @Override
    public int getInventoryHeight()
    {
        return inventory_height;
    }

    @Override
    public int size()
    {
        return getInventoryWidth() * getInventoryHeight();
    }

    @Override
    public boolean isEmpty()
    {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot)
    {
        return items.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount)
    {
        return Inventories.splitStack(this.items, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot)
    {
        return Inventories.removeStack(this.items, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack)
    {
        this.items.set(slot, stack);
    }

    @Override
    public void markDirty()
    {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player)
    {
        return true;
    }

    public void fromTag(NbtCompound tag)
    {
        this.inventory_width = tag.contains("inventory_width") ? tag.getInt("inventory_width") : 9;
        this.inventory_height = tag.contains("inventory_height") ? tag.getInt("inventory_height") : 2;

        this.items = DefaultedList.ofSize(inventory_width * inventory_height, ItemStack.EMPTY);
        readItemsFromTag(this.items, tag);
    }

    public NbtCompound toTag()
    {
        NbtCompound tag = new NbtCompound();
        tag.putInt("inventory_width", inventory_width);
        tag.putInt("inventory_height", inventory_height);

        writeItemsToTag(this.items, tag);

        return tag;
    }

    @Override
    public void onOpen(PlayerEntity player)
    {
        Inventory.super.onOpen(player);
        player.playSound(SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1f, 1f);
    }

    @Override
    public void onClose(PlayerEntity player)
    {
        Inventory.super.onClose(player);

        Optional<TrinketComponent> trinketcomponent = TrinketsApi.getTrinketComponent(player);

            List<Pair<SlotReference, ItemStack>> pairs = trinketcomponent.get().getEquipped(TestMod.SCRATCH_PACK);
            ItemStack pack = pairs.get(0).getRight();

        if(!pack.hasNbt())
        {
            pack.setNbt(new NbtCompound());
        }

        pack.getNbt().put("backpack", toTag());
        player.playSound(SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1f, 1f);
    }
}