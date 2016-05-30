package teamOD.armourReborn.common.block;

import teamOD.armourReborn.common.lib.LibItemNames;

public final class ModBlocks {
	public static BlockForgeMaster forgeMaster ;
	public static BlockMod forgeBlocks ;
	
	public static void init () {
		forgeMaster = new BlockForgeMaster () ;
		forgeBlocks = new BlockForgeStructure (LibItemNames.FORGE_BLOCK) ;
	}
}
