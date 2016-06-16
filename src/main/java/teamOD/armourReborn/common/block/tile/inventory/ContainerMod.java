package teamOD.armourReborn.common.block.tile.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;

public class ContainerMod <T extends TileEntity & IInventory> extends Container {
	
	protected T tile ;
	protected final World world ;
	protected final BlockPos pos ;
	
	public ContainerMod (T tile, InventoryPlayer inventoryPlayer) {
		this.tile = tile ;
		
		this.world = tile.getWorld() ;
		this.pos = tile.getPos() ;
		
		addPlayerInventory (inventoryPlayer, 8, 84) ;
	}
	
	public T getTile () {
		return tile ;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	protected void addPlayerInventory (InventoryPlayer playerInven, int x, int y) {
		int index = 9 ;
		
		// Inventory
		for (int row = 0; row < 3; row ++) {
			for (int col = 0; col < 9; col ++) {
				this.addSlotToContainer(new Slot (playerInven, index, x + col * 18, y + row * 18)) ;
				index ++ ;
			}
		}
		
		// hotbar
		index = 0 ;
		for (int col = 0; col < 9; col ++) {
			this.addSlotToContainer(new Slot (playerInven, index, x + col * 18, y + 58)) ;
			index ++ ;
		}
	}
}
