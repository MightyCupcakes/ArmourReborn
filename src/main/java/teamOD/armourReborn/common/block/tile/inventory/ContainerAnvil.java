package teamOD.armourReborn.common.block.tile.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerAnvil<T extends TileEntity & IInventory> extends ContainerMod {

	public static int sizeX = 18 ;
	public static int sizeY = 18 ;
	
	public int index = 0;
	
	public ContainerAnvil(T tile, InventoryPlayer inventoryPlayer) {
		super(tile, inventoryPlayer);

		addInventorySlots(8, 13, 4);
		addOutputSlots(75, 49, 4);
		addOutputSlots(111, 13, 1);
	}

	private void addInventorySlots (int x, int y, int num) {
		
		for (int col = 0; col < num; col ++) {
			Slot newSlot = new Slot ((IInventory) tile, index, x + col * sizeX, y) ;
				
			this.addSlotToContainer(newSlot) ;
			internalInventory.add(newSlot) ;
			
			index ++ ;
		}
	}
	
	private void addOutputSlots (int x, int y, int num) {
		
		for (int col = 0; col < num; col ++) {
			Slot newSlot = new outputSlot ((IInventory) tile, index, x + col * sizeX, y) ;
				
			this.addSlotToContainer(newSlot) ;
			internalInventory.add(newSlot) ;
			
			index ++ ;
		}
	}
	
	private class outputSlot extends Slot {

		public outputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid (ItemStack stack) {
			return false ;
		}
		
	}
}
