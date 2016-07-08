package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.modifiers.ITrait;

public class ItemLeatherCompositeArmour extends ItemModArmour {

	public ItemLeatherCompositeArmour (String name, ArmorMaterial mat, EntityEquipmentSlot type, int index) {
		super(type, name, mat, index);
		
		armourSetEnchantments = ImmutableMap.of(Enchantment.getEnchantmentByID(7), 2) ;
	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return ImmutableList.<ITrait>of() ;
	}

}
