package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public class ItemPlateArmour extends ItemModArmour {
	
	public ItemPlateArmour (String name, MaterialsMod material, ArmorMaterial mat, EntityEquipmentSlot type, int index) {
		super (type, name, mat, index) ;
		this.materials = material ;
		
		armourSetEnchantments = ImmutableMap.of(Enchantment.getEnchantmentByID(0), 2) ;
	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return ImmutableList.<ITrait>of(ModTraitsModifiersRegistry.waterlogged, ModTraitsModifiersRegistry.fireResist) ;
	}

}
