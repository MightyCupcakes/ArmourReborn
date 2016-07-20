package teamOD.armourReborn.client.core.gui.book;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;

public class BookPagePicture extends BookPage {
	
	public ResourceLocation resource ;
	public int imageWidth, imageHeight ;

	public BookPagePicture(String unlocalizedName, ResourceLocation resource) {
		super(unlocalizedName);
		
		this.resource = resource ;
		this.imageWidth = 112 ;
		this.imageHeight = 71 ;
	}
	
	public void setImageSize (int width, int height) {
		this.imageWidth = width ;
		this.imageHeight = height ;
	}
	
	@Override
	public void renderPage (GuiDocumentation parent, int mx, int my) {
		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(resource);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		((GuiScreen) parent).drawTexturedModalRect(parent.getLeft() + 15, parent.getTop() + 15, 0, 0, imageWidth, imageHeight);
		GlStateManager.disableBlend();

		int x = parent.getLeft() + 15;
		int y = parent.getTop() + 90 ;
		
		BookPageText.renderText(x, y, "", this.unlocalizedName) ;
	}
}
