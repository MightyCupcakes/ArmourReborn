package teamOD.armourReborn.common.block.tile;

import java.util.Stack;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import teamOD.armourReborn.common.block.tile.inventory.ITileInventory;

public class TileForgeAnvil extends TileMod implements IInventory, ITileInventory {
	
	private ItemStack itemInventory ;
	
	public ItemStack getStack() {
		return itemInventory ;
	}
	
	public void setStack (ItemStack stack) {
		itemInventory = stack ;
		
		this.markDirty() ;
		
		IBlockState state = worldObj.getBlockState(getPos()) ;
		worldObj.notifyBlockUpdate(getPos(), state, state, 3) ;
	}
	
	@Override
	public void readCustomNBT (NBTTagCompound cmp) {
		if (cmp.hasKey("item")) {
			itemInventory = ItemStack.loadItemStackFromNBT(cmp.getCompoundTag("item")) ;
		} else {
			itemInventory = null ;
		}
	}
	
	@Override
	public void writeCustomNBT (NBTTagCompound cmp) {
		if (itemInventory != null) {
			NBTTagCompound tag = new NBTTagCompound() ;
			itemInventory.writeToNBT(tag) ;
			cmp.setTag("item", tag) ;
		}
	}
	
	public void sync () {
		return ;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container createContainer(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiContainer createGui(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
