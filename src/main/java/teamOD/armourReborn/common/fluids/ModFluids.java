package teamOD.armourReborn.common.fluids;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.block.BlockModFluid;
import teamOD.armourReborn.common.item.ItemMaterials;
import teamOD.armourReborn.common.item.ItemMod;
import teamOD.armourReborn.common.lib.LibMisc;

public class ModFluids {
	public static FluidMod iron ;
	public static FluidMod steel ;
	public static FluidMod aluminium ;
	
	public static void registerBucket () {
		FluidRegistry.enableUniversalBucket();
	}
	
	public static void init () {
		
		iron = registerMoltenBlock ("iron", 0xFFFF0000) ;
		steel = registerMoltenBlock ("steel", 0xFFFFFFFF) ;
		aluminium = registerMoltenBlock ("aluminium", 0x40404040) ;
	}
	
	private static FluidMod registerMoltenBlock (String name, int colour) {
		
		FluidMod fluid ;
		String regName = "molten_" + name ;
		
		fluid = new FluidMod (regName, colour) ;
		
		BlockModFluid block = new BlockModFluid (fluid) ;
		
		fluid.setBlock(block) ;
		
		return fluid ;
	}
}
