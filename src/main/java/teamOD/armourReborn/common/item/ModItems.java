package teamOD.armourReborn.common.item;


import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.crafting.ModMaterials;
import teamOD.armourReborn.common.item.equipment.ItemChainArmour;
import teamOD.armourReborn.common.item.equipment.ItemLeatherCompositeArmour;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;
import teamOD.armourReborn.common.item.equipment.ItemPlateArmour;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibMisc;

public final class ModItems {
	
	public static Item MATERIALS ;
	
	/**
	 * Stores the armourset for each armourType/material combo
	 * index 0 is helmet, 1 is plate, 2 is legs and 3 is boots 
	 */
	private static Map<String, Item[]> modArmours = Maps.newHashMap() ;
	/**
	 * The identifier string for armormaterials in this hashmap is registered as : material + armourType
	 */
	private static Map<String, ArmorMaterial> armourMaterials = Maps.newHashMap() ;
	
	public static void init () {
		MATERIALS = new ItemMaterials () ;
		
		registerOreDict () ;
		registerArmours () ;
		
		// add minecraft:Coal to oredict
		OreDictionary.registerOre("ingotCoal", Items.coal);
	}
	
	public static void registerOreDict () {
		for (int i = 0; i < LibItemNames.MATERIALS_NAMES.length; i ++) {
			OreDictionary.registerOre(LibItemNames.MATERIALS_NAMES[i], new ItemStack (MATERIALS, 1, i)) ;
		}
	}
	
	/**
	 * Register all registered materials and armour types as items to the game registry.
	 * 
	 */
	private static void registerArmours () {
		
		Iterable<MaterialsMod> materials = ModMaterials.getAllRegisteredMaterials() ;
		
		for (String key : LibItemStats.armourTypesStats.keySet()) {
			for (MaterialsMod material : materials) {
				
				ArmorMaterial mat ;
				String armourName = material.getIdentifier() + key ;
				
				if (armourMaterials.containsKey(armourName)) {
					mat = armourMaterials.get(armourName) ;
				} else {
					mat = addArmourMaterial(key, material) ;
				}
				
				Item[] armour = new Item[4] ;
				
				if (key.equals("plate")) {
					
					armour[0] = new ItemPlateArmour (armourName + "helm", material, mat, EntityEquipmentSlot.HEAD, 1) ;
					armour[1] = new ItemPlateArmour (armourName + "chest", material, mat, EntityEquipmentSlot.CHEST, 1) ;
					armour[2] = new ItemPlateArmour (armourName + "legs", material, mat, EntityEquipmentSlot.LEGS, 2) ;
					armour[3] = new ItemPlateArmour (armourName + "boots", material, mat, EntityEquipmentSlot.FEET, 1) ;
					
					modArmours.put(material.getIdentifier() + key, armour) ;
					addArmourSet (armour) ;
				
				} else if (key.equals("chain")) {
					armour[0] = new ItemChainArmour (armourName + "helm", material, mat, EntityEquipmentSlot.HEAD, 1) ;
					armour[1] = new ItemChainArmour (armourName + "chest", material, mat, EntityEquipmentSlot.CHEST, 1) ;
					armour[2] = new ItemChainArmour (armourName + "legs", material, mat, EntityEquipmentSlot.LEGS, 2) ;
					armour[3] = new ItemChainArmour (armourName + "boots", material, mat, EntityEquipmentSlot.FEET, 1) ;
					
					modArmours.put(material.getIdentifier() + key, armour) ;
					addArmourSet (armour) ;
				
				} else if (key.equals("leather")) {
					armour[0] = new ItemLeatherCompositeArmour (armourName + "helm", material, mat, EntityEquipmentSlot.HEAD, 1) ;
					armour[1] = new ItemLeatherCompositeArmour (armourName + "chest", material, mat, EntityEquipmentSlot.CHEST, 1) ;
					armour[2] = new ItemLeatherCompositeArmour (armourName + "legs", material, mat, EntityEquipmentSlot.LEGS, 2) ;
					armour[3] = new ItemLeatherCompositeArmour (armourName + "boots", material, mat, EntityEquipmentSlot.FEET, 1) ;
					
					modArmours.put(material.getIdentifier() + key, armour) ;
					addArmourSet (armour) ;
				}
				
				
				
			}
		}
	}
	
	private static void addArmourSet (Item[] armour) {
		// Establish armour set
		for (int i = 0; i < armour.length; i ++) {
			ItemModArmour armourItem = (ItemModArmour) armour[i] ;
			
			armourItem.armourSet[0] = new ItemStack (armour[0]) ;
			armourItem.armourSet[1] = new ItemStack (armour[1]) ;
			armourItem.armourSet[2] = new ItemStack (armour[2]) ;
			armourItem.armourSet[3] = new ItemStack (armour[3]) ;
		}
	}
	
	private static ArmorMaterial addArmourMaterial (String key, MaterialsMod material) {
		
		String name = material.getIdentifier() + key ; // i.e "ironplate", "ironchain", "ironleather"
		String textureName = LibMisc.PREFIX_MOD + ":" + name ;
		
		int durability = (int) (material.getBaseDurabilityMultiplier() + LibItemStats.armourTypesStats.get(key)[1]) ;
		int[] reductionAmounts = new int[4] ;
		
		for (int i = 0; i < reductionAmounts.length; i ++) {
			reductionAmounts[i] = (int) Math.round(material.getBaseArmourValue()[i] * LibItemStats.armourTypesStats.get(key)[0]) ;
		}
		
		ArmorMaterial mat = EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, 1, SoundEvents.item_armor_equip_generic) ;
		armourMaterials.put(mat.getName(), mat) ;
		
		return mat ;
	}
	
	public static Item[] getArmourByName (String name) {
		return modArmours.get(name) ;
	}
	
	public static ArmorMaterial getArmorMaterialByName (String name) {
		return armourMaterials.get(name) ;
	}
	
	public static Iterable<Item[]> getAllModArmour () {
		return modArmours.values() ;
	}
}
