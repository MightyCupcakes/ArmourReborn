package teamOD.armourReborn.client.core.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
		
		GlStateManager.popMatrix() ;
		GlStateManager.popAttrib() ;
	}
	
	private void renderItem (TileForgeAnvil te) {
		ItemStack stack = te.getStack();
		
		if (stack != null) {
			GlStateManager.pushMatrix() ;
			
			RenderHelper.enableStandardItemLighting() ;
			GlStateManager.enableLighting() ;
			
			float angle = (System.currentTimeMillis() / 200) % 360;
	
			GlStateManager.color(1F, 1F, 1F, 1F);
			GlStateManager.translate(0.5f, 0.95f + (Math.sin(angle)) * 0.01F, 0.5f) ;
			GlStateManager.scale(0.2f, 0.2f, 0.2f) ;
			
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE) ;
			
			GlStateManager.popMatrix() ;
		} 
	}

}
