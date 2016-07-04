package teamOD.armourReborn.common.lib;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLLog;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;
import teamOD.armourReborn.common.modifiers.ModifierEvents;

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
	
	/**
	 * Gets the list of modifiers/traits associated only to its materials from a IModifiable
	 * 
	 */
	public static List<ITrait> getModifiersListMaterials (ItemStack stack) {
		return getModifiersList (stack, true, false) ;
	}
	
	public static List<ITrait> getModifiersListAll (ItemStack stack) {
		return getModifiersList (stack, false, false) ;
	}
	
	/**
	 * Gets the modifiers list from the given itemstack. Only applicable to items that implements IModifiable. Otherwise it returns null.
	 * 
	 * @param stack
	 * @param onlyMaterials		If set to true, only traits/modifiers associated to materials are included.
	 * @param onlyArmourSet		If set to true, only traits/modifiers associated to the armour set are included.
	 * 
	 */
	public static List<ITrait> getModifiersList (ItemStack stack, boolean onlyMaterials, boolean onlyArmourSet) {
		NBTTagList tagList = getModifiersTag(stack) ;
		
		if (tagList == null) {
			return null ;
		}
		
		List<ITrait> list = Lists.newLinkedList() ;
		boolean isArmourSet = false ;
		
		for (int i = 0; i < tagList.tagCount(); i ++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i) ;
			
			if (tag.hasKey("seperator")) {
				isArmourSet = true ;
				continue ;
			}
			
			if (isArmourSet && onlyMaterials) {
				break ;
			
			} else if (!isArmourSet && onlyArmourSet) {
				continue ;
			}
			
			list.add(ModTraitsModifiersRegistry.getTraitFromIdentifier(tag.getString(IModifiable.IDENTIFIER))) ;
		}
		
		return list ;
	}
	
	public static List<String> getItemToolTip (EntityPlayer player, ItemStack stack, ItemModArmour armour) {
		List<String> tooltip = Lists.newLinkedList() ;
		
		List<ITrait> traits ;
		
		if (!armour.hasArmourSet(player)) {
			traits = getModifiersListMaterials (stack) ;
		} else {
			traits = getModifiersListAll (stack) ;
		}
		
		for (ITrait trait : traits) {
			tooltip.add( formatIdentifier(trait) ) ;
		}
		
		return tooltip ;
	}
	
	public static String formatIdentifier (ITrait trait) {
		String s, identifier ;
		identifier = trait.getIdentifier() ;
		
		s = "" + trait.getColour() ;
		
		s += identifier.substring(0, 1).toUpperCase() + identifier.substring(1) ;
		
		return s ;
	}
	
	public static void repairArmour (ItemStack stack, int amount) {
		
		ModifierEvents.OnRepair.fireEvent(stack, amount) ;
		
		int repairAmt = Math.min(amount, stack.getItemDamage()) ;
		stack.setItemDamage(stack.getItemDamage() - repairAmt) ;
	}

}
