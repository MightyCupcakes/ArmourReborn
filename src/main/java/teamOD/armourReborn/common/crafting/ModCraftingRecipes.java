package teamOD.armourReborn.common.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.lib.LibItemStats;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;

public final class ModCraftingRecipes {
	
	private static final ImmutableList <String> suffixes = ImmutableList.of ("ingot", "block", "ore") ;
	public static HashMap <String, FluidStack> meltingRecipes ;
	public static HashMap <Integer, String> oreIDs ;
	
	public static void init () {
		addMeltingRecipes () ;
		addArmourRecipes () ;
		addForgeRecipes () ;
	}
	
	private static void addArmourRecipes () {
		
	}
	
	private static void addForgeRecipes () {
		addOreDictRecipe(new ItemStack(ModBlocks.forgeBlocks,1,0),
				"III", "I I", "III", 'I', "ingotIron");
	}
	
	private static void addMeltingRecipes () {
		meltingRecipes = new HashMap <String, FluidStack> () ;
		oreIDs = new HashMap <Integer, String> () ;
		
		addMeltingRecipe ("Iron", ModFluids.iron) ;
		addMeltingRecipe ("Steel", ModFluids.steel) ;
		addMeltingRecipe ("Aluminium", ModFluids.aluminium) ;
		addMeltingRecipe ("Copper", ModFluids.copper) ;
	}
	
	private static void addMeltingRecipe (String material, Fluid output) {

		for (String suffix: suffixes) {
			meltingRecipes.put (suffix + material, new FluidStack (output, LibItemStats.getValue(suffix)) ) ;
			oreIDs.put (OreDictionary.getOreID(suffix + material), suffix + material) ;
		}
	}
	
	public static FluidStack findRecipe (ItemStack item) {
		int[] ids = OreDictionary.getOreIDs(item) ;
		
		String oreName = null;
		
		for (int id: ids) {
			if (oreIDs.containsKey(id)) {
				oreName = oreIDs.get(id) ;
				break ;
			}
		}
		
		if (oreName !=null) {
			return meltingRecipes.get(oreName) ;
		} 
		
		return null ;
	}

	private static void addOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
	}
}
