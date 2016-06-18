package teamOD.armourReborn.common.block;

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
	}
}
