package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import teamOD.armourReborn.common.modifiers.ITrait;

public class ItemLeatherCompositeArmour extends ItemModArmour {

	public ItemLeatherCompositeArmour(EntityEquipmentSlot type, String name, ArmorMaterial mat, int index) {
		super(type, name, mat, index);

		this.armourModifier = 0.7D ;
		this.durabilityModifier = 0.8D ;
	}

	@Override
	protected void addArmourSetEnchantments(ItemStack armour) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return null;
	}

}
