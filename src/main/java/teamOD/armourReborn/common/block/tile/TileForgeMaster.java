package teamOD.armourReborn.common.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class TileForgeMaster extends TileMultiBlock implements IInventory {
	
	private boolean isActive = false ;

	@Override
	public void checkMultiBlockForm() {
		IBlockState state = this.worldObj.getBlockState(getPos());
		
		BlockPos position = this.getPos() ;
		
		for (int x = position.getX() - 1; x <= position.getX() + 1; x ++) {
			for (int y = position.getY(); y <= position.getY() + 1; y ++) {
				TileEntity entity = worldObj.getTileEntity(new BlockPos (x, y, position.getZ())) ;
				
				if (entity != null && entity instanceof TileForgeComponent) {					
					if ( ( (TileForgeComponent) entity).isMaster() && !( (TileForgeComponent) entity).isMasterCoords (entity.getPos())) {
						resetStructure () ;
						return ;
					}
				} else {
					resetStructure () ;
					return ;
				}
			}
		}
		
		setupStructure () ;
		
	}

	@Override
	public void resetStructure() {
		isActive = false ;
		
		for (int x = getMasterCoords ('x') - 1; x <= getMasterCoords ('x') + 1; x ++) {
			for (int y = getMasterCoords ('y'); y <= getMasterCoords ('y') + 1; y ++) {
				TileEntity entity = worldObj.getTileEntity(new BlockPos (x, y, getMasterCoords ('z'))) ;
				
				if (entity != null && entity instanceof TileForgeComponent) {					
					( (TileForgeComponent) entity).reset() ;
				}
			}
		}
		
	}

	@Override
	public void setupStructure() {
		BlockPos position = this.getPos() ;
		
		isActive = true ;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
