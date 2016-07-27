package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import teamOD.armourReborn.common.achievement.IOnCraftAchievement;
import teamOD.armourReborn.common.achievement.ModAchievements;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.crafting.ModMaterials;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public class ItemLeatherCompositeArmour extends ItemModArmour implements IOnCraftAchievement {

	public ItemLeatherCompositeArmour (String name, MaterialsMod material, ArmorMaterial mat, EntityEquipmentSlot type, int index) {
		super(type, name, mat, index);
		this.materials = material ;
		
		armourSetEnchantments = ImmutableMap.of(Enchantment.getEnchantmentByID(7), 2) ;
	}

	@Override
	public List<ITrait> getArmourTypeTrait() {
		// TODO Auto-generated method stub
		return ImmutableList.<ITrait>of(ModTraitsModifiersRegistry.reprisal1) ;
	}

	@Override
	public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory inventory) {
		return ModAchievements.craftingArmour ;
	}
	
	public ItemStack replaceOrRepairArmour (EntityPlayer player, ItemStack armour, ItemStack stack) {
		if ( !(armour.getItem() instanceof ItemModArmour) ) return armour ;
		
		if (ModMaterials.getMaterialFromItem(stack.getItem()) != null) {
			MaterialsMod mat = ModMaterials.getMaterialFromItem(stack.getItem()) ;
			
			if (mat.getIdentifier().equals(materials.getIdentifier())) {
				LibUtil.repairArmour(player, armour, LibItemStats.VALUE_INGOT);
			} else {
				// Fully repair item and replace material
				LibUtil.repairArmour(player, armour, Integer.MAX_VALUE);
							
				String newArmourName = mat.getIdentifier() + LibItemNames.ArmourTypeNames.LEATHER.getName() ;
				Item newArmour = ModItems.getArmourByName(newArmourName).get(this.armorType) ;
				
				ItemStack newArmourStack = new ItemStack (newArmour) ;
				newArmourStack.setTagCompound( (NBTTagCompound) armour.getTagCompound().copy() );
				
				for (ITrait trait : mat.getTraits()) {
					this.addModifier(newArmourStack, trait, false, false) ;
				}
				
				return newArmourStack ;
			}
		}
		
		return armour ;
	}

}
