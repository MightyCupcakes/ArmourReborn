package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.GuiButton;
import teamOD.armourReborn.client.core.gui.book.BookEntry;
import teamOD.armourReborn.client.core.gui.book.BookPage;
import teamOD.armourReborn.client.core.gui.button.GuiPageButton;

public class GuiDocumentationEntry extends GuiDocumentation {
	
	private BookEntry entry ;
	private GuiDocumentation parent ;
	private int page = 0 ;
	
	public GuiDocumentationEntry (GuiDocumentation parent, BookEntry entry) {
		this.entry = entry ;
		this.parent = parent ;
	}
	
	@Override
	public void onInitGui () {
		super.onInitGui();
		
		buttonList.add(nextButton = new GuiPageButton (322, getLeft() + 110, getTop() + 160, entry, true)) ;
		buttonList.add(prevButton = new GuiPageButton (323, getLeft() + 15, getTop() + 160, entry, false)) ;
		
		updatePageButtons() ;
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
	protected void actionPerformed(GuiButton par1GuiButton) {
		
		switch (par1GuiButton.id) {
		case 322:
			page ++ ;
			break ;
			
		case 323:
			page -- ;
			break ;
		}
		
		updatePageButtons();
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		
		BookPage thisPage = entry.pages.get(page) ;
		thisPage.updateScreen(this);
	}
	
	public void updatePageButtons() {
		prevButton.enabled = (page != 0);
		
		nextButton.enabled = (page + 1 < entry.pages.size());
	}
	
	@Override
	public boolean isMainPage () {
		return false ;
	}
}
