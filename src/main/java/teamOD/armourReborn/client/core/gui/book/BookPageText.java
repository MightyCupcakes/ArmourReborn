package teamOD.armourReborn.client.core.gui.book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;

public class BookPageText extends BookPage {
	
	public static final int paragraphSize = 10 ;
	
	public String title ;

	public BookPageText(String unlocalizedName) {
		super(unlocalizedName);
	}
	
	@Override
	public void renderPage (GuiDocumentation parent) {
		renderText (parent.getLeft() + 15, parent.getTop() + 15);
	}
	
	public void renderText (int x, int y) {
		FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
		boolean unicode = font.getUnicodeFlag();
		font.setUnicodeFlag(true);
		
		String text = I18n.format(this.unlocalizedName) ;
		String[] textEntries = text.split("<br>");
		
		for (String string : textEntries) {
			font.drawString(string, x, y, 0) ;
			
			y += this.paragraphSize ;
		}
		
		font.setUnicodeFlag(unicode);
	}
	
	public void setTitle (String title) {
		this.title = title ;
	}

}
