package teamOD.armourReborn.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import teamOD.armourReborn.common.achievement.AchievementEvents;
import teamOD.armourReborn.common.core.GuiHandler;
import teamOD.armourReborn.common.core.handler.ConfigHandler;
import teamOD.armourReborn.common.core.proxy.CommonProxy;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.item.equipment.ItemArmourEvents;
import teamOD.armourReborn.common.leveling.LevelingEventHandler;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.modifiers.ModifierEvents;
import teamOD.armourReborn.common.tweaks.AddDropsToMobs;
import teamOD.armourReborn.common.tweaks.NerfVanillaArmours;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION, dependencies = LibMisc.DEPENDENCIES, acceptedMinecraftVersions = LibMisc.MC_VERSIONS) 

public class ArmourReborn {
	
	@Instance(LibMisc.MOD_ID)
	public static ArmourReborn instance;
	
	@SidedProxy(serverSide = LibMisc.PROXY_COMMON, clientSide = LibMisc.PROXY_CLIENT) 
	public static CommonProxy proxy ;
	
	public static GuiHandler guiHandler = new GuiHandler() ;
	
	static {
		ModFluids.registerBucket() ;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		
		proxy.preInit(event) ;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event) ;
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event) ;
		
		if ( ConfigHandler.disableVanillaArmours ) {
			MinecraftForge.EVENT_BUS.register( new NerfVanillaArmours () );
		}
		
		MinecraftForge.EVENT_BUS.register( new LevelingEventHandler () );
		MinecraftForge.EVENT_BUS.register( new ModifierEvents () );
		MinecraftForge.EVENT_BUS.register( new AddDropsToMobs () );
		MinecraftForge.EVENT_BUS.register( new AchievementEvents () );
		MinecraftForge.EVENT_BUS.register( new ItemArmourEvents () ) ;
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}
}
