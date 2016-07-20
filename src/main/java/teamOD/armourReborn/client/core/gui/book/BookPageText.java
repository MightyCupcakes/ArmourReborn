package teamOD.armourReborn.client.core.gui.book;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;

public class BookPageText extends BookPage {
	
	public static final int paragraphSize = 10 ;
	
	public String title ;
	public boolean customTitle ;

	public BookPageText(String unlocalizedName) {
		this(unlocalizedName, false);
	}
	
	public BookPageText(String unlocalizedName, boolean customTitle) {
		super(unlocalizedName);
		this.customTitle = customTitle ;
	}
	
	@Override
	public void renderPage (GuiDocumentation parent, int mx, int my) {
		
		if (customTitle) {
			this.title = this.unlocalizedName + ".title" ;
		}
		
		BookPageText.renderText (parent.getLeft() + 15, parent.getTop() + 15, title, this.unlocalizedName);
	}
	
	public static void renderText (int x, int y, String title, String unlocalizedName) {
		FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
		boolean unicode = font.getUnicodeFlag();
		font.setUnicodeFlag(true);
		
		String text ;
		
		if (title != null) {
			text = I18n.format(title) ;
			
			font.drawStringWithShadow(text, x, y, 0) ;
			
			y += paragraphSize * 2 ;
		}
		
		text = I18n.format(unlocalizedName) ;
		String[] textEntries = text.split("<br>");
		
		List<List<String>> lines = Lists.newLinkedList() ;
		
		for (String string : textEntries) {
			String[] words = string.split(" ") ;
			
			List<String> thisLine = Lists.newLinkedList() ;
			lines.add(thisLine) ;
			int length = 0 ;
			
			for (String s: words) {
				
				if (length + s.length() + 1 <= 30) {
					thisLine.add(s) ;
					
					length += s.length() + 1 ;
				} else {
					thisLine = Lists.newLinkedList() ;
					lines.add(thisLine) ;
					
					thisLine.add(s) ;
					
					length = s.length() + 1;
				}
			}
			
			lines.add(Lists.<String>newLinkedList()) ;
		}
		
		for (Iterable<String> line : lines) {
			
			int xi = x ;
			
			for (String s: line) {
				font.drawString(s, xi, y, 0) ;
				
				xi += font.getStringWidth(s) + 4 ;
			}
			
			y += paragraphSize ;
			
		}
		
		font.setUnicodeFlag(unicode);
	}
	
	public void setTitle (String title) {
		this.title = title ;
	}

}
