package teamOD.armourReborn.common.block.tile;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import teamOD.armourReborn.client.core.gui.ForgeAnvilGui;
import teamOD.armourReborn.common.block.tile.inventory.ContainerAnvil;
import teamOD.armourReborn.common.block.tile.inventory.ContainerMod;
import teamOD.armourReborn.common.block.tile.inventory.ITileInventory;
import teamOD.armourReborn.common.block.tile.network.ForgeAnvilInventoryUpdatePacket;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.fluids.FluidMod;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.item.equipment.ItemChainArmour;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;
import teamOD.armourReborn.common.item.equipment.ItemPlateArmour;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.modifiers.IModifier;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;
import teamOD.armourReborn.common.network.PacketHandler;

public class TileForgeAnvil extends TileMod implements IInventory, ITileInventory, IFluidHandler {
	
	public static final ImmutableList<FluidMod> WHITELIST_LIQUIDS = ImmutableList.copyOf(ModFluids.modFluids) ;
	
	private ItemStack[] inputInventory ;
	private ItemStack[] outputInventory;
	private FluidTank fluidInventory;
	private InvWrapper itemHandler ;
	
	private final int repairSlot, castSlot ;
	private boolean repairSlotContentsChanged, inputInventorySlotChanged ;
	private ItemStack repairSlotOriginal ;
	private FluidStack lockedFluid ;
	
	private boolean lastRedstone = false ;
	
	/** Items to deduct from inputinventory when modifiers are added to armours */
	private Multimap<Integer, ItemStack> modifiersCosts ;
	
	public TileForgeAnvil() {
		inputInventory = new ItemStack[6];
		outputInventory = new ItemStack[5];
		fluidInventory = new FluidTank(4000);
		
		repairSlot = 4 ;
		castSlot = 5 ;
		repairSlotContentsChanged = true ;
		inputInventorySlotChanged = true ;
		
		repairSlotOriginal = null ;
		lockedFluid = null ;
		
		modifiersCosts = HashMultimap.<Integer, ItemStack>create();
	}
	
	@Override
	protected void updateEntity() {
		
		// Redstone
		boolean redstone = false ;
		
		for (EnumFacing directions : EnumFacing.VALUES) {
			redstone = worldObj.getRedstonePower(getPos().offset(directions), directions) > 0;
			
			if (redstone) {
				break ;
			}
		}
		
		handleRedstoneSignal (redstone) ;
		
		// Repair slot stuff
		ItemStack stack = this.getStackInSlot(repairSlot) ;

		if (stack != null) {

			// Only do calculations when slots changed to save on resources
			if ( inputInventorySlotChanged || repairSlotContentsChanged ) {
				addArmoursAndModifiers (repairSlot, stack) ;
				calculateArmourRepairAmt() ;
			}

			repairSlotContentsChanged = false ;
			inputInventorySlotChanged = false ;

		}
		
		if (fluidInventory.getFluid() != null) {
		
			ItemStack[] anvilRecipes = ModCraftingRecipes.getCastingRecipe(fluidInventory.getFluid().getFluid()) ;

			if (anvilRecipes == null) {
				return ;
			}

			for (ItemStack item : anvilRecipes) {

				if (item.getItem() instanceof ItemChainArmour) {
					if (this.getStackInSlot(castSlot) == null) {
						continue ;
					} else {
						if (this.getStackInSlot(castSlot).getItemDamage() != 0) continue ;
					}

				} else if (item.getItem() instanceof ItemPlateArmour) {
					if (this.getStackInSlot(castSlot) == null) {
						continue ;
					} else {
						if (this.getStackInSlot(castSlot).getItemDamage() != 1) continue ;
					}
				}

				if (fluidInventory.getFluidAmount() >= item.stackSize * LibItemStats.VALUE_INGOT ) {
					switch (item.stackSize) {
					case 1:
						setInventorySlotContents(inputInventory.length + 4, item.copy(), true);
						break ;

					case 4:
						addArmoursAndModifiers(inputInventory.length + 3, new ItemStack(item.getItem()) ) ;
						break ;

					case 5:
						addArmoursAndModifiers(inputInventory.length, new ItemStack(item.getItem()) );
						break ;

					case 7:
						addArmoursAndModifiers(inputInventory.length + 2, new ItemStack(item.getItem()) );
						break ;

					case 8:
						addArmoursAndModifiers(inputInventory.length + 1, new ItemStack(item.getItem()) );
						break ;

					default:
						break ;
					}
				}
			}
		}
	}
	
