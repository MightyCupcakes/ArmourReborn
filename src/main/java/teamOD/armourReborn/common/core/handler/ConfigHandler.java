package teamOD.armourReborn.common.core.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public final class ConfigHandler {
	
	public static Configuration config ;
	
	public static boolean generateOre = true ;
	public static boolean disableVanillaArmours = false ;
	public static int fuelConsumedPerTick = 1 ;
	
	public static void init (File configFile) {
		config = new Configuration (configFile) ;
		
		config.load() ;
		load() ;
		
		config.save() ;
	}
	
	public static void load () {
		String desc ;
		
		desc = "Set this to false to disable the world generation of this mod's added ores." + "\n" ;
		desc += "Make sure your pack has an alternative way to obtain the following ores if disabled:" + "\n" ;
		desc += "Copper Ore, Aluminum Ore" ;
		generateOre = loadPropBool ("enable.worldGen", desc, generateOre) ;
		
		desc = "If set to true, all vanilla armours will not provide any protection to the user" ;
		disableVanillaArmours = loadPropBool ("disable.vanillaArmour", desc, disableVanillaArmours) ;
		
		desc = "The amount of fuel consumed per tick. The conversion from this integer to mB is x/6 where x is this value." ;
		fuelConsumedPerTick = loadPropInteger ("furnance.fuelMultiplier", desc, fuelConsumedPerTick) ;
		
		
	}
	
	private static boolean loadPropBool(String propName, String desc, boolean default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, default_);
		prop.setComment(desc);
		
		return prop.getBoolean(default_);
	}
	
	private static double loadPropDouble (String propName, String desc, double default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, default_);
		prop.setComment(desc);
		
		return prop.getDouble(default_);
	}
	
	private static int loadPropInteger (String propName, String desc, int default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, default_);
		prop.setComment(desc);
		
		return prop.getInt(default_);
	}
}
