package teamOD.armourReborn.common.crafting;

import java.util.HashSet;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Lists;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import teamOD.armourReborn.common.lib.LibItemStats;

public class AlloyRecipes {
	
	private final FluidStack output ;
	private final ImmutableMap<Fluid, Integer> input ;
	private boolean requireBlastFurnance ;
	
	public AlloyRecipes (FluidStack output, FluidStack... input) {
		this.output = output ;
		this.requireBlastFurnance = false ;
		
		Builder<Fluid, Integer> builder = ImmutableMap.<Fluid, Integer>builder() ;
		
		for (FluidStack fluid : input) {
			builder.put(fluid.getFluid(), fluid.amount) ;
		}
		
		this.input = builder.build() ;
	}
	
	/**
	 * Given a list of fluidstacks, determine whether this alloy can be created.
	 * 
	 * @param fluids	the list of fluids in a tank
	 * @return true if one unit of this alloy can be created; false otherwise
	 */
	public boolean matches (List<FluidStack> fluids) {
		
		int matches = 0 ;
		
		if (fluids.size() < input.size()) return false ;
		
		for (FluidStack fluid : fluids) {
			if ( input.containsKey(fluid.getFluid()) ) {
				int amt = input.get(fluid.getFluid()) ;
				
				if (fluid.amount >= amt) matches ++ ;
			}
		}
		
		if (matches == input.size()) return true ;
		
		return false ;
	}
	
	public List<FluidStack> getRequiredMetals () {
		
		List<FluidStack> result = Lists.newLinkedList() ;
		
		for (Fluid key : input.keySet()) {
			FluidStack stack = new FluidStack (key, input.get(key) * LibItemStats.VALUE_INGOT) ;
			result.add(stack) ;
		}
		
		return result ;
	}
	
	public FluidStack getOutput () {
		FluidStack result = output.copy() ;
		
		result.amount = LibItemStats.VALUE_INGOT * output.amount ;
		return result ;
	}
	
	public void setBlastFurnace () {
		this.requireBlastFurnance = !(this.requireBlastFurnance) ;
	}
}
