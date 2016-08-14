package teamOD.armourReborn.common.block.tile;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import scala.actors.threadpool.Arrays;
import teamOD.armourReborn.common.block.tile.inventory.InternalForgeTank;
import teamOD.armourReborn.common.block.tile.network.ForgeFuelUpdatePacket;
import teamOD.armourReborn.common.crafting.AlloyRecipes;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.network.PacketHandler;

public abstract class TileHeatingComponent extends TileMultiBlock {
	
	// List of allowed fuels.
	public static final ImmutableList<Fluid> ALLOWED_FUEL = ImmutableList.of (FluidRegistry.LAVA) ;
	
	public static final int DRAIN_AMT = 1 ; // fuel consumed per tick
	public static final int FUEL_MULTIPLIER = 6 ;
	
	protected int fuelLeft ;
	private int internalTemp ;
	protected BlockPos heaterTankPos ;
	
	protected int[] itemTemps ;
	protected int[] itemMeltingTemps ;
	
	protected boolean forgeBusy ; // If true, the forge will not output its internal liquid inventory to any adjacent anvil.
	
	public TileHeatingComponent (int inventorySize) {
		super () ;
		
		internalTemp = 20 ;
		itemTemps = new int[inventorySize] ;
		itemMeltingTemps = new int[inventorySize] ;
		forgeBusy = false ;
	}
	
	public void heatItems (InternalForgeTank internalTank) {
		
		if (!this.hasMaster()) return ;
		
		TileForgeMaster inventory = this.getMasterBlock() ;
		
		boolean hasHeated = false ;
		forgeBusy = false ;
		
		for (int i = 0; i < inventory.getSizeInventory(); i ++) {
			ItemStack stack = inventory.getStackInSlot(i) ;
			
			if (stack == null) continue ;
			if (getTankAt(heaterTankPos) == null) return ;

			if (itemMeltingTemps[i] == 0) continue ;
			
			if (fuelLeft > 0) {

				if (itemTemps[i] >= itemMeltingTemps[i]) {
					// TODO Melt stuff here according to recipes
					FluidStack fluid = ModCraftingRecipes.findRecipe(stack) ;
					itemMelted (internalTank, i, stack, fluid) ;
					
					itemMeltingTemps[i] = 0 ;
					itemTemps[i] = 0 ;
					
					hasHeated = true ;
				
				} else {
					itemTemps[i] += internalTemp >> 8  ;
					hasHeated = true ;
				}
			} else {
				// no fuel
				searchForFuel () ;
				return ;
			}
		}
		
		if (hasHeated) {
			consumeFuel () ;
			forgeBusy = true ;
		}
	}
	
	public void createAlloys (InternalForgeTank internalTank) {
		AlloyRecipes recipe = ModCraftingRecipes.getAlloyRecipeFromFluid(internalTank.getFluids()) ;
		
		if (recipe != null) {
			forgeBusy = true ;
			
			for (FluidStack fluidDrain : recipe.getRequiredMetals()) {
				internalTank.drain(fluidDrain, true) ;
			}
			
			internalTank.fill(recipe.getOutput(), true) ;
		}
	}
	
	public void searchForFuel () {
		if (heaterTankPos == null) return ;
		
		FluidStack fluid = getTankAt(heaterTankPos).getTankInfo().fluid ;
		
		if (fluid != null) {
			addFuel (fluid.amount) ;
			updateInternalTemps (fluid.getFluid().getTemperature()) ;
		}
	}
	
