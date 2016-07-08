package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import teamOD.armourReborn.common.modifiers.ITrait;

public class ItemPlateArmour extends ItemModArmour {
	
	public ItemPlateArmour (String name, ArmorMaterial mat, EntityEquipmentSlot type, int index) {
		super (type, name, mat, index) ;
	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return ImmutableList.<ITrait>of() ;
	}

	@Override
	protected void addArmourSetEnchantments(ItemStack armour) {
		// TODO Auto-generated method stub
		
	}

}
