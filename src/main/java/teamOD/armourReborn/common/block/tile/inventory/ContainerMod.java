package teamOD.armourReborn.common.block.tile.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;

public class ContainerMod <T extends TileEntity & IInventory> extends Container {
	
	protected T tile ;
	protected final World world ;
	protected final BlockPos pos ;
	protected int playerInventoryEnd ;
	
	public List<Slot> internalInventory = Lists.<Slot>newArrayList() ; 
	
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
		
		playerInventoryEnd = index ;
		
		// hotbar
		index = 0 ;
		for (int col = 0; col < 9; col ++) {
			this.addSlotToContainer(new Slot (playerInven, index, x + col * 18, y + 58)) ;
			index ++ ;
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(internalInventory.contains(slot)) {
				// try to put it into the player inventory (if we have a player inventory)
				if(!this.mergeItemStack(itemstack1, 0, playerInventoryEnd, true)) {
					return null;
				}
			}
			// Slot is in the player inventory (if it exists), transfer to main inventory
			else if(!this.mergeItemStack(itemstack1, playerInventoryEnd, this.inventorySlots.size(), false)) {
				return null;
			}

			if(itemstack1.stackSize == 0) {
				slot.putStack(null);
			}
			else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
		// Fixes vanilla bug
		boolean result = mergeItemStackRefill(stack, startIndex, endIndex, useEndIndex);
		
		if(stack != null && stack.stackSize > 0) {
			result |= mergeItemStackMove(stack, startIndex, endIndex, useEndIndex);
		}
		return result;
	}

	// only refills items that are already present
	protected boolean mergeItemStackRefill(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
		if(stack.stackSize <= 0) {
			return false;
		}

		boolean flag1 = false;
		int k = startIndex;

		if(useEndIndex) {
			k = endIndex - 1;
		}

		Slot slot;
		ItemStack itemstack1;

		if(stack.isStackable()) {
			while(stack.stackSize > 0 && (!useEndIndex && k < endIndex || useEndIndex && k >= startIndex)) {
				slot = this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if(itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes()
						|| stack.getMetadata() == itemstack1
						.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {
					int l = itemstack1.stackSize + stack.stackSize;
					int limit = Math.min(stack.getMaxStackSize(), slot.getItemStackLimit(stack));

					if(l <= limit) {
						stack.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					}
					else if(itemstack1.stackSize < limit) {
						stack.stackSize -= limit - itemstack1.stackSize;
						itemstack1.stackSize = limit;
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if(useEndIndex) {
					--k;
				}
				else {
					++k;
				}
			}
		}

		return flag1;
	}

	// only moves items into empty slots
	protected boolean mergeItemStackMove(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
		if(stack.stackSize <= 0) {
			return false;
		}

		boolean flag1 = false;
		int k;

		if(useEndIndex) {
			k = endIndex - 1;
		}
		else {
			k = startIndex;
		}

		while(!useEndIndex && k < endIndex || useEndIndex && k >= startIndex) {
			Slot slot = this.inventorySlots.get(k);
			ItemStack itemstack1 = slot.getStack();

			if(itemstack1 == null && slot.isItemValid(stack)) // Forge: Make sure to respect isItemValid in the slot.
			{
				int limit = slot.getItemStackLimit(stack);
				ItemStack stack2 = stack.copy();
				if(stack2.stackSize > limit) {
					stack2.stackSize = limit;
					stack.stackSize -= limit;
				}
				else {
					stack.stackSize = 0;
				}
				slot.putStack(stack2);
				slot.onSlotChanged();
				flag1 = true;

				if(stack.stackSize == 0) {
					break;
				}
			}

			if(useEndIndex) {
				--k;
			}
			else {
				++k;
			}
		}


		return flag1;
	}
}
