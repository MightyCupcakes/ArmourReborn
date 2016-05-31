package teamOD.armourReborn.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class PacketReborn implements IMessage {
	
	protected void savePos (BlockPos pos, ByteBuf buf) {
		buf.writeInt(pos.getX()) ;
		buf.writeInt(pos.getY()) ;
		buf.writeInt(pos.getZ()) ;
	}

}
