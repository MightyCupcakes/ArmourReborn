package teamOD.armourReborn.client.core.gui.book;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;

public class BookPage {
	
	public String unlocalizedName;
	public ResourceLocation texture ;
	
	public BookPage (String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
		this.texture = null ;
	}
	
	public void renderPage (GuiDocumentation parent, int mx, int my) {
		
	}
	
	public void renderItem (double xPos, double yPos, ItemStack stack, boolean renderStackSize) {
		RenderItem render = Minecraft.getMinecraft().getRenderItem();

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableDepth();
		GlStateManager.pushMatrix();
		GlStateManager.translate(xPos, yPos, 0);
		render.renderItemAndEffectIntoGUI(stack, 0, 0);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, stack, 0, 0, (renderStackSize) ? null : "");
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();
		
		GlStateManager.disableLighting();
	}
	
	public void renderItem (double xPos, double yPos, ItemStack stack) {
		renderItem (xPos, yPos, stack, false) ;
	}
	
	public void renderItemOnGrid (GuiDocumentation parent, int x, int y, ItemStack stack) {
		if (stack == null || stack.getItem() == null) {
			return ;
		}
		
		int xPos = parent.getLeft() + x * 22 + 17 + (y == 0  && x == 3 ? 10 : 0);
		int yPos = parent.getTop() + y * 22 + 56 - (y == 0 ? 7 : 0);
		
		this.renderItem(xPos, yPos, stack, false) ;
	}
	
	public ResourceLocation getTexture () {
		return texture ;
	}
	
	public void updateScreen(GuiDocumentation parent) {
		// NO OP
	}
}
