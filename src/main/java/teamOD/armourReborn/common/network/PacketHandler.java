package teamOD.armourReborn.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import teamOD.armourReborn.common.block.tile.TileMod;
import teamOD.armourReborn.common.block.tile.network.ForgeFluidUpdatePacket;
import teamOD.armourReborn.common.block.tile.network.ForgeFuelUpdatePacket;
import teamOD.armourReborn.common.block.tile.network.ForgeInventoryUpdatePacket;
import teamOD.armourReborn.common.lib.LibMisc;

public class PacketHandler {
	public static PacketHandler INSTANCE = new PacketHandler () ;
	
	public final SimpleNetworkWrapper network ;
	public final MessageHandlerReborn handler ;
	private int id = 0 ;
	
	public static void init () {
		INSTANCE.registerClient (ForgeFluidUpdatePacket.class) ;
		INSTANCE.registerClient(ForgeInventoryUpdatePacket.class) ;
		INSTANCE.registerClient(ForgeFuelUpdatePacket.class) ;
	}
	
	public static void sendToAll (PacketReborn packet) {
		INSTANCE.network.sendToAll(packet) ;
	}
	
	public static void sendToPlayers (WorldServer world, BlockPos pos, PacketReborn packet) {
		Chunk chunk = world.getChunkFromBlockCoords(pos) ;
		
		for (EntityPlayer player: world.playerEntities) {
			
			if (!(player instanceof EntityPlayerMP)) {
				continue ;
			}
			
			EntityPlayerMP playerMP = (EntityPlayerMP) player ;
			
			if (world.getPlayerChunkManager().isPlayerWatchingChunk(playerMP, chunk.xPosition, chunk.zPosition)) {
				INSTANCE.network.sendTo(packet, playerMP) ;
			}
		}
	}
	
	
	public PacketHandler () {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_ID) ;
		handler = new MessageHandlerReborn () ;
	}
	
	public void registerPacket (Class<? extends PacketReborn> packet) {
		registerClient (packet) ;
		registerServer (packet) ;
	}
	
	public void registerClient (Class<? extends PacketReborn> packet) {
		registerPacketNetwork (packet, Side.CLIENT) ;
	}
	
	public void registerServer (Class<? extends PacketReborn> packet) {
		registerPacketNetwork (packet, Side.SERVER) ;
	}
	
	private void registerPacketNetwork (Class<? extends PacketReborn> packet, Side side) {
		network.registerMessage(handler, packet, id++, side);
	}
	
	public static class MessageHandlerReborn implements IMessageHandler <PacketReborn, IMessage> {

		@Override
		public IMessage onMessage(PacketReborn message, MessageContext ctx) {
			if (ctx.side == Side.SERVER) {
				return message.handleServer(ctx.getServerHandler()) ;
			} else {
				return message.handleClient(ctx.getClientHandler()) ;
			}
		}
		
	}

}
