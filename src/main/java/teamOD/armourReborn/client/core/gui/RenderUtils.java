package teamOD.armourReborn.client.core.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
