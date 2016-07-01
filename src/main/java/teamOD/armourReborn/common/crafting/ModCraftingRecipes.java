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
import net.minecraftforge.oredict.ShapelessOreRecipe;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.fluids.FluidMod;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.lib.LibItemStats;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;

public final class ModCraftingRecipes {
	
	private static final ImmutableList <String> suffixes = ImmutableList.of ("ingot", "block", "ore") ;
	public static HashMap <String, FluidStack> meltingRecipes ;
	public static HashMap <Integer, String> oreIDs ;
	public static HashMap <FluidMod, ItemStack[]> castingRecipes = new HashMap <FluidMod, ItemStack[]> () ; ;
	
	public static void init () {
		addMeltingRecipes () ;
		addForgeRecipes () ;
		addCastingRecipes () ;
	}
	
	private static void addCastingRecipes () {
		registerCastingRecipe (ModFluids.iron, 
				new ItemStack(Items.iron_ingot, 1, 0), 
				new ItemStack(Items.iron_boots, 4, 0),
				new ItemStack(Items.iron_leggings, 7, 0),
				new ItemStack(Items.iron_chestplate, 8, 0),
				new ItemStack(Items.iron_helmet, 5, 0)
				) ;	
		
	}
	
	private static void addForgeRecipes () {
		addOreDictRecipe(new ItemStack(ModBlocks.forgeBlocks,5,0),
				"III", 
				"I I", 
				"III", 
				'I', "ingotIron");
		
		addOreDictRecipe(new ItemStack(ModBlocks.forgeAnvil,1,0),
				"   ",
				" B ",
				"III",
				'B', "blockIron",
				'I', "ingotIron");
		
		addOreDictRecipe(new ItemStack(ModBlocks.forgeHeater,1,0),
				"III",
				"IBI",
				"III",
				'B', new ItemStack(Items.bucket),
				'I', "ingotIron");
		
		addOreDictRecipe(new ItemStack(ModBlocks.forgeMaster,1,0),
				"III",
				"IRI",
				"III",
				'R', "dustRedstone",
				'I', "ingotIron");
	}
	
	private static void addMeltingRecipes () {
		meltingRecipes = new HashMap <String, FluidStack> () ;
		oreIDs = new HashMap <Integer, String> () ;
		
		addMeltingRecipe ("Iron", ModFluids.iron) ;
		addMeltingRecipe ("Steel", ModFluids.steel) ;
		addMeltingRecipe ("Aluminium", ModFluids.aluminium) ;
		addMeltingRecipe ("Copper", ModFluids.copper) ;
		addMeltingRecipe ("Gold", ModFluids.gold) ;
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
	
	/**
	 * For each molten metal, register ALL possible items it can be casted into in the anvil
	 * For the itemstack, the amount represents the AMOUNT OF INGOTS required for this item
	 * 
	 */
	private static void registerCastingRecipe (FluidMod fluid, ItemStack... items) {
		castingRecipes.put(fluid, items) ;
	}
	
	/**
	 * Given a molten metal, returns all possible Items that is registered to this fluid.
	 * The amount represented in the ItemStack is the amount of ingots needed to make this item in the anvil
	 * @param fluid
	 * @return ItemStack[] representing all possible items castable by this fluid
	 */
	public static ItemStack[] getCastingRecipe (Fluid fluid) {
		return castingRecipes.get(fluid) ;
	}

	private static void addOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
	}
	
	private static void addShapelessOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
	}
}