	protected void addArmoursAndModifiers (int slot, ItemStack item) {
		
		if ( item != null && (item.getItem() instanceof ItemModArmour) ) {
			
			if (slot == repairSlot) {
				item = (repairSlotOriginal == null) ? item : repairSlotOriginal.copy();
			}
		
			ItemModArmour armour = (ItemModArmour) item.getItem() ;
			modifiersCosts.removeAll(slot) ;
			
			if ( item.getTagCompound() == null || !(item.getTagCompound().hasKey(LibMisc.MOD_ID)) ) {
				item = armour.buildItem() ;
			}
			
			for (int i = 0; i < inputInventory.length; i ++) {
				
				if (i == repairSlot) continue ;
				
				// Get the current item in input inventory
				ItemStack stack = getStackInSlot (i) ;
				
				if (stack != null) {
					// Gets the list of modifiers in the same family
					// this is applicable for modifiers with multiple levels
					// else its only one single modifier in the list
					List<IModifier> modifiers = ModTraitsModifiersRegistry.getModifierByItem(stack) ;
	
					if (modifiers != null && modifiers.size() > 0) {
						
						for (IModifier modifier : modifiers) {
							
							// Not enough items in input inventory for the modifier
							if (modifier.getItemStack().stackSize > stack.stackSize) break ;
							
							// Armour already have this modifier
							if (LibUtil.armourHasTrait(item, modifier)) continue ;
							
							// Not applicable to this armour type
							if (!modifier.canApplyToEquipment(item)) break ;
							
							armour.addModifier(item, modifier, true, true) ;
							modifiersCosts.put(slot, modifier.getItemStack()) ;
							
							break ;
						}
					}
				}
			}
		}
		
		setInventorySlotContents(slot, item, true) ;
	}
	
	protected void payModifiersCosts (int slot) {
		
		Iterable<ItemStack> costs = modifiersCosts.get(slot) ;
		
		for (ItemStack stack: costs) {
			
			for (int i = 0; i < inputInventory.length; i ++) {
				if (getStackInSlot(i) != null && getStackInSlot(i).getItem() == stack.getItem()) {
					ItemStack item = getStackInSlot(i).copy() ;
					
					item.stackSize = item.stackSize - stack.stackSize ;
					
					if (item.stackSize == 0) {
						item = null ;
					}
					
					setInventorySlotContents(i, item, true) ;
				}
			}
		}
	}
	
	protected void calculateArmourRepairAmt () {
		ItemStack stack = this.getStackInSlot(repairSlot) ;
		
		if (stack == null) return ;
		
		if (stack.getItem() instanceof ItemModArmour) {
			ItemModArmour armour = (ItemModArmour) stack.getItem() ;
			
			ItemStack repairMaterial = armour.getModMaterial().getItemstack() ;
			FluidStack fluid = ModCraftingRecipes.findRecipe(repairMaterial) ;
			
			if (fluidInventory.getFluid() != null && fluid != null && fluid.getFluid() == fluidInventory.getFluid().getFluid() ) {
				
				int repairCost ;
				int repairAmt ;
				FluidStack drainAmt ;
				
				float damage = stack.getMaxDamage() - LibUtil.getItemCurrentDurability(stack) ;
				
				if (stack.isItemDamaged()) {
					repairCost = (int) Math.ceil(damage / (LibItemStats.VALUE_INGOT * LibItemStats.REPAIR_PER_MB)) * LibItemStats.VALUE_INGOT ;
				} else {
					repairCost = 0 ;
				}
				
				drainAmt = this.drain(null, repairCost, false) ;
				
				if (drainAmt == null) {
					return ;
				}
				
				if (drainAmt.amount == repairCost) {
					repairAmt = repairCost ;
				
				} else {
					repairAmt = drainAmt.amount ;
				}
				
				this.drain(null, drainAmt, true) ;
				LibUtil.repairArmour(null, stack, repairAmt) ;
			}
		}
	}

