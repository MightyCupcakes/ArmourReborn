package teamOD.armourReborn.common.block.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileHeatingComponent extends TileForgeComponent implements IFluidHandler {
	
	public static final int CAPACITY = 2000 ;
	public static final int DRAIN_AMT = 10 ; // mB of fuel consumed per tick 
	
	private int fuelLeft ;
	private int internalTemp ;
	
	private int[] itemTemps ;
	private int[] itemMeltingTemps ;
	
	public FluidTank tank ;
	
	public TileHeatingComponent () {
		this.tank = new FluidTank (CAPACITY) ;
		
		itemTemps = new int[TileForgeMaster.INVENTORY_SIZE] ;
		itemMeltingTemps = new int[TileForgeMaster.INVENTORY_SIZE] ;
		
		internalTemp = 20 ;
	}
	
	public void heatItems () {
		if (!this.hasMaster()) return ;
		
		TileForgeMaster master = this.getMasterBlock() ;
		
		boolean hasHeated = false ;
		
		for (int i = 0; i < master.getSizeInventory(); i ++) {
			ItemStack stack = master.getStackInSlot(i) ;
			
			if (stack == null) continue ;
			
			if (itemMeltingTemps[i] == 0) continue ;
			
			if (fuelLeft >= 10) {
				if (itemTemps[i] >= itemMeltingTemps[i]) {
					// TODO Melt stuff here according to recipes
					
					itemMeltingTemps[i] = 0 ;
					hasHeated = true ;
				
				} else {
					itemTemps[i] += internalTemp / 100 ;
					hasHeated = true ;
				}
			} else {
				// no fuel
				return ;
			}
		}
		
		if (hasHeated) consumeFuel () ;
	}
	
	public void consumeFuel () {
		FluidStack fluid = tank.getFluid() ;
		
		if (fluid != null) {
			FluidStack drained = tank.drain(DRAIN_AMT, false) ;
			
			if (drained.amount == DRAIN_AMT) {
				tank.drain(DRAIN_AMT, true) ;
			}
		}
	}
	
	public void updateItemHeatReq (int slot, ItemStack stack) {
		// TODO get melting temps from recipes
	}
	
	// Fluid handlers
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int amt = tank.fill (resource, doFill) ;
		
		if (doFill) {
			internalTemp = resource.getFluid().getTemperature() ;
			fuelLeft += resource.amount ;
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
		
		if (doDrain) fuelLeft -= maxDrain ;
		
		if (fuelLeft < 0) fuelLeft = 0 ;
		
		return amt ;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return tank.getCapacity() == 0 || (tank.getFluid().getFluid() == fluid && tank.getFluidAmount() < tank.getCapacity()) ;
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
		cmp.setInteger("fuel", fuelLeft) ;
		cmp.setInteger("temp", internalTemp) ;
		cmp.setIntArray("itemTemps", itemTemps) ;
		cmp.setIntArray("itemMeltingTemps", itemMeltingTemps) ;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		fuelLeft = cmp.getInteger("fuel") ;
		internalTemp = cmp.getInteger("temp") ;
		itemTemps = cmp.getIntArray("itemTemps") ;
		itemMeltingTemps = cmp.getIntArray("itemMeltingTemps") ;
	}
}
