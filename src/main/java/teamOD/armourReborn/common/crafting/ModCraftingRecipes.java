package teamOD.armourReborn.common.crafting;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.fluids.FluidMod;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibMisc;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class ModCraftingRecipes {
	
	private static final ImmutableList <String> suffixes = ImmutableList.of ("ingot", "block", "ore") ;
	
	public static HashMap <String, FluidStack> meltingRecipes = new HashMap <String, FluidStack> () ;
	public static HashMap <Integer, String> oreIDs = new HashMap <Integer, String> () ;
	public static HashMap <FluidMod, ItemStack[]> castingRecipes = new HashMap <FluidMod, ItemStack[]> () ;
	
	/**
	 * The identifier string for armormaterials in this hashmap is registered as : material + armourType
	 */
	public static HashMap <String, ArmorMaterial> armourMaterials = new HashMap <String, ArmorMaterial> () ;
	
	public static List <AlloyRecipes> alloyRecipes = Lists.newLinkedList() ;
	
	public static void init () {
		addMeltingRecipes () ;
		addForgeRecipes () ;
		addCastingRecipes () ;
		addAlloyRecipes () ;
		addArmourRecipes () ;
	}
	
	private static void addCastingRecipes () {
		registerCastingRecipe (ModFluids.gold,
				new ItemStack (Items.gold_ingot, 1, 0)
				) ;
		
		registerCastingRecipe (ModFluids.steel, 
				new ItemStack (ModItems.MATERIALS, 1, 0)
				) ;
		
		registerCastingRecipe (ModFluids.aluminium, 
				new ItemStack (ModItems.MATERIALS, 1, 1)
				) ;
		
		registerCastingRecipe (ModFluids.copper, 
				new ItemStack (ModItems.MATERIALS, 1, 2)
				) ;
		
		registerCastingRecipe (ModFluids.iron, 
				new ItemStack(Items.iron_ingot, 1, 0), 
				new ItemStack(Items.iron_boots, 4, 0),
				new ItemStack(Items.iron_leggings, 7, 0),
				new ItemStack(Items.iron_chestplate, 8, 0),
				new ItemStack(Items.iron_helmet, 5, 0)
				) ;	
		
	}
	
	
	private static void addAlloyRecipes () {
		registerAlloyRecipe (new FluidStack (ModFluids.steel, 1), new FluidStack (ModFluids.iron, 2), new FluidStack (ModFluids.coal, 1)) ;
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
		
		addMeltingRecipe ("Iron", ModFluids.iron) ;
		addMeltingRecipe ("Steel", ModFluids.steel) ;
		addMeltingRecipe ("Aluminium", ModFluids.aluminium) ;
		addMeltingRecipe ("Copper", ModFluids.copper) ;
		addMeltingRecipe ("Gold", ModFluids.gold) ;
		addMeltingRecipe ("Coal", ModFluids.coal) ;
	}
	
	private static void addArmourRecipes () {
		
		Iterable<MaterialsMod> materials = ModMaterials.materialsRegistry.values() ;
		
		// Create ArmorMaterial class for each material first before registering armour to the game
		for (String key : LibItemStats.armourTypesStats.keySet()) {
			
			for (MaterialsMod material : materials) {
				
				String name = material.getIdentifier() + key ; // i.e "ironplate", "ironchain", "ironleather"
				String textureName = LibMisc.PREFIX_MOD + ":" + name ;
				
				int durability = (int) (material.getBaseDurabilityMultiplier() + LibItemStats.armourTypesStats.get(key)[1]) ;
				int[] reductionAmounts = new int[4] ;
				
				for (int i = 0; i < reductionAmounts.length; i ++) {
					reductionAmounts[i] = (int) (material.getBaseArmourValue()[i] * LibItemStats.armourTypesStats.get(key)[0]) ;
				}
				
				ArmorMaterial mat = EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, 1, SoundEvents.item_armor_equip_generic) ;
				armourMaterials.put(mat.getName(), mat) ;
			}
		}
		
		// Register armours to the gameregistry
		ModItems.registerArmours() ;
		
		// TODO ADD ARMOUR CRAFTING RECIPES HERE
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
	 * Adds the alloy to the registry
	 * @param output	the output of this recipe (i.e the final alloy). The amount received is specified in the itemstack in ingots
	 * @param input		The mix of metals needed to form this alloy. Again the amount needed for each metal is specified in the itemstack in ingots.
	 */
	private static AlloyRecipes registerAlloyRecipe (FluidStack output, FluidStack... input) {
		
		AlloyRecipes alloy = new AlloyRecipes (output, input) ;
		alloyRecipes.add(alloy) ;
		
		return alloy ;
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
	
	public static AlloyRecipes getAlloyRecipeFromFluid (List<FluidStack> fluids) {
		for (AlloyRecipes recipes : alloyRecipes) {
			if (recipes.matches(fluids)) {
				return recipes ;
			}
		}
		
		return null ;
	}

	private static void addOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
	}
	
	private static void addShapelessOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
	}
}
