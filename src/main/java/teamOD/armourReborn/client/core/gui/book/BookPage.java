package teamOD.armourReborn.client.core.gui.book;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class BookPage {
	
	public String unlocalizedName;
	public GuiScreen parent ;
	
	public BookPage (GuiScreen parent, String unlocalizedName) {
		this.parent = parent ;
		this.unlocalizedName = unlocalizedName;
	}
	
	public void renderPage () {
		
	}
	
	public void renderItem (double xPos, double yPos, ItemStack stack) {
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
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, stack, 0, 0, "");
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();
		
		GlStateManager.disableLighting();
	}
	
	public void renderItemOnGrid (int x, int y, ItemStack stack) {
		if (stack == null || stack.getItem() == null) {
			return ;
		}
		
		int xPos = 0 ;
		int yPos = 0 ;
		
		this.renderItem(xPos, yPos, stack) ;
	}
}
