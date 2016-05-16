package teamOD.armourReborn.common;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamOD.armourReborn.common.lib.LibMisc;

@Mod(modid = LibMisc.MOD_ID, version = LibMisc.VERSION, dependencies = LibMisc.DEPENDENCIES, acceptedMinecraftVersions = LibMisc.MC_VERSIONS) 

public class ArmourReborn {
	
	public static boolean thaumcraftLoaded = false;
	
	@Instance(LibMisc.MOD_ID)
	public static ArmourReborn instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		thaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// some example code
	    System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
