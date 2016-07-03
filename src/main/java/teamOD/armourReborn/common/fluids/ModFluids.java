package teamOD.armourReborn.common.fluids;

import java.util.List;

import com.google.common.collect.Lists;

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
	public static FluidMod copper ;
	public static FluidMod gold ;
	public static FluidMod coal ;
	
	public static List<FluidMod> modFluids = Lists.newLinkedList() ;
	
	public static void registerBucket () {
		FluidRegistry.enableUniversalBucket();
	} ;
	
	public static void init () {
		
		iron = registerMoltenBlock ("iron", 0xFFFF0000) ;
		steel = registerMoltenBlock ("steel", 0xFFFFFFFF, 1200) ;
		aluminium = registerMoltenBlock ("aluminium", 0xff808080, 1300) ;
		copper = registerMoltenBlock ("copper", 0xffC87533) ;
		gold = registerMoltenBlock ("gold", 0xfff6d609) ;
		coal = registerMoltenBlock ("coal", 0xff000000) ;
	}
	
	private static FluidMod registerMoltenBlock (String name, int colour) {
		return registerMoltenBlock (name, colour, 1000) ;
	}
	
	private static FluidMod registerMoltenBlock (String name, int colour, int meltingPoint) {
		
		FluidMod fluid ;
		String regName = "molten_" + name ;
		
		fluid = new FluidMod (regName, colour) ;
		fluid.setTemperature(meltingPoint) ;
		
		BlockModFluid block = new BlockModFluid (fluid) ;
		
		fluid.setBlock(block) ;
		
		FluidRegistry.addBucketForFluid(fluid);
		modFluids.add(fluid) ;
		
		return fluid ;
	}
}
