package teamOD.armourReborn.common.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileForgeMaster extends TileMultiBlock implements IInventory {
	
	public static final String NAME = "forgeInventory" ;
	public static final int STACKSIZE = 1 ;
	public static final int INVENTORY_SIZE = 4 ;
	
	private boolean isActive = false ;
	private ItemStack[] inventory ;
	
	public TileForgeMaster () {
		super ();
		
		inventory = new ItemStack[INVENTORY_SIZE] ;
	}

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
	
	public boolean isActive () {
		return isActive ;
	}

	// =================================================================================== |
	//                                Inventory Handlers                                   |
	// =================================================================================== |
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public int getSizeInventory() {
		return inventory.length ;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= inventory.length) return null ;
		
		return inventory[index] ;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		ItemStack stack = getStackInSlot (index) ;
		
		if (stack == null) return null ;
		
		if (stack.stackSize <= count) {
			removeStackFromSlot (index) ;
		} else {
			stack = stack.splitStack(count) ;
			
			if (getStackInSlot (index).stackSize == 0) {
				removeStackFromSlot (index) ;
			}
		}
		
		this.markDirty() ;
		return stack ;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		
		ItemStack stack = getStackInSlot (index) ;
		setInventorySlotContents (index, null) ;
		
		return stack ;		
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= inventory.length) return ;
		
		inventory[index] = stack ;
		
		if (stack != null && stack.stackSize > getInventoryStackLimit() ) {
			stack.stackSize = getInventoryStackLimit () ;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return STACKSIZE ;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(pos) != this || worldObj.getBlockState(pos).getBlock() == Blocks.air) {
			return false ;
		}
		return true;
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
		ItemStack inventory = getStackInSlot (index) ;
		
		if (index >= getSizeInventory () ) return false ;
		
		if (inventory == null || stack.stackSize + inventory.stackSize <= getInventoryStackLimit() ) {
			return true ;
		}
		
		return false ;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// NO OP	
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < getSizeInventory(); i ++) {
			removeStackFromSlot (i) ;
		}
		
	}
	
	// NBT
	@Override
	public void writeCustomNBT(NBTTagCompound cmp) {
		// write inventory contents to nbt
		IInventory inventory = this ;
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < inventory.getSizeInventory(); i ++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound itemTag = new NBTTagCompound () ;
				
				itemTag.setByte("slot", (byte) i) ;
				inventory.getStackInSlot(i).writeToNBT(itemTag) ;
				nbttaglist.appendTag(itemTag) ;
			}
		}
		
		cmp.setTag("inventory", nbttaglist) ;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		// write inventory contents to nbt
		IInventory inventory = this ;
		NBTTagList nbttaglist = cmp.getTagList("inventory" , 0) ;
		
		for (int i = 0; i < nbttaglist.tagCount(); i ++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound itemTag = nbttaglist.getCompoundTagAt(i) ;
				
				int slot = itemTag.getByte("slot") & 0xFF;
				
				if (slot > 0 && slot < inventory.getSizeInventory()) {
					inventory.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(itemTag));
				}
			}
		}
	}

}
