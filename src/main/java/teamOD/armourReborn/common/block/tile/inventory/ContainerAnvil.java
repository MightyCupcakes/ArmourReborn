package teamOD.armourReborn.common.block.tile.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import teamOD.armourReborn.common.item.ItemCopperCast;

public class ContainerAnvil<T extends TileEntity & IInventory> extends ContainerMod {

	public static int sizeX = 18 ;
	public static int sizeY = 18 ;
	
	public int index = 0;
	public int outputSlot ;
	
	public ContainerAnvil(T tile, InventoryPlayer inventoryPlayer) {
		super(tile, inventoryPlayer);

		addInventorySlots(8, 13, 4);
		addInventorySlots(111, 49, 1);
		addCastSlots (87,49, 1) ;
		
		outputSlot = index ;
		
		addOutputSlots(8, 49, 4);
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
	
	private void addCastSlots (int x, int y, int num) {
		for (int col = 0; col < num; col ++) {
			Slot newSlot = new CastSlot ((IInventory) tile, index, x + col * sizeX, y) ;
				
			this.addSlotToContainer(newSlot) ;
			internalInventory.add(newSlot) ;
			
			index ++ ;
		}
	}
	
	private void addOutputSlots (int x, int y, int num) {
		
		for (int col = 0; col < num; col ++) {
			Slot newSlot = new OutputSlot ((IInventory) tile, index, x + col * sizeX, y) ;
				
			this.addSlotToContainer(newSlot) ;
			internalInventory.add(newSlot) ;
			
			index ++ ;
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		
		if (index > outputSlot) return itemstack ;
		
		return super.transferStackInSlot(playerIn, index) ;
	}
	
	private class OutputSlot extends Slot {

		public OutputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid (ItemStack stack) {
			return false ;
		}
		
	}
	
	private class CastSlot extends Slot {

		public CastSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid (ItemStack stack) {
			return stack.getItem() instanceof ItemCopperCast ;
		}
		
	}
	
}
