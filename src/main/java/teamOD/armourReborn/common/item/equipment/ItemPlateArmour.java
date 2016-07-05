package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import teamOD.armourReborn.common.modifiers.ITrait;

public class ItemPlateArmour extends ItemModArmour {
	
	public ItemPlateArmour (String name, ArmorMaterial mat, EntityEquipmentSlot type, int index) {
		super (type, name, mat, index) ;
		
		this.armourModifier = 1D ;
		this.durabilityModifier = 1D ;
	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return null;
	}

}
