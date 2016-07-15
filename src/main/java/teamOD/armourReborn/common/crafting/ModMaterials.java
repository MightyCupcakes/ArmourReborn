package teamOD.armourReborn.common.crafting;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import teamOD.armourReborn.common.item.ItemMod;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public class ModMaterials {
	
	private static HashMap<Item, MaterialsMod> materialsRegistry = new HashMap<Item, MaterialsMod> () ;
	
	public static MaterialsMod paper ;
	public static MaterialsMod iron ;
	public static MaterialsMod steel ;
	public static MaterialsMod aluAlloy ;
	
	public static void init () {
		
		// paper
		paper = new MaterialsMod ("paper", Items.paper, 
				ImmutableList.of(
						ModTraitsModifiersRegistry.waterlogged, 
						ModTraitsModifiersRegistry.evasion2,
						ModTraitsModifiersRegistry.flammable
						)) ;
		
		paper.setBaseDurabilityMultiplier(1D).setBaseArmourValue(new int[] {3,4,3,2}) ;
		registerMaterial (paper.getItem(), paper) ;
		
		
		//iron
		iron = new MaterialsMod ("iron", Items.iron_ingot, 
				ImmutableList.of( 
						ModTraitsModifiersRegistry.reprisal1
						)) ;
		
		iron.setBaseDurabilityMultiplier(3D).setBaseArmourValue(new int[] {3,4,3,2}) ;
		registerMaterial (iron.getItem(), iron) ;
		
		//steel
		steel = new MaterialsMod ("steel", ModItems.MATERIALS, 
				ImmutableList.of( 
						ModTraitsModifiersRegistry.reprisal2
						)) ;
		
		steel.setBaseDurabilityMultiplier(5D).setBaseArmourValue(new int[] {3,4,3,2}) ;
		registerMaterial (steel.getItem(), steel) ;
		
		//aluAlloy
		aluAlloy = new MaterialsMod ("aluminium", ModItems.MATERIALS, 
				ImmutableList.of( 
						ModTraitsModifiersRegistry.frostbite
						)) ;
		
		aluAlloy.setBaseDurabilityMultiplier(4D).setBaseArmourValue(new int[] {3,4,3,2}) ;
		registerMaterial (aluAlloy.getItem(), aluAlloy) ;
		
	}
	
	public static MaterialsMod getMaterialFromItem (Item item) {
		return materialsRegistry.get(item) ;
	}
	
	private static void registerMaterial (Item item, MaterialsMod material) {
		materialsRegistry.put(item, material) ;
	}
	
	public static Iterable<MaterialsMod> getAllRegisteredMaterials () {
		return materialsRegistry.values() ;
	}
}
