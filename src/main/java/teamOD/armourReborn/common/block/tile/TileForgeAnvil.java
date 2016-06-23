package teamOD.armourReborn.common.block.tile;

import java.util.Stack;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;

public class TileForgeAnvil extends TileMod {
	
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
}
