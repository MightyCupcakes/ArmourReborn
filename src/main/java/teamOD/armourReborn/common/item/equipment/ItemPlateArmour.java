package teamOD.armourReborn.common.item.equipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPlateArmour extends ItemModArmour {
	
	public ItemPlateArmour (EntityEquipmentSlot type, String name, ArmorMaterial mat) {
		super (type, "plate", mat) ;
	}
	
	@Override
	public void onArmorTick (World world, EntityPlayer player, ItemStack stack) {
		if ( hasArmourSet (player) ) {
			// do stuff here
		}
	}

}
