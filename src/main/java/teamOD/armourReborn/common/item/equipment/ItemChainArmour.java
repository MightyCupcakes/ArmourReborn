package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public class ItemChainArmour extends ItemModArmour {

	public ItemChainArmour (String name, MaterialsMod material, ArmorMaterial mat, EntityEquipmentSlot type, int index) {
		super (type, name, mat, index) ;
		this.materials = material ;
		
		armourSetEnchantments = ImmutableMap.of(Enchantments.PROJECTILE_PROTECTION, 2) ;
	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return ImmutableList.<ITrait>of(ModTraitsModifiersRegistry.evasion1) ;
	}

}
