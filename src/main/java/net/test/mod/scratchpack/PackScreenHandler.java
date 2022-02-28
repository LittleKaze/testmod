package net.test.mod.scratchpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.test.mod.ScratchpackClient;

public class PackScreenHandler extends ScreenHandler {
    
    public PackScreenHandler(int syncId, PlayerInventory inventory, BlockPos blockPos, boolean b) {
        super (ScratchpackClient.SCRATCH_PACK, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity var1) {
        // TODO Auto-generated method stub
        return false;
    }
}
