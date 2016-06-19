package teamOD.armourReborn.common.block;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import teamOD.armourReborn.common.lib.LibItemNames;

public final class ModBlocks {
	public static BlockForgeMaster forgeMaster ;
	public static BlockMod forgeBlocks ;
	public static BlockMod forgeHeater ;
	public static BlockMod oresMaterials ;
	
	public static void init () {
		forgeMaster = new BlockForgeMaster () ;
		forgeBlocks = new BlockForgeStructure (LibItemNames.FORGE_BLOCK) ;
		forgeHeater = new BlockForgeHeater (LibItemNames.FORGE_HEATER) ;
		oresMaterials = new BlockMaterial (LibItemNames.oreMATERIALS) ;
		
		registerOreDict () ;
	}
	
	private static void registerOreDict () {
		for (int i = 0; i < LibItemNames.ORE_MATERIALS_NAMES.length; i ++) {
			OreDictionary.registerOre (LibItemNames.ORE_MATERIALS_NAMES[i], new ItemStack (oresMaterials, 1, i) ) ;
		}
	}
}
