package teamOD.armourReborn.common.block.tile;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues ;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import teamOD.armourReborn.client.core.gui.ForgeGui;
import teamOD.armourReborn.common.block.BlockForgeMaster;
import teamOD.armourReborn.common.block.tile.inventory.ContainerForge;
import teamOD.armourReborn.common.block.tile.inventory.ContainerMod;
import teamOD.armourReborn.common.block.tile.inventory.ITileInventory;
import teamOD.armourReborn.common.block.tile.inventory.InternalForgeTank;
import teamOD.armourReborn.common.block.tile.network.ForgeInventoryUpdatePacket;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.network.PacketHandler;

public class TileForgeMaster extends TileHeatingComponent implements IInventory, ITileInventory {
	
	public static final int STACKSIZE = 1 ;
	public static final int INVENTORY_SIZE = 9 ;
	
	public static final int FORGE_LENGTH = 3 ;
	public static final int FORGE_WIDTH = 2 ;
	public static final int FORGE_HEIGHT = 2 ;
	public static final int TOTAL_BLOCKS = FORGE_LENGTH * FORGE_WIDTH * FORGE_HEIGHT ;
	public static final boolean IS_SQUARE = false ;
	
	private boolean isActive ;
	private ItemStack[] inventory ;
	private InternalForgeTank internalTank ;
	private HashMap<Fluid, BlockPos> anvilPos ;
	private int tick, timeElapsed ;
	
	private int minX, minY, minZ ;
	private int maxX, maxY, maxZ ;
	
	private InvWrapper itemHandler ;
	
	public TileForgeMaster () {
		super (INVENTORY_SIZE);
		
		isActive = false ;
		
		internalTank = new InternalForgeTank (this) ;
		inventory = new ItemStack[INVENTORY_SIZE] ;
		anvilPos = new HashMap<Fluid, BlockPos> () ;
		itemHandler = new InvWrapper (this) ;
		
	}
	
	@Override
	public void updateEntity () { // Called every tick. 1 second <=> 20 ticks
		if(!isActive || heaterTankPos == null) {
			if (tick == 0) {
				checkMultiBlockForm () ;
			}
		} else {
			
			if (tick == 0) {
				checkMultiBlockForm () ;
			}
			
			heatItems(internalTank) ;
			createAlloys(internalTank) ;
			outputToAnvil() ;
		}
		
		tick = (++tick == 20) ? 0 : tick ;
	}

	@Override
	public void checkMultiBlockForm() {
		
		int length, width, height ;
		boolean wasActive = isActive () ;
		
		this.reset () ;
		
		BlockPos position = this.getPos() ;
		IBlockState state = worldObj.getBlockState(getPos()) ;
		
		HashSet <Long> visited = new HashSet <Long>( (int) (TOTAL_BLOCKS/0.75) + 1) ;
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
		
		// TODO: Clean up this section...
		if (!IS_SQUARE && length == width) {
			resetStructure () ;
		}
		
		else if (visited.size() != TOTAL_BLOCKS) {
			resetStructure () ;
		}
		
		// To account for people putting down the forge along the x-axis or the z-axis
		else if (length != FORGE_LENGTH && length != FORGE_WIDTH) {
			resetStructure () ;
		}
		
		else if (width != FORGE_LENGTH && width != FORGE_WIDTH) {
			resetStructure () ;
		}
		
		else if (height != FORGE_HEIGHT) {
			resetStructure () ;
		}
		
		else if ( !(worldObj.getTileEntity( position.offset( state.getValue( BlockForgeMaster.FACING).getOpposite() )) instanceof TileForgeTank) ) {
			heaterTankPos = null ;
			
			resetStructure () ;
		} 
		
		else {
			setupStructure () ;
			findAdjacentAnvil () ;
		}
		
		worldObj.notifyBlockUpdate(getPos(), state, state, 3);
		worldObj.notifyNeighborsOfStateChange(getPos(), this.blockType);
		this.markDirty() ;
		
	}
	
	private List <BlockPos> adjacent (BlockPos pos) {
		List <BlockPos> result = Lists.newLinkedList() ;
		
		EnumFacing[] directions = EnumFacing.values();
		
		// Will not check downwards
		for (int i = 1; i < directions.length; i ++) {
			TileEntity entity = worldObj.getTileEntity( pos.offset(directions[i]) ) ;
			
			if (entity != null && entity instanceof TileForgeComponent) {
				result.add(pos.offset(directions[i])) ;
			}
		}
		
		return result ;
	}
	
