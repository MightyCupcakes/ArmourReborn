package teamOD.armourReborn.common.block.tile.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerMod <T extends TileEntity & IInventory> extends Container {
	
	protected T tile ;
	protected final World world ;
	protected final BlockPos pos ;
	
	public ContainerMod (T tile) {
		this.tile = tile ;
		
		this.world = tile.getWorld() ;
		this.pos = tile.getPos() ;
	}
	
	public T getTile () {
		return tile ;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return false;
	}

}
