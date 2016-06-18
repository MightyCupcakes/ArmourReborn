package teamOD.armourReborn.common.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.network.PacketHandler;
import teamOD.armourReborn.common.world.WorldGenReborn;

public class CommonProxy {
	
	public void preInit (FMLPreInitializationEvent event) {
		ModBlocks.init () ;
		ModItems.init () ;
		ModFluids.init ();
		
		PacketHandler.init(); 
	}
	
	public void init (FMLInitializationEvent event) {
		ModCraftingRecipes.init() ;	
		GameRegistry.registerWorldGenerator(WorldGenReborn.INSTANCE, 0);	
	}
	
	public void postInit (FMLPostInitializationEvent event) {
		
	}
 
}
