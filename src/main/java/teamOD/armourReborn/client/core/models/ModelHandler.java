package teamOD.armourReborn.client.core.models;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.block.tile.TileForgeAnvil;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibMisc;

import static teamOD.armourReborn.common.item.ModItems.*;

import java.util.Map;


public final class ModelHandler {
	
	public static void registerModels () {
		
		registerStandardItems () ;
		registerResources () ;
		registerFluids () ;
		registerBlocks () ;
	}
	
	private static void registerStandardItems () {

		// Armour
		for (Map <EntityEquipmentSlot, Item> armour : ModItems.getAllModArmour()) {

			for (Item armourPiece : armour.values()) {
				registerItemModel (armourPiece) ;
			}
		}
		
		registerItemModel (ModItems.ModBook) ;
	}
	
	private static void registerBlocks () {
		registerBlockModels (ModBlocks.forgeMaster) ;
		registerBlockModels (ModBlocks.forgeBlocks) ;
		registerBlockModels (ModBlocks.forgeHeater) ;
		registerBlockModels (ModBlocks.forgeAnvil) ;
	}
	
	private static void registerFluids () {
		registerFluidsModel (ModFluids.iron) ;
		registerFluidsModel (ModFluids.steel) ;
		registerFluidsModel (ModFluids.aluminium) ;
		registerFluidsModel (ModFluids.copper) ;
		registerFluidsModel (ModFluids.gold) ;
		registerFluidsModel (ModFluids.coal) ;
		registerFluidsModel (ModFluids.aluAlloy) ;
		registerFluidsModel (ModFluids.silicate) ;
		registerFluidsModel (ModFluids.fiendFyre) ;
	}
	
	private static void registerResources () {
		Item item = MATERIALS ;
		
		for (int i = 0; i < LibItemNames.MATERIALS_NAMES.length; i++) {
			 String name = LibMisc.MOD_ID + ":" + LibItemNames.MATERIALS_NAMES[i];
			 ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name, "inventory"));
		}
		 
		item = Item.getItemFromBlock(ModBlocks.oresMaterials) ;
		 
		for (int i = 0; i < LibItemNames.ORE_MATERIALS_NAMES.length; i ++) {
			String name = ForgeRegistries.BLOCKS.getKey(ModBlocks.oresMaterials).toString() ;
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name, "inventory_" + LibItemNames.ORE_MATERIALS_NAMES[i]));	
		}
		
		item = ModItems.MOD_MODIFIERS_MATERIALS ;
		
		for (int i = 0; i < LibItemNames.MODIFIERS_MATERIALS_NAMES.length; i++) {
			 String name = LibMisc.MOD_ID + ":" + LibItemNames.MODIFIERS_MATERIALS_NAMES[i];
			 ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name, "inventory"));
		}
		
		item = ModItems.COPPER_CASTS ;

		for (int i = 0; i < LibItemNames.ANVIL_CAST_NAMES.length; i++) {
			String name = LibMisc.MOD_ID + ":" + LibItemNames.ANVIL_CAST_NAMES[i];
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name, "inventory"));
		}
	}
	
	public static void registerItemModel (Item item, int meta) {
		ResourceLocation location = item.getRegistryName() ;
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation (location, "inventory"));
		
	}
	
	public static void registerItemModel (Item item) {
		registerItemModel (item, 0) ;
	}
	
	private static void registerFluidsModel (Fluid fluid) {
		if (fluid == null) return ;
		
		Block block = fluid.getBlock();
		Item item = Item.getItemFromBlock(block) ;
		FluidMeshDefinition mapper = new FluidMeshDefinition (fluid) ;
		
		ModelLoader.setCustomStateMapper(block, mapper) ;
		
		ModelBakery.registerItemVariants(item) ;
		ModelLoader.setCustomMeshDefinition(item, mapper) ;
	}
	
	
	public static class FluidMeshDefinition extends StateMapperBase implements ItemMeshDefinition {
		
		public final Fluid fluid ;
		public final ModelResourceLocation location ;
		
		public FluidMeshDefinition (Fluid fluid) {
			this.fluid = fluid ;
			this.location = new ModelResourceLocation(new ResourceLocation (LibMisc.MOD_ID, "molten_metal"), fluid.getName() );
		}
		
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return location;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
			return location ;
		}
		
	}
	
	public static void registerBlockModels (Block block) {
		registerItemModel (Item.getItemFromBlock(block)) ;
	}

}
