package teamOD.armourReborn.common.block.tile.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import teamOD.armourReborn.common.network.PacketReborn;

public class ForgeInventoryUpdatePacket extends PacketReborn {
	
	public int slot ;
	public ItemStack stack ;
	public BlockPos pos ;
	
	public ForgeInventoryUpdatePacket () { }
	
	public ForgeInventoryUpdatePacket (BlockPos pos, ItemStack stack, int slot) {
		this.slot = slot ;
		this.pos = pos ;
		this.stack = stack ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		slot = buf.readInt() ;
		stack = ByteBufUtils.readItemStack(buf) ;
		pos = readPos (buf) ;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(slot) ;
		ByteBufUtils.writeItemStack(buf, stack) ;
		savePos (pos, buf) ;
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		TileEntity entity = Minecraft.getMinecraft().theWorld.getTileEntity(pos) ;
		
		if (entity instanceof IInventory) {
			( (IInventory) entity).setInventorySlotContents(slot, stack) ;
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		// NO OP		
	}

}
