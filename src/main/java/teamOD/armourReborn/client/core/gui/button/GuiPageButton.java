package teamOD.armourReborn.client.core.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;
import teamOD.armourReborn.client.core.gui.book.BookEntry;

public class GuiPageButton extends GuiModButton {
	
	public final boolean next ;

	public GuiPageButton(int buttonId, int x, int y, BookEntry entry, boolean next) {
		super(buttonId, x, y, 18, 10, "", entry);
		
		this.next = next ;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (enabled) {
			hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			int k = getHoverState(hovered);
			
			mc.renderEngine.bindTexture(GuiDocumentation.texture);
			GlStateManager.color(1F, 1F, 1F, 1F);
			drawTexturedModalRect(xPosition, yPosition, k == 2 ? 18 : 0, next ? 180 : 190, 18, 10);
		}
	}

}
