package teamOD.armourReborn.common.block.tile;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import scala.actors.threadpool.Arrays;
import teamOD.armourReborn.common.block.tile.inventory.InternalForgeTank;
import teamOD.armourReborn.common.block.tile.network.ForgeFuelUpdatePacket;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.network.PacketHandler;

public class TileHeatingComponent extends TileForgeComponent implements IFluidHandler {
	
	// List of allowed fuels.
	public static final ImmutableList<Fluid> ALLOWED_FUEL = ImmutableList.of (FluidRegistry.LAVA) ;
	
	public static final int CAPACITY = 2000 ;
	public static final int DRAIN_AMT = 1 ; // fuel consumed per tick
	public static final int FUEL_MULTIPLIER = 6 ;
	
	private int fuelLeft ;
	private int internalTemp ;
	
	private int[] itemTemps ;
	private int[] itemMeltingTemps ;
	
	protected FluidTank tank ;
	
	public TileHeatingComponent () {
		this.tank = new FluidTank (CAPACITY) ;
		
		itemTemps = new int[TileForgeMaster.INVENTORY_SIZE] ;
		itemMeltingTemps = new int[TileForgeMaster.INVENTORY_SIZE] ;
		
		internalTemp = 20 ;
	}
	
	public void heatItems (InternalForgeTank internalTank) {
		if (!this.hasMaster()) return ;
		
		TileForgeMaster master = this.getMasterBlock() ;
		
		boolean hasHeated = false ;
		
		for (int i = 0; i < master.getSizeInventory(); i ++) {
			ItemStack stack = master.getStackInSlot(i) ;
			
			if (stack == null) continue ;

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
					itemTemps[i] += internalTemp / 300 ;
					hasHeated = true ;
				}
			} else {
				// no fuel
				return ;
			}
		}
		
		if (hasHeated) consumeFuel () ;
	}
	
	public void createAlloys () {
		
	}
	
	public void consumeFuel () {
		FluidStack fluid = tank.getFluid() ;
		
		if (fluid != null) {
			int amt = tank.getFluidAmount() ;
			
			if (amt > (fuelLeft/FUEL_MULTIPLIER) ){
				int drainAmt = amt - (fuelLeft/FUEL_MULTIPLIER) ;
				
				FluidStack drained = tank.drain(drainAmt, false) ;
				
				if (drained.amount == drainAmt) {
					tank.drain(drainAmt, true) ;
					PacketHandler.sendToAll(new ForgeFuelUpdatePacket (getPos(), tank.getFluid()));
				}
			}
			
			fuelLeft -= DRAIN_AMT ;
		}
	}
	
	public void updateItemHeatReq (int slot, ItemStack stack) {
		// TODO get melting temps from recipes
		
		if (stack == null) {
			itemMeltingTemps[slot] = 0 ;
		
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
				masterTank.fill(fluid, true) ;
				getMasterBlock().setInventorySlotContents(slot, null) ;
				System.out.println("Item Melted, fluid added:" + fluid.getFluid().getUnlocalizedName());
			}
		}
		
	}
	
	// Fluid handlers
	
	public FluidTankInfo getTankInfo () {
		return tank.getInfo() ;
	}
	
	public void setFuelStack (FluidStack stack) {
		tank.setFluid(stack) ;
	}
	
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int amt = tank.fill (resource, doFill) ;
		
		if (doFill) {
			internalTemp = resource.getFluid().getTemperature() ;
			fuelLeft += resource.amount * FUEL_MULTIPLIER ;
		}
		
		return amt ;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (resource == null || tank.getFluidAmount() <= 0) {
			return null ;
		}
		
		if (!tank.getFluid().isFluidEqual(resource)) {
			return null ;
		}
		
		return this.drain (from, resource.amount, doDrain) ;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		FluidStack amt = tank.drain(maxDrain, doDrain) ;
		
		if (doDrain) fuelLeft -= (maxDrain * FUEL_MULTIPLIER) ;
		
		if (fuelLeft < 0) fuelLeft = 0 ;
		
		return amt ;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		
		if (tank.getFluid() != null && tank.getFluid().getFluid() == fluid) return true ;
		
		for (Fluid liquid: ALLOWED_FUEL) {
			
			if (liquid == fluid) {
				return true ;
			}
		}
		
		return false ;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return tank.getCapacity() > 0 && tank.getFluid().getFluid() == fluid ;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { new FluidTankInfo (tank) } ;
	}
	
	// NBTS
	
	@Override
	public void writeCustomNBT(NBTTagCompound cmp) {
		tank.writeToNBT(cmp) ;
		
		cmp.setInteger("fuel", fuelLeft) ;
		cmp.setInteger("temp", internalTemp) ;
		cmp.setIntArray("itemTemps", itemTemps) ;
		cmp.setIntArray("itemMeltingTemps", itemMeltingTemps) ;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		tank.readFromNBT(cmp) ;
		
		fuelLeft = cmp.getInteger("fuel") ;
		internalTemp = cmp.getInteger("temp") ;
		itemTemps = cmp.getIntArray("itemTemps") ;
		itemMeltingTemps = cmp.getIntArray("itemMeltingTemps") ;
	}
}
