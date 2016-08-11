package teamOD.armourReborn.common.core.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public final class ConfigHandler {
	
	public static Configuration config ;
	
	public static boolean generateOre = true ;
	
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
		
		
	}
	
	private static boolean loadPropBool(String propName, String desc, boolean default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, propName, default_);
		prop.setComment(desc);
		
		return prop.getBoolean(default_);
	}
}
