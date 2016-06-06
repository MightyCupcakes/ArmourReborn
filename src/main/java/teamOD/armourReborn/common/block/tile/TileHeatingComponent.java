package teamOD.armourReborn.common.block.tile;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileHeatingComponent extends TileForgeComponent implements IFluidHandler {
	
	public static final int CAPACITY = 1000 ;
	
	public FluidTank tank ;
	
	public TileHeatingComponent () {
		this.tank = new FluidTank (CAPACITY) ;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		int amt = tank.fill (resource, doFill) ;
		
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

}
