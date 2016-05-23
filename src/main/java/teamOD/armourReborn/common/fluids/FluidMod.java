package teamOD.armourReborn.common.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import teamOD.armourReborn.common.lib.LibMisc;

public class FluidMod extends Fluid {
	public final int colour ;
	
	public static final ResourceLocation molten_still = new ResourceLocation (LibMisc.MOD_ID, "blocks/fluids/molten_metal") ;
	public static final ResourceLocation molten_flowing = new ResourceLocation (LibMisc.MOD_ID, "blocks/fluids/molten_metal_flow") ;
	
	public FluidMod (String fluidName, int colour, ResourceLocation still, ResourceLocation flowing) {
		super (fluidName, still, flowing) ;
		
		this.colour = colour ;
		this.setDensity(2000) ;
		this.setViscosity(10000) ;
		this.setTemperature(1000) ;
		this.setLuminosity(10) ;
		
		setUnlocalizedName (LibMisc.MOD_ID + "." + fluidName) ;
		
		if (shouldRegister()) {
			FluidRegistry.registerFluid(this) ;
			FluidRegistry.addBucketForFluid(this) ;
		}
	}
	
	public FluidMod (String fluidName, int colour) {
		this (fluidName, colour, molten_still, molten_flowing) ;
	}
	
	protected boolean shouldRegister () {
		// If for some reason you don't want to register this fluid, override this method
		return true ;
	}
	
	@Override
	public int getColor () {
		return colour ;
	}
}
