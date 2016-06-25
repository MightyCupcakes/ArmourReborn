package teamOD.armourReborn.common.block.tile.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITileInventory {
	public ContainerMod createContainer (InventoryPlayer inventoryPlayer, World world, BlockPos pos) ;
	
	public GuiContainer createGui (InventoryPlayer inventoryPlayer, World world, BlockPos pos) ;
}