	private void findAdjacentAnvil () {
		
		anvilPos.clear() ;
		
		for (int x = minX; x <= maxX; x ++) {
			for (int z = minZ; z <= maxZ; z ++) {
				BlockPos currentPos = new BlockPos (x, minY, z) ;
				
				for (EnumFacing directions: EnumFacing.HORIZONTALS) {
					TileEntity entity = worldObj.getTileEntity(currentPos.offset(directions)) ;
					
					if (entity instanceof TileForgeAnvil) {
						TileForgeAnvil a = (TileForgeAnvil) entity ;
						
						if (a.getTankInfo().fluid != null) {
							anvilPos.put(a.getTankInfo().fluid.getFluid(), currentPos.offset(directions)) ;
						} else {
							anvilPos.put(FluidRegistry.WATER, currentPos.offset(directions)) ;
						}
					}
				}
			}
		}
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
	public void resetStructure() {
		
		isActive = false ;
		
		for (int x = minX; x <= maxX; x ++) {
			for (int y = minY; y <= maxY ; y ++) {
				for (int z = minZ; z <= maxZ; z ++) {
					BlockPos currentPos = new BlockPos (x, y, z) ;
					TileEntity entity = worldObj.getTileEntity(currentPos) ;
					
					if (entity != null && entity instanceof TileForgeComponent) {					
						( (TileForgeComponent) entity).reset() ;
						
						worldObj.notifyBlockUpdate(currentPos, worldObj.getBlockState(currentPos), worldObj.getBlockState(currentPos), 3);
						entity.markDirty() ;
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
					
					if (tile instanceof TileForgeTank) setHeaterPos (currentPos) ;
					
					tile.setMasterCoords(position) ;
					worldObj.notifyBlockUpdate(currentPos, worldObj.getBlockState(currentPos), worldObj.getBlockState(currentPos), 3);
					worldObj.notifyNeighborsOfStateChange(getPos(), worldObj.getBlockState(currentPos).getBlock());
					tile.markDirty() ;
				}
			}
		}
	}
	
	public boolean isActive () {
		return isActive ;
	}
	
	public void outputToAnvil () {
		// If forge is currently melting stuff down, or forming alloys, don't output its contents.
		// or there is no anvil to output to
		if (forgeBusy || anvilPos.size() == 0) {
			return ;
		}
		
		for (int i = 0; i < internalTank.getFluids().size(); i ++) {
			
			FluidStack fluid = internalTank.getFluids().get(i) ;
			
			// Try to find an anvil with the current fluid and fill it first
			// Else get any empty anvil.
			BlockPos aPos = (anvilPos.containsKey(fluid.getFluid())) ? anvilPos.get(fluid.getFluid()) : anvilPos.get(FluidRegistry.WATER) ;
			
			// No empty anvil available and no anvil filled with the current liquid
			if (aPos == null) {
				return ;
			}
			
			TileForgeAnvil anvil = null ;
			TileEntity a = worldObj.getTileEntity(aPos) ;
			
			// Makes sure that the tile has not changed during the 1 second window
			if ( a instanceof TileForgeAnvil ) {
				anvil = (TileForgeAnvil) a ;
			}
			
			if (anvil == null) continue ;
			
			if ( !anvil.canFill(null, fluid.getFluid()) ) continue ;
			
			int amt = anvil.fill(null, fluid, false) ;
			
			if (amt == fluid.amount) {
				// Anvil can accept all of this liquid.
				// Fully drain the internal tank of this fluid
				anvil.fill(null, fluid, true) ;
				internalTank.drain(fluid, true) ;
			
			} else if (amt > 0 && amt != fluid.amount) {
				// Anvil don't have sufficient space to fully drain the internal tank
				FluidStack tmpFluid = fluid.copy() ;
				tmpFluid.amount = amt ;
				
				anvil.fill(null, tmpFluid, true) ;
				internalTank.drain(tmpFluid, true) ;
			}
		}
		
	}
	
	@Override
	public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity packet) {
		boolean wasActive = this.isActive() ;
		
		super.onDataPacket(net, packet) ;
		
		if (isActive != wasActive) {
			IBlockState state = worldObj.getBlockState(getPos()) ;
			worldObj.notifyBlockUpdate(getPos(), state, state, 3);
		}
	}
	
	@Override
	public ContainerMod createContainer(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
		return new ContainerForge <TileForgeMaster> (this, inventoryPlayer) ;
	}

	@Override
	public GuiContainer createGui(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
		return new ForgeGui (createContainer(inventoryPlayer, world, pos), this) ;
	}
	
	public List<FluidStack> getInternalTank () {
		return internalTank.getFluids() ;
	}
	
	public FluidTankInfo getHeater () {
		return (getTankAt(heaterTankPos) != null) ? getTankAt(heaterTankPos).getTankInfo() : null ;
	}

	// =================================================================================== |
	//                                Inventory Handlers                                   |
	// =================================================================================== |
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nonnull
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) itemHandler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public String getName() {
		return "Forge Furnance";
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
		
		if (worldObj != null && !ItemStack.areItemStacksEqual(stack, getStackInSlot(index)) && !worldObj.isRemote && worldObj instanceof WorldServer) {
			PacketHandler.sendToPlayers( (WorldServer) worldObj, getPos(), new ForgeInventoryUpdatePacket (getPos(), stack, index));
		}
		
		inventory[index] = stack ;
		updateItemHeatReq(index, stack) ;
		
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
		if(worldObj.getTileEntity(pos) != this || worldObj.getBlockState(pos).getBlock() == Blocks.AIR) {
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
		super.writeCustomNBT(cmp) ;
		
		cmp.setBoolean("Active", isActive() ) ;
		writeAnvilPos (cmp) ;
		
		// write inventory contents to nbt
		IInventory inventory = this ;
		NBTTagList nbttaglist = new NBTTagList();
		
		internalTank.writeToNBT(cmp) ;
		
		for (int i = 0; i < inventory.getSizeInventory(); i ++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound itemTag = new NBTTagCompound () ;
				
				itemTag.setByte("slot", (byte) i) ;
				inventory.getStackInSlot(i).writeToNBT(itemTag) ;
				nbttaglist.appendTag(itemTag) ;
			}
		}
		
		cmp.setTag("Inventory", nbttaglist) ;
	}
	
