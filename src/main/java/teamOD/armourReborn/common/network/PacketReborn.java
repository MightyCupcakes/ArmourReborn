package teamOD.armourReborn.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/*
 * Code originally from SlimeKnights
 */
public abstract class PacketReborn implements IMessage {
	
	protected void savePos (BlockPos pos, ByteBuf buf) {
		buf.writeInt(pos.getX()) ;
		buf.writeInt(pos.getY()) ;
		buf.writeInt(pos.getZ()) ;
	}
	
	protected BlockPos readPos (ByteBuf buf) {
		int x = buf.readInt() ;
		int y = buf.readInt() ;
		int z = buf.readInt() ;
		
		return new BlockPos (x, y, z) ;
	}
	
	public final IMessage handleClient (final NetHandlerPlayClient netHandler) {
		
		FMLCommonHandler.instance().getWorldThread(netHandler).addScheduledTask(new Runnable () {

			@Override
			public void run() {
				handleClientSafe (netHandler) ;
			}
			
		}) ;
		
		return null ;
	}
	
	public final IMessage handleServer (final NetHandlerPlayServer netHandler) {
		
		FMLCommonHandler.instance().getWorldThread(netHandler).addScheduledTask(new Runnable () {

			@Override
			public void run() {
				handleServerSafe (netHandler) ;
			}
			
		}) ;
		
		return null ;
	}
	
	public abstract void handleClientSafe (NetHandlerPlayClient netHandler);
	
	public abstract void handleServerSafe (NetHandlerPlayServer netHandler);

}
