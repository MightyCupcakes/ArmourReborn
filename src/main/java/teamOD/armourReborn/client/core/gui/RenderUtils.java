package teamOD.armourReborn.client.core.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;

/**
 * 
 * @author SlimeKnights
 * https://github.com/SlimeKnights/TinkersConstruct/blob/master/src/main/java/slimeknights/tconstruct/library/client/RenderUtil.java
 *
 */

public final class RenderUtils {
	public static float FLUID_OFFSET = 0.005f;

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public static void renderTooltip(int x, int y, List<String> tooltipData) {
		int color = 0x505000ff;
		int color2 = 0xf0100010;

		renderTooltip(x, y, tooltipData, color, color2);
	}
	
	public static void renderTooltip(int x, int y, List<String> tooltipData, int color, int color2) {
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		
		if(lighting) {
			RenderHelper.disableStandardItemLighting();
		}

		if (!tooltipData.isEmpty()) {
			int var5 = 0;
			int var6;
			int var7;
			FontRenderer fontRenderer = mc.fontRendererObj;
			for (var6 = 0; var6 < tooltipData.size(); ++var6) {
				var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
				if (var7 > var5)
					var5 = var7;
			}
			var6 = x + 12;
			var7 = y - 12;
			int var9 = 8;
			if (tooltipData.size() > 1)
				var9 += 2 + (tooltipData.size() - 1) * 10;
			float z = 300F;
			drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
			drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
			int var12 = (color & 0xFFFFFF) >> 1 | color & -16777216;
			drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, color, var12);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
			drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

			GlStateManager.disableDepth();
			for (int var13 = 0; var13 < tooltipData.size(); ++var13) {
				String var14 = tooltipData.get(var13);
				fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
				if (var13 == 0)
					var7 += 2;
				var7 += 10;
			}
			GlStateManager.enableDepth();
		}
		if(!lighting) {
			RenderHelper.disableStandardItemLighting();
		}
		GlStateManager.color(1F, 1F, 1F, 1F);
	}

	public static void drawGradientRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
		float var7 = (par5 >> 24 & 255) / 255F;
		float var8 = (par5 >> 16 & 255) / 255F;
		float var9 = (par5 >> 8 & 255) / 255F;
		float var10 = (par5 & 255) / 255F;
		float var11 = (par6 >> 24 & 255) / 255F;
		float var12 = (par6 >> 16 & 255) / 255F;
		float var13 = (par6 >> 8 & 255) / 255F;
		float var14 = (par6 & 255) / 255F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.getInstance();
		var15.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		var15.getBuffer().pos(par3, par2, z).color(var8, var9, var10, var7).endVertex();
		var15.getBuffer().pos(par1, par2, z).color(var8, var9, var10, var7).endVertex();
		var15.getBuffer().pos(par1, par4, z).color(var12, var13, var14, var11).endVertex();
		var15.getBuffer().pos(par3, par4, z).color(var12, var13, var14, var11).endVertex();
		var15.draw();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}


	/** Renders the given texture tiled into a GUI */
	public static void renderTiledTextureAtlas(int x, int y, int width, int height, float depth, TextureAtlasSprite sprite) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		putTiledTextureQuads(worldrenderer, x, y, width, height, depth, sprite);

		tessellator.draw();
	}

	public static void renderTiledFluid(int x, int y, int width, int height, float depth, FluidStack fluidStack) {
		TextureAtlasSprite fluidSprite = mc.getTextureMapBlocks().getAtlasSprite(fluidStack.getFluid().getStill(fluidStack).toString());
		RenderUtils.setColorRGBA(fluidStack.getFluid().getColor(fluidStack));
		renderTiledTextureAtlas(x, y, width, height, depth, fluidSprite);
	}

	/** Adds a quad to the rendering pipeline. Call startDrawingQuads beforehand. You need to call draw() yourself. */
	public static void putTiledTextureQuads(VertexBuffer renderer, int x, int y, int width, int height, float depth, TextureAtlasSprite sprite) {
		float u1 = sprite.getMinU();
		float v1 = sprite.getMinV();

		// tile vertically
		do {
			int renderHeight = Math.min(sprite.getIconHeight(), height);
			height -= renderHeight;

			float v2 = sprite.getInterpolatedV((16f * renderHeight) / (float) sprite.getIconHeight());

			// we need to draw the quads per width too
			int x2 = x;
			int width2 = width;
			// tile horizontally
			do {
				int renderWidth = Math.min(sprite.getIconWidth(), width2);
				width2 -= renderWidth;

				float u2 = sprite.getInterpolatedU((16f * renderWidth) / (float) sprite.getIconWidth());

				renderer.pos(x2, y, depth).tex(u1, v1).endVertex();
				renderer.pos(x2, y + renderHeight, depth).tex(u1, v2).endVertex();
				renderer.pos(x2 + renderWidth, y + renderHeight, depth).tex(u2, v2).endVertex();
				renderer.pos(x2 + renderWidth, y, depth).tex(u2, v1).endVertex();

				x2 += renderWidth;
			} while(width2 > 0);

			y += renderHeight;
		} while(height > 0);
	}


	public static int alpha(int c) {
		return (c >> 24) & 0xFF;
	}

	public static int red(int c) {
		return (c >> 16) & 0xFF;
	}

	public static int green(int c) {
		return (c >> 8) & 0xFF;
	}

	public static int blue(int c) {
		return (c) & 0xFF;
	}

	public static void setColorRGBA(int color) {
		float a = (float) alpha(color) / 255.0F;
		float r = (float) red(color) / 255.0F;
		float g = (float) green(color) / 255.0F;
		float b = (float) blue(color) / 255.0F;

		GlStateManager.color(r, g, b, a);
	}
}
