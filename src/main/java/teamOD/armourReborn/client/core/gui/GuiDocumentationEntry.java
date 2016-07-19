package teamOD.armourReborn.client.core.gui;

import teamOD.armourReborn.client.core.gui.book.BookEntry;
import teamOD.armourReborn.client.core.gui.book.BookPage;

public class GuiDocumentationEntry extends GuiDocumentation {
	
	private BookEntry entry ;
	private GuiDocumentation parent ;
	private int page = 0 ;
	
	public GuiDocumentationEntry (GuiDocumentation parent, BookEntry entry) {
		this.entry = entry ;
		this.parent = parent ;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {			
		BookPage thisPage = entry.pages.get(page) ;
		
		if (thisPage.getTexture() != null) {
			this.customTexture = thisPage.getTexture() ;
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		thisPage.renderPage(this) ;
	}
	
	@Override
	public boolean isMainPage () {
		return false ;
	}
}
