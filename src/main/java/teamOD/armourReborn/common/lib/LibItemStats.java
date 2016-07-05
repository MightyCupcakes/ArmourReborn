package teamOD.armourReborn.common.lib;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public final class LibItemStats {
	// UNUSED!!
	public static final ArmorMaterial paperArmourMat = EnumHelper.addArmorMaterial("PAPER", "paper", 1, new int[] {1,2,3,2}, 1, SoundEvents.item_armor_equip_generic) ;
	public static final ArmorMaterial ironArmourMat = EnumHelper.addArmorMaterial("IRON", "modIron", 16, new int[] {2,4,4,2}, 1, SoundEvents.item_armor_equip_iron) ;
	public static final ArmorMaterial steelArmourMat = EnumHelper.addArmorMaterial("STEEL", "steel", 20, new int[] {4,4,4,2}, 1, SoundEvents.item_armor_equip_gold) ;
	public static final ArmorMaterial aluminiumArmourMat = EnumHelper.addArmorMaterial("ALUMINIUM", "aluminium", 34, new int[] {3,8,6,3}, 1, SoundEvents.item_armor_equip_diamond) ;
	
	public static final ImmutableMap <String, double[]> armourTypesStats = ImmutableMap.of("plate", new double[] {1,1}, "chain", new double[] {0.8, 1.25}, "leather", new double[] {0.7,0.8}) ;
	
	public static final int VALUE_INGOT = 144;
	public static final int VALUE_NUGGET = VALUE_INGOT / 9;
	public static final int VALUE_BLOCK = VALUE_INGOT * 9;
	public static final int VALUE_ORE = VALUE_INGOT * 2;
	
	public static final int DEFAULT_MODIFIER_SLOTS = 3 ;
	
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
