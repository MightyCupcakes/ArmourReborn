package teamOD.armourReborn.common.block.tile;

import java.util.Stack;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import teamOD.armourReborn.common.network.PacketHandler;

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
	public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound()) ;
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
			tag.setTag("item", tag) ;
		}
	}
	
	public void sync () {
		return ;
	}
}
