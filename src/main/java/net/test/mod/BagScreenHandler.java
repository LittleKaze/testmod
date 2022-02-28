package net.test.mod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;

public class BagScreenHandler extends ScreenHandler {
	private final ScreenHandlerType<?> type;

	public BagScreenHandler(int syncId, PlayerInventory inventory, ScratchpackInventory scratchpackInventory) {
		super(TestMod.BAG_SCREEN_HANDLER, syncId);
		type = TestMod.BAG_SCREEN_HANDLER;
	}

	@Override
	public ScreenHandlerType<?> getType() {
		return type;
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		if (index >= 0) { // slotId < 0 are used for networking internals
			ItemStack stack = getSlot(index).getStack();

			if (stack.getItem() instanceof ScratchpackItem) {
				// Prevent moving bags around
				return stack;
			}
		}

		return super.transferSlot(player, index);
	}

	@Override
	public boolean canUse(PlayerEntity var1) {
		// TODO Auto-generated method stub
		return false;
	}
}