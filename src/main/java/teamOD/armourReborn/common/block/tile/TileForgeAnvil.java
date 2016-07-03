package teamOD.armourReborn.common.block.tile;

import java.util.Stack;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import teamOD.armourReborn.client.core.gui.ForgeAnvilGui;
import teamOD.armourReborn.client.core.gui.ForgeGui;
import teamOD.armourReborn.common.block.tile.inventory.ContainerAnvil;
import teamOD.armourReborn.common.block.tile.inventory.ContainerMod;
import teamOD.armourReborn.common.block.tile.inventory.ITileInventory;
import teamOD.armourReborn.common.block.tile.network.ForgeAnvilInventoryUpdatePacket;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.network.PacketHandler;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.fluids.FluidMod;
import teamOD.armourReborn.common.lib.LibItemStats;

public class TileForgeAnvil extends TileMod implements IInventory, ITileInventory, IFluidHandler {
	
	public static final ImmutableList<FluidMod> WHITELIST_LIQUIDS = ImmutableList.copyOf(ModFluids.modFluids) ;
	
	private ItemStack[] inputInventory ;
	private ItemStack[] outputInventory;
	private FluidTank fluidInventory;
	
	public TileForgeAnvil(){
		inputInventory = new ItemStack[4];
		outputInventory = new ItemStack[5];
		fluidInventory = new FluidTank(5000);
	}
	
	@Override
	protected void updateEntity() {
		if (fluidInventory.getFluid() == null) {
			return ;
		}
		
		ItemStack[] anvilRecipes = ModCraftingRecipes.getCastingRecipe(fluidInventory.getFluid().getFluid()) ;
		
		if (anvilRecipes == null) {
			return ;
		}
		
		for (ItemStack item : anvilRecipes) {
			if (fluidInventory.getFluidAmount() >= item.stackSize * LibItemStats.VALUE_INGOT ) {
				switch (item.stackSize) {
				case 1:
					setInventorySlotContents(8, new ItemStack(item.getItem()), true);
					break ;
					
				case 4:
					setInventorySlotContents(7, new ItemStack(item.getItem()), true);
					break ;
					
				case 5:
					setInventorySlotContents(4, new ItemStack(item.getItem()), true);
					break ;
					
				case 7:
					setInventorySlotContents(6, new ItemStack(item.getItem()), true);
					break ;
					
				case 8:
					setInventorySlotContents(5, new ItemStack(item.getItem()), true);
					break ;
					
				default:
						break ;
				}
			}
		}
	}
	
	protected void addArmoursAndModifiers (int slot, ItemStack item) {
		for (int i = 0; i < inputInventory.length; i ++) {
			ItemStack stack = getStackInSlot (i) ;
			
			if (stack != null) {
				// TODO add modifiers to armour here
			}
		}
		
		setInventorySlotContents(slot, item, true) ;
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
	}
	
	public void sync () {
		return ;
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
		
		System.out.println("Removing from slot " + index);
		
		if (index >= 4){
			for (int i=4; i<9; i++){
				setInventorySlotContents(i, null, true);
			}
		} else {
			setInventorySlotContents (index, null, true) ;
		}
		
		switch (index){
		case 4:
			fluidInventory.drain(LibItemStats.VALUE_INGOT * 5, true);
			break;
		case 5:
			fluidInventory.drain(LibItemStats.VALUE_INGOT * 8, true);
			break;
		case 6:
			fluidInventory.drain(LibItemStats.VALUE_INGOT * 7, true);
			break;
		case 7:
			fluidInventory.drain(LibItemStats.VALUE_INGOT * 4, true);
			break;
		case 8:
			fluidInventory.drain(LibItemStats.VALUE_INGOT * 1, true);
			break;
		}
		System.out.println("Remaining Fluid: " + fluidInventory.getFluidAmount());
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
		if (index < 0 || index >= inputInventory.length) return false ;
		
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
		
		if (doDrain) tankContentChanged() ;
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
}
