package teamOD.armourReborn.client.core.models;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibMisc;

import static teamOD.armourReborn.common.item.ModItems.*;


public class ModelHandler {
	
	public static void registerModels () {
		registerResources () ;
	}
	
	private static void registerStandardItems () {
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

}
