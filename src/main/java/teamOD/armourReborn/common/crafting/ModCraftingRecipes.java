package teamOD.armourReborn.common.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.List;

public final class ModCraftingRecipes {
	
	public static HashMap <String, MeltingRecipe> meltingRecipes ;
	
	public static void init () {
		addMeltingRecipes () ;
	}
	
	public static void addMeltingRecipes () {
		meltingRecipes = new HashMap <String, MeltingRecipe> () ;
	}
	
	public static void addOreDictMeltingRecipes (Fluid fluid, String name) {
		OreDictionary.getOres(name) ;
	}
}
