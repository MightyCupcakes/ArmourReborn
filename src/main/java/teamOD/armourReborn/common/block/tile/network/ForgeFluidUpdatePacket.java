package teamOD.armourReborn.common.block.tile.network;

import java.util.List;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;
import teamOD.armourReborn.common.network.PacketReborn;

public class ForgeFluidUpdatePacket extends PacketReborn {
	public BlockPos pos ;
	public List<FluidStack> liquids ;
	
	public ForgeFluidUpdatePacket () { }
	
	public ForgeFluidUpdatePacket (BlockPos pos, List<FluidStack> fluids) {
		this.pos = pos ;
		this.liquids = fluids ;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = this.readPos (buf) ;
		
		int length = buf.readInt() ;
		
		liquids = Lists.newArrayList() ;
		
		for (int i = 0; i < length; i ++) {
			NBTTagCompound tag = ByteBufUtils.readTag(buf) ;
			FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag) ;
			
			liquids.add(fluid) ;
		}
		
	}
	@Override
	public void toBytes(ByteBuf buf) {
		this.savePos(pos, buf) ;
		
		buf.writeInt(liquids.size()) ;
		
		for (FluidStack liquid: liquids) {
			NBTTagCompound tag = new NBTTagCompound () ;
			liquid.writeToNBT(tag) ;
			ByteBufUtils.writeTag(buf, tag) ;
		}
		
	}
	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		TileEntity entity = Minecraft.getMinecraft().theWorld.getTileEntity(pos) ;
		
		if (entity instanceof TileForgeMaster) {
			TileForgeMaster master = (TileForgeMaster) entity ;
			master.updateFluidsFromPacket(liquids) ;
		}
	}
	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		// NO-OP
	}
	
	
}
