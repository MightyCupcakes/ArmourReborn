package teamOD.armourReborn.common.fluids;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import teamOD.armourReborn.common.block.BlockModFluid;

public class ModFluids {
	public static FluidMod iron ;
	public static FluidMod steel ;
	public static FluidMod aluminium ;
	public static FluidMod copper ;
	public static FluidMod gold ;
	public static FluidMod coal ;
	public static FluidMod aluAlloy ;
	public static FluidMod silicate ;
	
	public static FluidMod fiendFyre ;
	
	public static List<FluidMod> modFluids = Lists.newLinkedList() ;
	
	public static void registerBucket () {
		FluidRegistry.enableUniversalBucket();
	} ;
	
	public static void init () {
		
		iron = registerMoltenBlock ("iron", 0xFFFF0000) ;
		steel = registerMoltenBlock ("steel", 0xFFFFFFFF, 1200, true) ;
		aluminium = registerMoltenBlock ("aluminium", 0xff808080, 1300, true) ;
		copper = registerMoltenBlock ("copper", 0xffC87533) ;
		gold = registerMoltenBlock ("gold", 0xfff6d609) ;
		coal = registerMoltenBlock ("coal", 0xff000000) ;
		aluAlloy = registerMoltenBlock ("aluminiumalloy", 0xffC5DAE0) ;
		silicate = registerMoltenBlock ("silicate", 0xff8693b6, 1600, true) ;
		
		fiendFyre = registerMoltenBlock ("fiendfyre", 0xffffc529, 2600, false) ;
		fiendFyre.setLuminosity(15) ;
	}
	
	public static ItemStack getUniversalBucket (FluidMod fluid) {
		UniversalBucket bucket = ForgeModContainer.getInstance().universalBucket ;
		
		return UniversalBucket.getFilledBucket (bucket, fluid) ;
	}
	
	private static FluidMod registerMoltenBlock (String name, int colour) {
		return registerMoltenBlock (name, colour, 1000, true) ;
	}
	
	private static FluidMod registerMoltenBlock (String name, int colour, int meltingPoint, boolean allowedFluids) {
		
		FluidMod fluid ;
		String regName = "molten_" + name ;
		
		fluid = new FluidMod (regName, colour) ;
		fluid.setTemperature(meltingPoint) ;
		
		BlockModFluid block = new BlockModFluid (fluid) ;
		
		fluid.setBlock(block) ;
		
		FluidRegistry.addBucketForFluid(fluid);
		
		if (allowedFluids) modFluids.add(fluid) ;
		
		return fluid ;
	}
}
