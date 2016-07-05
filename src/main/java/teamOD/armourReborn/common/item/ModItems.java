package teamOD.armourReborn.common.item;


import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import teamOD.armourReborn.common.lib.LibItemNames;

public final class ModItems {
	
	public static Item MATERIALS ; 
	
	public static void init () {
		MATERIALS = new ItemMaterials () ;
		
		registerOreDict ();
		
		// add minecraft:Coal to oredict
		OreDictionary.registerOre("ingotCoal", Items.coal);
	}
	
	public static void registerOreDict () {
		for (int i = 0; i < LibItemNames.MATERIALS_NAMES.length; i ++) {
			OreDictionary.registerOre(LibItemNames.MATERIALS_NAMES[i], new ItemStack (MATERIALS, 1, i)) ;
		}
	}
}
