package teamOD.armourReborn.common.crafting;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public class ModMaterials {
	
	public static HashMap<Item, MaterialsMod> materialsRegistry = new HashMap<Item, MaterialsMod> () ;
	
	public static MaterialsMod paper ;
	public static MaterialsMod iron ;
	public static MaterialsMod steel ;
	public static MaterialsMod aluAlloy ;
	
	public static void init () {
		
		// paper
		paper = new MaterialsMod ("paper", Items.paper, 
				ImmutableList.of(
						ModTraitsModifiersRegistry.waterlogged, 
						ModTraitsModifiersRegistry.evasion2
						)) ;
		
		paper.setBaseDurabilityMultiplier(1D).setBaseArmourValue(new int[] {3,4,3,2}) ;
		registerMaterial (paper.getItem(), paper) ;
	}
	
	public static MaterialsMod getMaterialFromItem (Item item) {
		return materialsRegistry.get(item) ;
	}
	
	private static void registerMaterial (Item item, MaterialsMod material) {
		materialsRegistry.put(item, material) ;
	}
}
