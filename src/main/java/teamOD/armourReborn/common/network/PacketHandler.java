package teamOD.armourReborn.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import teamOD.armourReborn.common.lib.LibMisc;

public class PacketHandler {
	public static PacketHandler INSTANCE = new PacketHandler () ;
	
	public final SimpleNetworkWrapper network ;
	public final MessageHandler handler ;
	private int id = 0 ;
	
	public PacketHandler () {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_ID) ;
		handler = new MessageHandler () ;
	}
	
	public static class MessageHandler implements IMessageHandler <PacketReborn, IMessage> {

		@Override
		public IMessage onMessage(PacketReborn message, MessageContext ctx) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
