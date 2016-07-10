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
import teamOD.armourReborn.common.lib.LibItemNames.ArmourTypeNames;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibMisc;

public final class ModItems {
	
	public static Item MATERIALS ;
	public static Item ModBook ;
	
	/**
	 * Stores the armourset for each armourType/material combo
	 */
	private static Map<String, Map<EntityEquipmentSlot, Item>> modArmours = Maps.newHashMap() ;
	
	/**
	 * The identifier string for armormaterials in this hashmap is registered as : material + armourType
	 */
	private static Map<String, ArmorMaterial> armourMaterials = Maps.newHashMap() ;
	
	public static void init () {
		MATERIALS = new ItemMaterials () ;
		ModBook = new ItemKnowledgeTome (LibItemNames.ModBook) ;
		
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
		
		for (ArmourTypeNames key : LibItemStats.armourTypesStats.keySet()) {
			for (MaterialsMod material : materials) {
				
				ArmorMaterial mat ;
				String armourName = material.getIdentifier() + key.getName() ;
				
				if (armourMaterials.containsKey(armourName)) {
					mat = armourMaterials.get(armourName) ;
				} else {
					mat = addArmourMaterial(key, material) ;
				}
				
				Map<EntityEquipmentSlot, Item> armour = Maps.newEnumMap(EntityEquipmentSlot.class) ;
				
				if (key.matches(ArmourTypeNames.PLATE)) {
					
					armour.put (EntityEquipmentSlot.HEAD, new ItemPlateArmour (armourName + "helm", material, mat, EntityEquipmentSlot.HEAD, 1)) ;
					armour.put (EntityEquipmentSlot.CHEST, new ItemPlateArmour (armourName + "chest", material, mat, EntityEquipmentSlot.CHEST, 1)) ;
					armour.put (EntityEquipmentSlot.LEGS, new ItemPlateArmour (armourName + "legs", material, mat, EntityEquipmentSlot.LEGS, 2)) ;
					armour.put (EntityEquipmentSlot.FEET, new ItemPlateArmour (armourName + "boots", material, mat, EntityEquipmentSlot.FEET, 1)) ;
					
					modArmours.put(armourName, armour) ;
					addArmourSet (armour) ;
				
				} else if (key.matches(ArmourTypeNames.CHAIN)) {
					armour.put (EntityEquipmentSlot.HEAD, new ItemChainArmour (armourName + "helm", material, mat, EntityEquipmentSlot.HEAD, 1)) ;
					armour.put (EntityEquipmentSlot.CHEST, new ItemChainArmour (armourName + "chest", material, mat, EntityEquipmentSlot.CHEST, 1)) ;
					armour.put (EntityEquipmentSlot.LEGS, new ItemChainArmour (armourName + "legs", material, mat, EntityEquipmentSlot.LEGS, 2)) ;
					armour.put (EntityEquipmentSlot.FEET, new ItemChainArmour (armourName + "boots", material, mat, EntityEquipmentSlot.FEET, 1)) ;
					
					modArmours.put(armourName, armour) ;
					addArmourSet (armour) ;
				
				} else if (key.matches(ArmourTypeNames.LEATHER)) {
					armour.put (EntityEquipmentSlot.HEAD, new ItemLeatherCompositeArmour (armourName + "helm", material, mat, EntityEquipmentSlot.HEAD, 1)) ;
					armour.put (EntityEquipmentSlot.CHEST, new ItemLeatherCompositeArmour (armourName + "chest", material, mat, EntityEquipmentSlot.CHEST, 1)) ;
					armour.put (EntityEquipmentSlot.LEGS, new ItemLeatherCompositeArmour (armourName + "legs", material, mat, EntityEquipmentSlot.LEGS, 2)) ;
					armour.put (EntityEquipmentSlot.FEET, new ItemLeatherCompositeArmour (armourName + "boots", material, mat, EntityEquipmentSlot.FEET, 1)) ;
					
					modArmours.put(armourName, armour) ;
					addArmourSet (armour) ;
				}
			}
		}
	}
	
	private static void addArmourSet (Map<EntityEquipmentSlot, Item> armour) {
		// Establish armour set
		for (Item item : armour.values()) {
			ItemModArmour armourItem = (ItemModArmour) item ;
			
			armourItem.armourSet[0] = new ItemStack (armour.get(EntityEquipmentSlot.HEAD)) ;
			armourItem.armourSet[1] = new ItemStack (armour.get(EntityEquipmentSlot.CHEST)) ;
			armourItem.armourSet[2] = new ItemStack (armour.get(EntityEquipmentSlot.LEGS)) ;
			armourItem.armourSet[3] = new ItemStack (armour.get(EntityEquipmentSlot.FEET)) ;
		}
	}
	
	private static ArmorMaterial addArmourMaterial (ArmourTypeNames key, MaterialsMod material) {
		
		String name = material.getIdentifier() + key.getName() ; // i.e "ironplate", "ironchain", "ironleather"
		String textureName = LibMisc.PREFIX_MOD + ":" + name ;
		
		int durability = (int) (material.getBaseDurabilityMultiplier() + LibItemStats.armourTypesStats.get(key)[1]) ;
		int[] reductionAmounts = new int[4] ;
		
		for (int i = 0; i < reductionAmounts.length; i ++) {
			reductionAmounts[i] = (int) Math.round(material.getBaseArmourValue()[i] * LibItemStats.armourTypesStats.get(key)[0]) ;
		}
		
		ArmorMaterial mat = EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, 0, SoundEvents.item_armor_equip_generic) ;
		armourMaterials.put(mat.getName(), mat) ;
		
		return mat ;
	}
	
	public static Map<EntityEquipmentSlot, Item> getArmourByName (String name) {
		return modArmours.get(name) ;
	}
	
	public static ArmorMaterial getArmorMaterialByName (String name) {
		return armourMaterials.get(name) ;
	}
	
	public static Iterable<Map<EntityEquipmentSlot, Item>> getAllModArmour () {
		return modArmours.values() ;
	}
}
