package teamOD.armourReborn.common.fluids;

import net.minecraft.item.Item;
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
	
	public static Item molten_iron ;
	
	public static void registerBucket () {
		FluidRegistry.enableUniversalBucket();
	}
	
	public static void init () {
		
		registerMoltenBlock (iron, molten_iron, "iron", LibMisc.CONTROL_CODE_COLORS[15]) ;
	}
	
	private static void registerMoltenBlock (Fluid fluid, Item item, String name, int colour) {
		
		String regName = "molten_" + name ;
		
		fluid = new FluidMod (regName, colour) ;
		BlockModFluid block = new BlockModFluid (fluid) ;
		
		item = new ItemMod (regName) ;
		
		ResourceLocation resource = new ResourceLocation (LibMisc.MOD_ID, regName) ;
		
		GameRegistry.register(block, resource) ;
		GameRegistry.register(item) ;
		
		fluid.setBlock(block) ;
		
		block.setUnlocalizedName (regName) ;
	}
}
