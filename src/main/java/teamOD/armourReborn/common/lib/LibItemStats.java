package teamOD.armourReborn.common.lib;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import teamOD.armourReborn.common.lib.LibItemNames.ArmourTypeNames;

public final class LibItemStats {
	// UNUSED!!
//	public static final ArmorMaterial paperArmourMat = EnumHelper.addArmorMaterial("PAPER", "paper", 1, new int[] {1,2,3,2}, 1, SoundEvents.item_armor_equip_generic) ;
//	public static final ArmorMaterial ironArmourMat = EnumHelper.addArmorMaterial("IRON", "modIron", 16, new int[] {2,4,4,2}, 1, SoundEvents.item_armor_equip_iron) ;
//	public static final ArmorMaterial steelArmourMat = EnumHelper.addArmorMaterial("STEEL", "steel", 20, new int[] {4,4,4,2}, 1, SoundEvents.item_armor_equip_gold) ;
//	public static final ArmorMaterial aluminiumArmourMat = EnumHelper.addArmorMaterial("ALUMINIUM", "aluminium", 34, new int[] {3,8,6,3}, 1, SoundEvents.item_armor_equip_diamond) ;
	
	/** 
	 * The string is the name of this armourset.
	 * 
	 * The double array represents the values and should contain only 2 values.
	 * The first value in the array represents the Armour value modifier
	 * and the second represents the durability modifier
	 */
	public static final ImmutableMap <ArmourTypeNames, double[]> armourTypesStats = ImmutableMap.of(
			ArmourTypeNames.PLATE, new double[] {1,1}, 
			ArmourTypeNames.CHAIN, new double[] {0.8, 1.25}, 
			ArmourTypeNames.LEATHER, new double[] {0.7,0.8}
			) ;
	
	public static final int DEFAULT_MODIFIER_SLOTS = 2 ;
	
	public static final int EXP_PER_DMG = 5 ;
	public static final int EXP_PER_REPAIR = 10 ;
	public static final float EXP_MULTIPLIER_PLAYER_DMG = 2 ;
	
	public static final float REPAIR_PER_MB = 1 ;
	
	public static final int VALUE_INGOT = 144;
	public static final int VALUE_NUGGET = VALUE_INGOT / 9;
	public static final int VALUE_BLOCK = VALUE_INGOT * 9;
	public static final int VALUE_ORE = VALUE_INGOT * 2;
	
	public static int getValue (String suffix) {
		if (suffix.equals("nugget")) {
			return VALUE_NUGGET ;
		} 
		
		else if (suffix.equals("ingot")) {
			return VALUE_INGOT ;
		}
		
		else if (suffix.equals("block")) {
			return VALUE_BLOCK ;
		}
		
		else if (suffix.equals("ore")) {
			return VALUE_ORE ;
		}
		
		return 0 ;
	}
}
