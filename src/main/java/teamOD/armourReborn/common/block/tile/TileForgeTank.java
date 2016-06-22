package teamOD.armourReborn.common.block.tile;

import com.google.common.collect.ImmutableList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileForgeTank extends TileForgeComponent implements IFluidHandler {
	
	// List of allowed fuels.
	public static final ImmutableList<Fluid> ALLOWED_FUEL = ImmutableList.of (FluidRegistry.LAVA) ;
	
	public static final int CAPACITY = 2000 ;
	public static final int DRAIN_AMT = 1 ; // fuel consumed per tick
	
	public FluidTank tank ;
	
	public TileForgeTank () {
		tank = new FluidTank (CAPACITY) ;
	}
	
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
			if (this.hasMaster()) {
				getMasterBlock().updateInternalTemps(resource.getFluid().getTemperature()) ;
				getMasterBlock().addFuel (resource.amount) ;
			}
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
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		tank.readFromNBT(cmp) ;
	}
}
