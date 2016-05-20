package teamOD.armourReborn.client.core.models;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import static teamOD.armourReborn.common.item.ModItems.*;


public class ModelHandler {
	
	public static void registerModels () {
		registerStandardItems () ;
	}
	
	public static void registerStandardItems () {
		registerItemModel (STEEL_INGOT) ;
	}
	
	public static void registerItemModel (Item item, int meta) {
		ResourceLocation location = item.getRegistryName() ;
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation (location, "inventory"));
		
	}
	
	public static void registerItemModel (Item item) {
		registerItemModel (item, 0) ;
	}

}
