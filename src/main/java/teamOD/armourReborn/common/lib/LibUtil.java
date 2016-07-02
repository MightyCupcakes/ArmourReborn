package teamOD.armourReborn.common.lib;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLLog;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

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
	
	public static String getPrefix (String name) {
		return LibMisc.PREFIX_MOD + "." + name ;
	}

	public static NBTTagCompound writePos(BlockPos pos) {
		NBTTagCompound tag = new NBTTagCompound();
		if(pos != null) {
			tag.setInteger("X", pos.getX());
			tag.setInteger("Y", pos.getY());
			tag.setInteger("Z", pos.getZ());
		}
		return tag;
	}
	
	public static BlockPos readPos(NBTTagCompound tag) {
		if(tag != null) {
			return new BlockPos(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
		}
		return null;
	}
	
	public static float getRandomFloat () {
		Random randGenerator = new Random () ;
		
		return randGenerator.nextFloat() ;
	}
	
	// Modifiers tags utilities 
	
	public static NBTTagList getModifiersTag (ItemStack stack) {
		if ( !(stack.getItem() instanceof IModifiable) ) {
			return null ;
		}
		
		NBTTagCompound tag = stack.getTagCompound() ;
		NBTTagList result = tag.getTagList("traits", 10) ;
		
		return result ;
	}
	
	public static List<ITrait> getModifiersList (ItemStack stack) {
		NBTTagList tagList = getModifiersTag(stack) ;
		
		if (tagList == null) {
			return null ;
		}
		
		List<ITrait> list = Lists.newLinkedList() ;
		
		for (int i = 0; i < tagList.tagCount(); i ++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i) ;
			
			list.add(ModTraitsModifiersRegistry.getTraitFromIdentifier(tag.getString(IModifiable.IDENTIFIER))) ;
		}
		
		return list ;
	}

}
