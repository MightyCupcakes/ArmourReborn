package teamOD.armourReborn.common.item;


import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.crafting.ModCraftingRecipes;
import teamOD.armourReborn.common.crafting.ModMaterials;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;
import teamOD.armourReborn.common.item.equipment.ItemPlateArmour;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibItemStats;

public final class ModItems {
	
	public static Item MATERIALS ;
	
	/**
	 * Stores the armourset for each armourType/material combo
	 * index 0 is helmet, 1 is plate, 2 is legs and 3 is boots 
	 */
	public static Map<String, Item[]> modArmours = Maps.newHashMap() ;
	
	public static void init () {
		MATERIALS = new ItemMaterials () ;
		
		registerOreDict () ;
		
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
	 * ALL ArmorMaterial must be first be created
	 * 
	 */
	public static void registerArmours () {
		
		Iterable<MaterialsMod> materials = ModMaterials.materialsRegistry.values() ;
		
		for (String key : LibItemStats.armourTypesStats.keySet()) {
			for (MaterialsMod material : materials) {
				ArmorMaterial mat = ModCraftingRecipes.armourMaterials.get(material.getIdentifier() + key) ;
				Item[] armour = new Item[4] ;
				
				if (key.equals("plate")) {
					
					armour[0] = new ItemPlateArmour (material.getIdentifier() + key + "helm", mat, EntityEquipmentSlot.HEAD, 1) ;
					armour[1] = new ItemPlateArmour (material.getIdentifier() + key + "chest", mat, EntityEquipmentSlot.CHEST, 1) ;
					armour[2] = new ItemPlateArmour (material.getIdentifier() + key + "legs", mat, EntityEquipmentSlot.LEGS, 2) ;
					armour[3] = new ItemPlateArmour (material.getIdentifier() + key + "boots", mat, EntityEquipmentSlot.LEGS, 1) ;
					
					modArmours.put(material.getIdentifier() + key, armour) ;
				}
				
				
				// Establish armour set
				for (int i = 0; i < armour.length; i ++)) {
					ItemModArmour armourItem = (ItemModArmour) armour[i] ;
					
					armourItem.armourSet[i] = new ItemStack (armourItem) ;
				}
			}
		}
	}
}
