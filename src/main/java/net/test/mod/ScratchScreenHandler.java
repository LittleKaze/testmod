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
import net.test.mod.scratchpack.ScratchpackItem;

public class ScratchScreenHandler extends Generic3x3ContainerScreenHandler {
	private final ScreenHandlerType<?> type;

	public ScratchScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(9));
	}

	public ScratchScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		this(ScratchpackClient.SCRATCH_PACK, syncId, playerInventory, inventory);
	}

	protected ScratchScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(syncId, playerInventory, inventory);
		this.type = type;
	}

	@Override
	public ScreenHandlerType<?> getType() {
		return type;
	}

	@Override
	public void onSlotClick(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity) {
		if (slotId >= 0) { // slotId < 0 are used for networking internals
			ItemStack stack = getSlot(slotId).getStack();

			if (stack.getItem() instanceof ScratchpackItem) {
				// Prevent moving bags around
				return;
			}
		}

		return;
	}
}