	@Override
	public void readCustomNBT (NBTTagCompound cmp) { 
		super.readCustomNBT(cmp) ;
		
		TileForgeAnvil inventory = this ;
		NBTTagList nbttaglist = cmp.getTagList("Inventory" , 10) ;
		
		fluidInventory.readFromNBT(cmp) ;
		
		for (int i = 0; i < nbttaglist.tagCount(); i ++) {
			NBTTagCompound itemTag = nbttaglist.getCompoundTagAt(i) ;
			
			int slot = itemTag.getByte("slot") & 255 ;
			
			if (slot >= 0 && slot < inventory.getSizeInventory()) {
				inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag), true);
			}
		}
		
		readReapirSlotNBT (cmp) ;
		
		lastRedstone = cmp.getBoolean("lastRedstone") ;
		
		// Locked Fluid
		nbttaglist = cmp.getTagList("lockedFluid" , 10) ;		
		NBTTagCompound fluidTag = nbttaglist.getCompoundTagAt(0) ;
		
		if (!fluidTag.hasKey("noLocked")) {
			lockedFluid = FluidStack.loadFluidStackFromNBT(fluidTag) ;
		}
	}
	
	private void readReapirSlotNBT (NBTTagCompound cmp) {
		NBTTagList nbttaglist = cmp.getTagList("repairSlot" , 10) ;
		NBTTagCompound itemTag = nbttaglist.getCompoundTagAt(0) ;
		
		repairSlotOriginal = ItemStack.loadItemStackFromNBT(itemTag) ;
	}
	
	@Override
	public void writeCustomNBT (NBTTagCompound cmp) {
		super.writeCustomNBT(cmp);
		
		IInventory inventory = this ;
		NBTTagList nbttaglist = new NBTTagList();
		
		fluidInventory.writeToNBT(cmp) ;
		
		for (int i = 0; i < inventory.getSizeInventory(); i ++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound itemTag = new NBTTagCompound () ;
				
				itemTag.setByte("slot", (byte) i) ;
				inventory.getStackInSlot(i).writeToNBT(itemTag) ;
				nbttaglist.appendTag(itemTag) ;
			}
		}
		
		cmp.setTag("Inventory", nbttaglist) ;
		writeRepairSlotNBT (cmp) ;
		
		cmp.setBoolean("lastRedstone", lastRedstone);
		
		// Locked Fluid
		nbttaglist = new NBTTagList();
		NBTTagCompound fluidTag = new NBTTagCompound () ;
		
		if (lockedFluid != null) {
			lockedFluid.writeToNBT(fluidTag) ;
		} else {
			fluidTag.setBoolean("noLocked", true);
		}
		nbttaglist.appendTag(fluidTag);
		
		cmp.setTag("lockedFluid", nbttaglist);
	}
	
	private void writeRepairSlotNBT (NBTTagCompound cmp) {
		NBTTagList nbttaglist = new NBTTagList();
		NBTTagCompound itemTag = new NBTTagCompound () ;
		
		if (repairSlotOriginal != null) {
			repairSlotOriginal.writeToNBT(itemTag) ;
		}
		
		nbttaglist.appendTag(itemTag) ;
		cmp.setTag("repairSlot", nbttaglist) ;
	}
	
	protected void updateOutputInventory () {
		for (int i = inputInventory.length; i < inputInventory.length + outputInventory.length; i++){
			setInventorySlotContents(i, null, true);
		}
	}
	
	public void handleRedstoneSignal (boolean signal) {
		if (signal && signal != lastRedstone) {
			
			if (fluidInventory.getFluid() == null) {
				return ;
			}
			
			lockedFluid = fluidInventory.getFluid() ;
			
			lastRedstone = signal ;
		
		} else if (!signal && signal != lastRedstone) {
			lockedFluid = null ;
			
			lastRedstone = signal ;
		}
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing) {
		
		if ( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ) {
			return true ;
		}
		
		return super.hasCapability(capability, facing);
	}

	@Nonnull
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing) {
		if( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ) {
			return (T) itemHandler;
		}
		
		return super.getCapability(capability, facing);
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
	public ContainerMod createContainer(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
		return new ContainerAnvil <TileForgeAnvil> (this, inventoryPlayer) ;
	}

	@Override
	public GuiContainer createGui(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
		return new ForgeAnvilGui (createContainer(inventoryPlayer, world, pos), this) ;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return inputInventory.length + outputInventory.length ;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index >= 0 && index < inputInventory.length){
			return inputInventory[index];
		} else if (index >= inputInventory.length && index < getSizeInventory()) {
			return outputInventory[index - inputInventory.length];
		}
		return null;
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
		
//		System.out.println("Removing from slot " + index);
		
		if (index == repairSlot) {
			payModifiersCosts(index) ;
		}

		setInventorySlotContents (index, null, true) ;				
		
		int temp = index - inputInventory.length;
		
		switch (temp){
		case 0:
			this.drain(null, LibItemStats.VALUE_INGOT * 5, true) ;
			payModifiersCosts(index) ;
			
			break;
		case 1:
			this.drain(null, LibItemStats.VALUE_INGOT * 8, true) ;
			payModifiersCosts(index) ;
			
			break;
		case 2:
			this.drain(null, LibItemStats.VALUE_INGOT * 7, true) ;
			payModifiersCosts(index) ;
			
			break;
		case 3:
			this.drain(null, LibItemStats.VALUE_INGOT * 4, true) ;
			payModifiersCosts(index) ;
			
			break;
		case 4:
			this.drain(null, LibItemStats.VALUE_INGOT, true) ;
			break;
		}
//		System.out.println("Remaining Fluid: " + fluidInventory.getFluidAmount());
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		setInventorySlotContents (index, stack, false) ;
	}
	
	public void setInventorySlotContents(int index, ItemStack stack, boolean forced) {
		if (index < 0 || index >= this.getSizeInventory()) return ;
		
		if (!isItemValidForSlot(index, stack) && !forced) return ; 
		
		if (worldObj != null && !ItemStack.areItemStacksEqual(stack, getStackInSlot(index)) && !worldObj.isRemote && worldObj instanceof WorldServer) {
			PacketHandler.sendToPlayers((WorldServer) worldObj, getPos(), new ForgeAnvilInventoryUpdatePacket (getPos(), stack, index, fluidInventory.getFluid()));
			
			if (index == repairSlot && stack != null) {
				if (getStackInSlot(index) == null) {
					repairSlotOriginal = stack.copy() ;
				}
				
				repairSlotContentsChanged = true ;
			
			} else if ( index < inputInventory.length ) {
				inputInventorySlotChanged = true ;
			}
			
		}
		
		if (index < inputInventory.length) {
			inputInventory[index] = stack ;
		} else {
			outputInventory[index - inputInventory.length] = stack ;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
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
		if (index < 0) return false ;
		
		if (index >= inputInventory.length && stack == null) {
			return true ;
		}
		
		else if (index >= inputInventory.length && stack != null) {
			return false ;
		}
		
		return true ;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
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
		// NO OP
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int amt = fluidInventory.fill (resource, doFill) ;
		
		if (doFill) tankContentChanged() ;
		return amt ;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (resource == null || fluidInventory.getFluidAmount() <= 0) {
			return null ;
		}
		
		if (!fluidInventory.getFluid().isFluidEqual(resource)) {
			return null ;
		}
		
		return this.drain (from, resource.amount, doDrain) ;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		FluidStack amt = fluidInventory.drain(maxDrain, doDrain) ;
		
		if (doDrain) {
			tankContentChanged() ;
			updateOutputInventory() ;
		}
		return amt ;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		
		if (fluidInventory.getFluid() != null && fluidInventory.getFluid().getFluid() == fluid) return true ;
		
		for (Fluid liquid: WHITELIST_LIQUIDS) {
			
			if (liquid == fluid) {
				return true ;
			}
		}
		
		return false ;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return fluidInventory.getCapacity() > 0 && fluidInventory.getFluid().getFluid() == fluid ;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { new FluidTankInfo (fluidInventory) } ;
	}
	
	public FluidTankInfo getTankInfo() {
		return getTankInfo(null)[0] ;
	}
	
	public void setFluidInventory (FluidStack stack) {
		this.fluidInventory.setFluid(stack) ;
	}
	
	public void tankContentChanged () {
		if (worldObj != null && !worldObj.isRemote) {
			PacketHandler.sendToAll(new ForgeAnvilInventoryUpdatePacket (getPos(), null, -1, fluidInventory.getFluid()) );
		}
	}
	
	public Fluid getLockedFluid () {
		return (lockedFluid == null) ? null : lockedFluid.getFluid() ;
	}
}
