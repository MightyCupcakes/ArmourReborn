package teamOD.armourReborn.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamOD.armourReborn.common.core.proxy.CommonProxy;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.leveling.LevelingEventHandler;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.tweaks.NerfVanillaArmours;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION, dependencies = LibMisc.DEPENDENCIES, acceptedMinecraftVersions = LibMisc.MC_VERSIONS) 

public class ArmourReborn {
	
	public static boolean thaumcraftLoaded = false;
	
	@Instance(LibMisc.MOD_ID)
	public static ArmourReborn instance;
	
	@SidedProxy(serverSide = LibMisc.PROXY_COMMON, clientSide = LibMisc.PROXY_CLIENT) 
	public static CommonProxy proxy ;
	
	static {
		ModFluids.registerBucket() ;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		thaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
		
		MinecraftForge.EVENT_BUS.register(this);
		
		proxy.preInit(event) ;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event) ;
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event) ;
		MinecraftForge.EVENT_BUS.register( new NerfVanillaArmours () );
		MinecraftForge.EVENT_BUS.register( new LevelingEventHandler () );
	}
}
