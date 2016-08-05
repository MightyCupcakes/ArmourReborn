package teamOD.armourReborn.common.core.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.achievement.ModAchievements;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.core.command.CommandGiveModArmour;
import teamOD.armourReborn.common.core.command.CommandLevelUpArmour;
import teamOD.armourReborn.common.core.handler.ConfigHandler;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.crafting.ModMaterials;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.leveling.ModLevels;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;
import teamOD.armourReborn.common.network.PacketHandler;
import teamOD.armourReborn.common.potion.ModPotions;
import teamOD.armourReborn.common.world.WorldGenReborn;

public class CommonProxy {
	
	public void preInit (FMLPreInitializationEvent event) {
		
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		
		ModBlocks.init () ;
		ModItems.init () ;
		ModFluids.init ();
		ModTraitsModifiersRegistry.init() ;
		ModLevels.init() ;
		ModMaterials.init() ;
		ModPotions.init() ;
		
		ModItems.initArmours() ;
		
		PacketHandler.init(); 
	}
	
	public void init (FMLInitializationEvent event) {
		ModCraftingRecipes.init() ;	
		ModAchievements.init() ;
		
		if (ConfigHandler.generateOre) {
			GameRegistry.registerWorldGenerator(WorldGenReborn.INSTANCE, 0);
		}
	}
	
	public void postInit (FMLPostInitializationEvent event) {
		
	}
	
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand (new CommandGiveModArmour ()) ;
		event.registerServerCommand (new CommandLevelUpArmour ()) ;
	}
 
}
