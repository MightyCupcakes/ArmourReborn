package teamOD.armourReborn.common.crafting;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibItemNames.ArmourTypeNames;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public class ModMaterials {
	
	private static HashMap<String, MaterialsMod> materialsRegistry = Maps.newHashMap() ;
	
	public static MaterialsMod paper ;
	public static MaterialsMod iron ;
	public static MaterialsMod steel ;
	public static MaterialsMod aluAlloy ;
	
	public static void init () {
		
		// paper
		paper = new MaterialsMod ("paper", new ItemStack (Items.PAPER), 
				ImmutableList.of(
						ModTraitsModifiersRegistry.waterlogged, 
						ModTraitsModifiersRegistry.evasion2,
						ModTraitsModifiersRegistry.flammable
						)) ;
		
		paper.setBaseDurabilityMultiplier(5D).setBaseArmourValue(new int[] {3,4,3,2}).allowArmourSet(ArmourTypeNames.LEATHER) ;
		registerMaterial (paper.getItem().getUnlocalizedName(), paper) ;
		
		
		//iron
		iron = new MaterialsMod ("iron", new ItemStack (Items.IRON_INGOT), 
				ImmutableList.of( 
						ModTraitsModifiersRegistry.rusty
						)) ;
		
		iron.setBaseDurabilityMultiplier(15D).setBaseArmourValue(new int[] {3,5,3,3}).allowArmourSet(ArmourTypeNames.values()) ;
		registerMaterial (iron.getItem().getUnlocalizedName(), iron) ;
		
		//steel
		steel = new MaterialsMod ("steel", new ItemStack (ModItems.MATERIALS, 1, 0), 
				ImmutableList.<ITrait>of(
						ModTraitsModifiersRegistry.durable,
						ModTraitsModifiersRegistry.writable
						)) ;
		
		steel.setBaseDurabilityMultiplier(35D).setBaseArmourValue(new int[] {3,6,4,3}).allowArmourSet(ArmourTypeNames.values()) ;
		registerMaterial (ModItems.MATERIALS.getUnlocalizedName(steel.getItemstack()), steel) ;
		
		//aluAlloy
		aluAlloy = new MaterialsMod ("aluminium", new ItemStack (ModItems.MATERIALS, 1, 3), 
				ImmutableList.of( 
						ModTraitsModifiersRegistry.nullField
						)) ;
		
		aluAlloy.setBaseDurabilityMultiplier(25D).setBaseArmourValue(new int[] {4,6,5,3}).allowArmourSet(ArmourTypeNames.values()) ;
		registerMaterial (ModItems.MATERIALS.getUnlocalizedName(aluAlloy.getItemstack()), aluAlloy) ;
		
	}
	
	public static MaterialsMod getMaterialFromItem (Item item) {
		return materialsRegistry.get(item) ;
	}
	
	private static void registerMaterial (String item, MaterialsMod material) {
		materialsRegistry.put(item, material) ;
	}
	
	public static Iterable<MaterialsMod> getAllRegisteredMaterials () {
		return materialsRegistry.values() ;
	}
}
