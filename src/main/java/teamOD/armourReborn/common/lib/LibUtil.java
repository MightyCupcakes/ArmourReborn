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
	public static void LogToFML (int level, String format, Object... data) {
		
		switch (level) {
		case 1:
			FMLLog.info(format, data) ;
			break ;
		case 2:
			FMLLog.warning(format, data) ;
			break ;
		case 3:
			FMLLog.severe(format, data) ;
			break ;
		default:
			FMLLog.fine(format, data) ;
		}
	}
	
	public static boolean hasTag (NBTTagCompound tag, String key) {
		return tag.hasKey(key) ;
	}
	
	public static NBTTagCompound getModCompoundTag (ItemStack stack) {
		return stack.getTagCompound().getCompoundTag("ArmourReborn") ;
	}

}
