package teamOD.armourReborn.client.core.models;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.FMLLog;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibMisc;

import static teamOD.armourReborn.common.item.ModItems.*;


public class ModelHandler {
	
	public static void registerModels () {
		registerResources () ;
		registerFluids () ;
	}
	
	private static void registerStandardItems () {
	}
	
	private static void registerFluids () {
		registerFluidsModel (ModFluids.iron) ;
		registerFluidsModel (ModFluids.steel) ;
	}
	
	private static void registerResources () {
		Item item = MATERIALS ;
		 for (int i = 0; i < LibItemNames.MATERIALS_NAMES.length; i++) {
			 String name = LibMisc.MOD_ID + ":" + LibItemNames.MATERIALS_NAMES[i];
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
			FMLLog log = new FMLLog () ;
			log.info("NAME OF FLUID IS : %s", fluid.getName());
			this.location = new ModelResourceLocation(LibMisc.MOD_ID.toLowerCase() + ":" + "molten_metal", fluid.getName() );
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

}
