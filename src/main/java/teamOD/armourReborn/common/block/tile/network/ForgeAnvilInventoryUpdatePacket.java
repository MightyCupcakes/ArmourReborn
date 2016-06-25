package teamOD.armourReborn.common.block.tile.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import teamOD.armourReborn.common.block.tile.TileForgeAnvil;
import teamOD.armourReborn.common.block.tile.TileHeatingComponent;
import teamOD.armourReborn.common.network.PacketReborn;

public class ForgeAnvilInventoryUpdatePacket extends PacketReborn {

	public int slot;
	public ItemStack itemStack;
	public BlockPos pos;
	public FluidStack fluidStack;
	
	public ForgeAnvilInventoryUpdatePacket () { }
	
	public ForgeAnvilInventoryUpdatePacket (BlockPos pos, ItemStack itemStack, int slot, FluidStack fluidStack) {
		this.slot = slot ;
		this.pos = pos ;
		this.itemStack = itemStack ;
		this.fluidStack = fluidStack;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		slot = buf.readInt() ;
		itemStack = ByteBufUtils.readItemStack(buf) ;
		pos = readPos (buf) ;

		NBTTagCompound tag = ByteBufUtils.readTag(buf) ;
		fluidStack = FluidStack.loadFluidStackFromNBT(tag) ;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(slot) ;
		ByteBufUtils.writeItemStack(buf, itemStack) ;
		savePos (pos, buf) ;
		
		NBTTagCompound tag = new NBTTagCompound () ;
		if (fluidStack != null) fluidStack.writeToNBT(tag) ;
		ByteBufUtils.writeTag(buf, tag) ;
		
		
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		TileEntity entity = Minecraft.getMinecraft().theWorld.getTileEntity(pos) ;
		
		if (entity instanceof TileForgeAnvil) {
			TileForgeAnvil anvil = (TileForgeAnvil) entity ;
			anvil.setFluidInventory(fluidStack) ;
			anvil.setInventorySlotContents(slot, itemStack, true);
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		// NO OP
		
	}

}
