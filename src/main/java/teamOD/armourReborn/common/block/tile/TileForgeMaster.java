package teamOD.armourReborn.common.block.tile;

import com.google.common.collect.Lists;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Queues ;

import java.util.ArrayDeque;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileForgeMaster extends TileMultiBlock implements IInventory {
	
	public static final String NAME = "forgeInventory" ;
	public static final int STACKSIZE = 1 ;
	public static final int INVENTORY_SIZE = 4 ;
	
	public static final int FORGE_LENGTH = 3 ;
	public static final int FORGE_WIDTH = 1 ;
	public static final int FORGE_HEIGHT = 2 ;
	public static final int TOTAL_BLOCKS = FORGE_LENGTH * FORGE_WIDTH * FORGE_HEIGHT ;
	public static final boolean IS_SQUARE = false ;
	
	private boolean isActive = false ;
	private ItemStack[] inventory ;
	private int tick, timeElapsed ;
	
	private int minX, minY, minZ ;
	private int maxX, maxY, maxZ ;
	
	public TileForgeMaster () {
		super ();
		
		inventory = new ItemStack[INVENTORY_SIZE] ;
		
		this.reset() ;
	}
	
	@Override
	public void updateEntity () { // Called every tick. 1 second <=> 20 ticks
		if(!isActive) {
			if (tick == 0) {
				checkMultiBlockForm () ;
			}
		} else {
			
			if (tick == 0) {
				if (++ timeElapsed >= 5) {
					timeElapsed = 0 ;
					checkMultiBlockForm () ;
				}
			}
		}
		
		tick = (tick + 1) % 20 ;
	}

	@Override
	public void checkMultiBlockForm() {
		
		int length, width, height ;
		boolean wasActive = isActive () ;
		
		BlockPos position = this.getPos() ;
		IBlockState state = worldObj.getBlockState(getPos()) ;
		
		EnumFacing[] directions = EnumFacing.values();
		TileEntity entity ;
		
		this.reset() ;
		
		HashMultiset <Long> visited = HashMultiset.create() ;
		ArrayDeque<BlockPos> queue = Queues.newArrayDeque() ;
		
		queue.offer(position) ;
		visited.add(position.toLong()) ;
		
		// Perform a Breadth first search to find all connected blocks.
		while ( !queue.isEmpty() ) {
			BlockPos search = queue.poll() ;
			
			for (BlockPos pos: adjacent (search)) {
				if (!visited.contains(pos.toLong())) {
					visited.add(pos.toLong()) ;
					
					queue.offer(pos) ;
					
					setMaxCoords (pos) ;
				}
			}
		}
		
		length = maxX - minX + 1 ;
		width = maxZ - minZ + 1 ;
		height = maxY - position.getY() +  1 ;
		
		if (!IS_SQUARE && length == width) {
			resetStructure () ;
			return ;
		}
		
		if (visited.size() != TOTAL_BLOCKS) {
			resetStructure () ;
			return ;
		}
		
		// To account for people putting down the forge along the x-axis or the z-axis
		if (length != FORGE_LENGTH && length != FORGE_WIDTH) {
			resetStructure () ;
			return ;
		}
		
		if (width != FORGE_LENGTH && width != FORGE_WIDTH) {
			resetStructure () ;
			return ;
		}
		
		if (height != FORGE_HEIGHT) {
			resetStructure () ;
			return ;
		}
		
		if (wasActive) {
			worldObj.notifyBlockUpdate(getPos(), state, state, 3);
			this.markDirty() ;
		}
		
		setupStructure () ;
		
	}
	
	private List <BlockPos> adjacent (BlockPos pos) {
		List <BlockPos> result = Lists.newLinkedList() ;
		
		EnumFacing[] directions = EnumFacing.values();
		
		for (int i = 1; i < directions.length; i ++) {
			TileEntity entity = worldObj.getTileEntity( pos.offset(directions[i]) ) ;
			
			if (entity != null && entity instanceof TileForgeComponent) {
				result.add(pos.offset(directions[i])) ;
			}
		}
		
		return result ;
	}
	
	private void setMaxCoords (BlockPos pos) {
		if (pos.getX() < minX) minX = pos.getX() ;
		if (pos.getZ() < minZ) minZ = pos.getZ() ;
		
		if (pos.getX() > maxX) maxX = pos.getX() ;
		if (pos.getY() > maxY) maxY = pos.getY() ;
		if (pos.getZ() > maxZ) maxZ = pos.getZ() ;
		
	}
	
	@Override
	public void reset () {
		super.reset() ;
		
		BlockPos position = this.getPos() ;
		
		minX = position.getX() ;
		minY = position.getY() ;
		minZ = position.getZ() ;
		
		maxX = position.getX() ;
		maxY = position.getY() ;
		maxZ = position.getZ() ;
	}

	@Override
	protected void resetStructure() {
		isActive = false ;
		
		for (int x = minX; x <= maxX; x ++) {
			for (int y = minY; y <= maxY ; y ++) {
				for (int z = minZ; z <= maxZ; z ++) {
					TileEntity entity = worldObj.getTileEntity(new BlockPos (x, y, z)) ;
					
					if (entity != null && entity instanceof TileForgeComponent) {					
						( (TileForgeComponent) entity).reset() ;
					}
				}
			}
		}
		
		this.reset() ;
		
	}

	@Override
	protected void setupStructure() {

		BlockPos position = this.getPos() ;
		
		isActive = true ;
		this.setIsMaster(true) ;
		this.setMasterCoords(position) ;
		
		for (int x = minX; x <= maxX; x ++) {
			for (int y = minY; y <= maxY; y ++) {
				for (int z = minZ; z <= maxZ; z ++) {
					BlockPos currentPos = new BlockPos (x, y, z) ;
					
					if (this.isMasterCoords(currentPos)) continue ;
					
					// Safe to cast it as TileForgeComponent as it was previously checked to be as such
					TileForgeComponent tile = (TileForgeComponent) worldObj.getTileEntity( currentPos );
					
					tile.setMasterCoords(position) ;
					tile.setHasMaster(true) ;
				}
			}
		}
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
