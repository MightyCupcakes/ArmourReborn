package teamOD.armourReborn.common.crafting;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
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
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibUtil;

public final class ModCraftingRecipes {
	
	private static final ImmutableList <String> preffixes = ImmutableList.of ("ingot", "block", "ore") ;
	
	private static Map <String, FluidStack> meltingRecipes = Maps.newHashMap() ;
	private static Map <Integer, String> oreIDs = Maps.newHashMap() ;
	private static Map <FluidMod, ItemStack[]> castingRecipes = Maps.newHashMap() ;
	private static Map <Item, IRecipe> ModRecipes = Maps.newHashMap() ;
	
	public static List <AlloyRecipes> alloyRecipes = Lists.newLinkedList() ;
	
	public static void init () {
		addMeltingRecipes () ;
		addForgeRecipes () ;
		addCastingRecipes () ;
		addAlloyRecipes () ;
		addArmourRecipes () ;
		addItemRecipes () ;
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
				new ItemStack(ModItems.getArmourByName("ironplate").get(EntityEquipmentSlot.HEAD), 5, 0),
				new ItemStack(ModItems.getArmourByName("ironplate").get(EntityEquipmentSlot.CHEST), 8, 0),
				new ItemStack(ModItems.getArmourByName("ironplate").get(EntityEquipmentSlot.LEGS), 7, 0),
				new ItemStack(ModItems.getArmourByName("ironplate").get(EntityEquipmentSlot.FEET), 4, 0)
				) ;	
		
		registerCastingRecipe (ModFluids.aluAlloy, 
				new ItemStack (ModItems.MATERIALS, 1, 3), 
				new ItemStack(ModItems.getArmourByName("aluminiumplate").get(EntityEquipmentSlot.HEAD), 5, 0),
				new ItemStack(ModItems.getArmourByName("aluminiumplate").get(EntityEquipmentSlot.CHEST), 8, 0),
				new ItemStack(ModItems.getArmourByName("aluminiumplate").get(EntityEquipmentSlot.LEGS), 7, 0),
				new ItemStack(ModItems.getArmourByName("aluminiumplate").get(EntityEquipmentSlot.FEET), 4, 0)
				) ;	
		
		registerCastingRecipe (ModFluids.steel, 
				new ItemStack (ModItems.MATERIALS, 1, 0), 
				new ItemStack(ModItems.getArmourByName("steelplate").get(EntityEquipmentSlot.HEAD), 5, 0),
				new ItemStack(ModItems.getArmourByName("steelplate").get(EntityEquipmentSlot.CHEST), 8, 0),
				new ItemStack(ModItems.getArmourByName("steelplate").get(EntityEquipmentSlot.LEGS), 7, 0),
				new ItemStack(ModItems.getArmourByName("steelplate").get(EntityEquipmentSlot.FEET), 4, 0)
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
		//Paper Leather Armour
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.HEAD)),
				"LLL", 
				"I I", 
				"   ", 
				'I', "paper",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.CHEST)),
				"L L", 
				"LIL", 
				"III", 
				'I', "paper",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.LEGS)),
				"LLL", 
				"I I", 
				"I I", 
				'I', "paper",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.FEET)),
				"   ", 
				"I I", 
				"L L", 
				'I', "paper",
				'L', "leather");
		
		//Iron Leather Armour
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("ironleather").get(EntityEquipmentSlot.HEAD)),
				"LLL", 
				"I I", 
				"   ", 
				'I', "ingotIron",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("ironleather").get(EntityEquipmentSlot.CHEST)),
				"L L", 
				"LIL", 
				"III", 
				'I', "ingotIron",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("ironleather").get(EntityEquipmentSlot.LEGS)),
				"LLL", 
				"I I", 
				"I I", 
				'I', "ingotIron",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("ironleather").get(EntityEquipmentSlot.FEET)),
				"   ", 
				"I I", 
				"L L", 
				'I', "ingotIron",
				'L', "leather");
		
		//Aluminium Leather Armour
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("aluminiumleather").get(EntityEquipmentSlot.HEAD)),
				"LLL", 
				"I I", 
				"   ", 
				'I', "ingotAluminiumAlloy",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("aluminiumleather").get(EntityEquipmentSlot.CHEST)),
				"L L", 
				"LIL", 
				"III", 
				'I', "ingotAluminiumAlloy",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("aluminiumleather").get(EntityEquipmentSlot.LEGS)),
				"LLL", 
				"I I", 
				"I I", 
				'I', "ingotAluminiumAlloy",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("aluminiumleather").get(EntityEquipmentSlot.FEET)),
				"   ", 
				"I I", 
				"L L", 
				'I', "ingotAluminiumAlloy",
				'L', "leather");
		
		//Steel Leather Armour
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("steelleather").get(EntityEquipmentSlot.HEAD)),
				"LLL", 
				"I I", 
				"   ", 
				'I', "ingotSteel",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("steelleather").get(EntityEquipmentSlot.CHEST)),
				"L L", 
				"LIL", 
				"III", 
				'I', "ingotSteel",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("steelleather").get(EntityEquipmentSlot.LEGS)),
				"LLL", 
				"I I", 
				"I I", 
				'I', "ingotSteel",
				'L', "leather");
		
		addOreDictRecipe(LibUtil.buildArmourCustomNBT(ModItems.getArmourByName("steelleather").get(EntityEquipmentSlot.FEET)),
				"   ", 
				"I I", 
				"L L", 
				'I', "ingotSteel",
				'L', "leather");
	}
	
	/**
	 * For misc items that do not fall in the above categories
	 */
	private static void addItemRecipes () {
		addShapedRecipe (new ItemStack (ModItems.MOD_MODIFIERS_MATERIALS, 1, 1),
				"SSS",
				"SAS",
				"SSS",
				'S', new ItemStack(Items.snowball, 1, 0),
				'A', new ItemStack(ModItems.MOD_MODIFIERS_MATERIALS, 1, 0)
				) ;
		
		addShapedRecipe (new ItemStack (ModItems.MOD_MODIFIERS_MATERIALS, 1, 2),
				"BBB",
				"BAB",
				"BBB",
				'B', new ItemStack(Items.blaze_powder, 1, 0),
				'A', new ItemStack(ModItems.MOD_MODIFIERS_MATERIALS, 1, 0)
				) ;
	}
	
	private static void addMeltingRecipe (String material, Fluid output) {

		for (String suffix: preffixes) {
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
	
	private static void registerLatestAddedModRecipe (ItemStack output) {
		List<IRecipe> list = CraftingManager.getInstance().getRecipeList();
		ModRecipes.put(output.getItem(), list.get(list.size() - 1)) ;
	}
	
	public static IRecipe getModRecipe (Item item) {
		
		return ModRecipes.get(item) ;
	}
	
	public static IRecipe getModRecipe (Block block) {
		
		return getModRecipe (Item.getItemFromBlock(block)) ;
	}

	private static void addOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
		registerLatestAddedModRecipe (output) ;
	}
	
	private static void addShapelessOreDictRecipe(ItemStack output, Object... recipe) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
	}
	
	private static void addShapedRecipe (ItemStack output, Object... recipe) {
		GameRegistry.addRecipe(output, recipe) ;
		registerLatestAddedModRecipe (output) ;
	}
	
}
