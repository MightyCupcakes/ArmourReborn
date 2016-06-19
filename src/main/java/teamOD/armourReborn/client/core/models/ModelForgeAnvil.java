package teamOD.armourReborn.client.core.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import teamOD.armourReborn.common.block.tile.TileForgeAnvil;
import teamOD.armourReborn.common.lib.LibMisc;

public class ModelForgeAnvil extends TileEntitySpecialRenderer<TileForgeAnvil> {
	
	@Override
	public void renderTileEntityAt(TileForgeAnvil te, double x, double y, double z, float partialTicks,
			int destroyStage) {
		
		GlStateManager.pushAttrib() ;
		GlStateManager.pushMatrix() ;
		
		GlStateManager.translate(x, y, z) ;
		GlStateManager.disableRescaleNormal() ;
		
		renderItem (te) ;
		
		GlStateManager.popAttrib() ;
		GlStateManager.popMatrix() ;
	}
	
	private void renderItem (TileForgeAnvil te) {
		ItemStack stack = te.getStack() ;
		
		if (stack != null) {
			RenderHelper.enableStandardItemLighting() ;
			GlStateManager.enableLighting() ;
			GlStateManager.pushMatrix() ;
			
			GlStateManager.translate(0.5f, 0.9f, 0.5f) ;
			GlStateManager.scale(0.4f, 0.4f, 0.4f) ;
			
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE) ;
			
			GlStateManager.popMatrix() ;
		}
	}

}
