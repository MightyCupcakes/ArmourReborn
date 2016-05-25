package teamOD.armourReborn.common.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLLog;

public class LibUtil {
	
	/**
	 * Log a message using the logger provided by ForgeModLoader
	 * @param int level. Level describes the severity, with level 1 being a log just for information and 3 being a critical error message
	 * @param format a string formatter
	 * @param message
	 */
	public static void LogToFML (int level, String format, String message) {
		
		switch (level) {
		case 1:
			FMLLog.info(format, message) ;
			break ;
		case 2:
			FMLLog.warning(format, message) ;
			break ;
		case 3:
			FMLLog.severe(format, message) ;
			break ;
		default:
			FMLLog.fine(format, message) ;
		}
	}
	
	public static boolean hasTag (NBTTagCompound tag, String key) {
		return tag.hasKey(key) ;
	}
	
	public static NBTTagCompound getModCompoundTag (ItemStack stack) {
		return stack.getTagCompound().getCompoundTag("ArmourReborn") ;
	}

}
