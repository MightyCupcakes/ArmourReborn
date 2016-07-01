package teamOD.armourReborn.common.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.lib.LibUtil;

public class FluidMod extends Fluid {
	public final int colour ;
	
	public static final ResourceLocation molten_still = new ResourceLocation (LibMisc.MOD_ID, "blocks/fluids/molten_metal") ;
	public static final ResourceLocation molten_flowing = new ResourceLocation (LibMisc.MOD_ID, "blocks/fluids/molten_metal_flow") ;
	
	public FluidMod (String fluidName, int colour, ResourceLocation still, ResourceLocation flowing) {
		super (fluidName, still, flowing) ;
		
		this.colour = colour ;
		this.setDensity(3000) ;
		this.setViscosity(10000) ;
		
		// This temperature represents the melting point of the associated material. Higher temps == more time to melt
		this.setTemperature(1000) ; 
		this.setLuminosity(10) ;
		
		setUnlocalizedName (LibUtil.getPrefix(fluidName)) ;
		FluidRegistry.registerFluid(this) ;
	}
	
	public FluidMod (String fluidName, int colour) {
		this (fluidName, colour, molten_still, molten_flowing) ;
	}
	
	@Override
	public int getColor () {
		return colour ;
	}
}
