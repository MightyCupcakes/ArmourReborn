package teamOD.armourReborn.common.block.tile.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import teamOD.armourReborn.common.block.tile.TileHeatingComponent;
import teamOD.armourReborn.common.network.PacketReborn;

public class ForgeFuelUpdatePacket extends PacketReborn {
	
	public BlockPos pos ;
	public FluidStack fuel ;
	
	public ForgeFuelUpdatePacket () { }
	
	public ForgeFuelUpdatePacket (BlockPos pos, FluidStack fuel) {
		this.pos = pos ;
		this.fuel = fuel ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = readPos (buf) ;
		
		NBTTagCompound tag = ByteBufUtils.readTag(buf) ;
		fuel = FluidStack.loadFluidStackFromNBT(tag) ;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		savePos (pos, buf) ;
		
		NBTTagCompound tag = new NBTTagCompound () ;
		fuel.writeToNBT(tag) ;
		ByteBufUtils.writeTag(buf, tag) ;
		
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		TileEntity entity = Minecraft.getMinecraft().theWorld.getTileEntity(pos) ;
		
		if (entity instanceof TileHeatingComponent) {
			TileHeatingComponent heater = (TileHeatingComponent) entity ;
			heater.setFuelStack(fuel) ;
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		// NO-OP		
	}

}