	private void writeAnvilPos(NBTTagCompound cmp) {
		NBTTagList nbttaglist = new NBTTagList() ;
		
		for (BlockPos bp : anvilPos.values()) {
			nbttaglist.appendTag(LibUtil.writePos(bp)) ;
		}
		
		cmp.setTag("anvilPos", nbttaglist);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		super.readCustomNBT(cmp) ;
		
		readAnvilPos(cmp) ;
		
		// write inventory contents to nbt
		IInventory inventory = this ;
		NBTTagList nbttaglist = cmp.getTagList("Inventory" , 10) ;
		
		isActive = cmp.getBoolean("Active") ;
		internalTank.readFromNBT(cmp) ;
		
		for (int i = 0; i < nbttaglist.tagCount(); i ++) {
			NBTTagCompound itemTag = nbttaglist.getCompoundTagAt(i) ;
			
			int slot = itemTag.getByte("slot") & 255 ;
			
			if (slot >= 0 && slot < inventory.getSizeInventory()) {
				inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
			}
		}
	}
	
	private void readAnvilPos(NBTTagCompound cmp) {
		NBTTagList tagList = cmp.getTagList("anvilPos", 10);
		
		anvilPos.clear();
		
		for (int i = 0; i < tagList.tagCount(); i++) {
			BlockPos p = LibUtil.readPos(tagList.getCompoundTagAt(i)) ;
			
			if (worldObj != null) {
				TileForgeAnvil anvil = (TileForgeAnvil) worldObj.getTileEntity(p) ;
				if (anvil != null) {
					FluidStack fluid = anvil.getTankInfo().fluid ;
					
					if (fluid != null) {
						anvilPos.put(fluid.getFluid(), p) ;
					} else {
						anvilPos.put(FluidRegistry.WATER, p) ;
					}
				}
			}
		}
	}
	
	public void updateFluidsFromPacket (List<FluidStack> liquids) {
		internalTank.updateFluidsFromPacket(liquids) ;
	}
	
	@Override
	public void validate() {
		super.validate() ;
		
		isActive = false ;
	}

}