	public void consumeFuel () {
		
		if (fuelLeft == 0) {
			return ;
		}
		
		FluidStack fluid = getTankAt(heaterTankPos).getTankInfo().fluid ;
		
		if (fluid != null) {
			
			int amt = fluid.amount ;
			
			if (amt > (fuelLeft/FUEL_MULTIPLIER) ){
				int drainAmt = amt - (fuelLeft/FUEL_MULTIPLIER) ;
				
				FluidStack drained = getTankAt(heaterTankPos).drain(EnumFacing.SOUTH, drainAmt, false) ;
				
				if (drained.amount == drainAmt) {
					getTankAt(heaterTankPos).drain(EnumFacing.SOUTH, drainAmt, true) ;
					PacketHandler.sendToAll(new ForgeFuelUpdatePacket (getPos(), fluid));
				}
			}
		}
		
		fuelLeft -= DRAIN_AMT ;
	}
	
	public void updateItemHeatReq (int slot, ItemStack stack) {
		// TODO get melting temps from recipes
		
		if (stack == null) {
			itemMeltingTemps[slot] = 0 ;
			itemTemps[slot] = 0 ;
		
		} else {
			FluidStack fluid = ModCraftingRecipes.findRecipe(stack) ;
			
			if (fluid != null) {
				itemMeltingTemps[slot] = fluid.getFluid().getTemperature() ;
			} else {
				itemMeltingTemps[slot] = 0 ;
			}
		}
	}
	
	public void itemMelted (InternalForgeTank masterTank, int slot, ItemStack item, FluidStack fluid) {
		if (!this.hasMaster()) return ;
		
		if (fluid != null) {
			int amt = masterTank.fill(fluid, false) ;
			
			if (amt == fluid.amount) {
				getMasterBlock().setInventorySlotContents(slot, null) ;
				masterTank.fill(fluid, true) ;
//				System.out.println("Item Melted, fluid added:" + fluid.getFluid().getUnlocalizedName());
			}
		}
		
	}
	
	public float getItemMeltingProgress (int slot) {
		if (itemMeltingTemps[slot] == 0) return 0F ;
		
		return ((float) itemTemps[slot]) / itemMeltingTemps[slot] ;
	}
	
	public void updateInternalTemps (int temp) {
		internalTemp = temp - 300 ;
	}
	
	public void addFuel (int amt) {
		fuelLeft += (amt * FUEL_MULTIPLIER) ;
		
		if (fuelLeft < 0) {
			fuelLeft = 0 ;
			updateInternalTemps(0) ;
		}
	}
	
	public float getMeltingProgress (int slot) {
		
		return getItemMeltingProgress(slot) ;
	}
	
	protected TileForgeTank getTankAt (BlockPos tankPos) {
		if (heaterTankPos == null) return null ;
		
		return (TileForgeTank) worldObj.getTileEntity(tankPos) ;
	}
	
	public void setFuelStack (FluidStack stack) {
		if (heaterTankPos == null) return ;
		
		getTankAt(heaterTankPos).setFuelStack(stack) ;
	}
	
	protected void setHeaterPos (BlockPos newpos) {
		heaterTankPos = newpos ;
	}
	
	// NBTS
	
	@Override
	public void writeCustomNBT(NBTTagCompound cmp) {
		
		super.writeCustomNBT(cmp) ;
		
		NBTTagList tagList = new NBTTagList () ;
		
		tagList.appendTag(LibUtil.writePos(heaterTankPos)) ;
		
		cmp.setTag("heaterPos", tagList);
		
		cmp.setInteger("fuel", fuelLeft) ;
		cmp.setInteger("temp", internalTemp) ;
		cmp.setIntArray("itemTemps", itemTemps) ;
		cmp.setIntArray("itemMeltingTemps", itemMeltingTemps) ;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		
		super.readCustomNBT(cmp) ;
		
		NBTTagList tagList = cmp.getTagList("heaterPos", 10);
		setHeaterPos (LibUtil.readPos(tagList.getCompoundTagAt(0))) ;
		
		fuelLeft = cmp.getInteger("fuel") ;
		internalTemp = cmp.getInteger("temp") ;
		itemTemps = cmp.getIntArray("itemTemps") ;
		itemMeltingTemps = cmp.getIntArray("itemMeltingTemps") ;
	}
}
