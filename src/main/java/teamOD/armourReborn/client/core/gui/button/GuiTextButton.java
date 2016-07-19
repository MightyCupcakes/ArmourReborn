package teamOD.armourReborn.client.core.gui.button;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import teamOD.armourReborn.client.core.gui.book.BookEntry;

public class GuiTextButton extends GuiModButton {

	public GuiTextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, BookEntry entry) {
		super(buttonId, x, y, widthIn, heightIn, buttonText, entry);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		
		hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
		int k = getHoverState(hovered);
		
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GlStateManager.disableAlpha();

		int maxalpha = 0x22;
		drawRect(xPosition - 5, yPosition, (xPosition - 5), yPosition + height, 0x22FFFFFF);
		
		GlStateManager.enableAlpha();
		
		int colour = (k == 2) ? 0xf2e9d8 : 0 ;
		
		boolean unicode = mc.fontRendererObj.getUnicodeFlag();
		mc.fontRendererObj.setUnicodeFlag(true);
		mc.fontRendererObj.drawString(displayString, xPosition , yPosition + (height - 8) / 2, colour);
		mc.fontRendererObj.setUnicodeFlag(unicode);
		
		GlStateManager.popMatrix();
	}
}
