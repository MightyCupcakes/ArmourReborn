package teamOD.armourReborn.common.block.tile.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerForge<T extends TileEntity & IInventory> extends ContainerMod {
	
	public static int sizeX = 23 ;
	public static int sizeY = 18 ;

	public ContainerForge(T tile, InventoryPlayer inventoryPlayer) {
		super(tile, inventoryPlayer);
		
		addInventorySlots (26, 13) ;
	}
	
	private void addInventorySlots (int x, int y) {
		int index = 0 ;
		
		for (int rows = 0; rows < 3; rows ++) {
			for (int col = 0; col < 3; col ++) {
				Slot newSlot = new Slot ((IInventory) tile, index, x + col * sizeX, y + rows * sizeY) ;
				
				this.addSlotToContainer(newSlot) ;
				internalInventory.add(newSlot) ;
				
				index ++ ;
			}
		}
	}
}
