package teamOD.armourReborn.common.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;
import teamOD.armourReborn.common.block.tile.inventory.ITileInventory;
import teamOD.armourReborn.common.lib.LibMisc;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if (ID == 0) {
			TileEntity entity = world.getTileEntity(new BlockPos (x,y,z)) ;
			
			if (entity instanceof ITileInventory) {
				return ( (ITileInventory) entity).createContainer(player.inventory, world, new BlockPos (x,y,z)) ;
			}
		}
			
		return null ;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if (ID == 0) {
			TileEntity entity = world.getTileEntity(new BlockPos (x,y,z)) ;
			
			if (entity instanceof ITileInventory) {
				return ( (ITileInventory) entity).createGui(player.inventory, world, new BlockPos (x,y,z)) ;
			}
		}
		
		else if (ID == LibMisc.BOOK_GUI_ID) {
			return GuiDocumentation.currentBook ;
		}
		
		return null ;
	}

}
