package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.modifiers.ITrait;

public class ItemChainArmour extends ItemModArmour {

	public ItemChainArmour (String name, MaterialsMod material, ArmorMaterial mat, EntityEquipmentSlot type, int index) {
		super (type, name, mat, index) ;
		this.materials = material ;
	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return ImmutableList.<ITrait>of() ;
	}

}
