package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.client.core.gui.book.BookEntriesRegistry;
import teamOD.armourReborn.client.core.gui.book.BookEntry;
import teamOD.armourReborn.client.core.gui.button.GuiModButton;
import teamOD.armourReborn.client.core.gui.button.GuiTextButton;
import teamOD.armourReborn.common.lib.LibMisc;

public class GuiDocumentation extends GuiScreen {
	
	public static GuiDocumentation currentBook = new GuiDocumentation () ;
	
	public static final ResourceLocation texture = new ResourceLocation (LibMisc.MOD_ID, "textures/gui/guiBook.png") ;
	
	public ResourceLocation customTexture ;
	
	public final int guiWidth = 146 ;
	public final int guiHeight = 180 ;
	
	public int left, top ;
	public GuiButton nextButton, prevButton ;
	
	public int ticksElapsed = 0 ;
	
	@Override
	public final void initGui() {
		super.initGui() ;
		
		left = this.width / 2 - guiWidth / 2 ;
		top = this.height / 2 - guiHeight / 2 ;
		
		onInitGui();
	}
	
	public void onInitGui () {
		buttonList.clear(); 
		
		if (isMainPage()) {
			int startx = left + 15 ;
			int starty = top + 15 ;
			
			int i = 0 ;
			
			Iterable<BookEntry> entries = BookEntriesRegistry.getRegisteredEntries() ;
			
			for (BookEntry entry : entries) {
				buttonList.add(new GuiTextButton(i, startx, starty + (10*i), 110, 10, entry.buttonName, entry)) ;
				
				i++ ;
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1F, 1F, 1F, 1F) ;
		
		ResourceLocation bookTexture = (customTexture == null) ? texture : customTexture ;
		
		mc.renderEngine.bindTexture(bookTexture) ;
		this.drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight) ;
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton instanceof GuiModButton) {
			BookEntry entry = ((GuiModButton) par1GuiButton).getEntry() ;
			
			mc.displayGuiScreen( new GuiDocumentationEntry (this, entry) );
		}
	}
	
	@Override
	public void updateScreen() {
		if(GuiScreen.isShiftKeyDown())
			return;

		ticksElapsed = (++ticksElapsed == 20) ? 0 : ticksElapsed;
	}
	
	@Override
	public boolean doesGuiPauseGame () {
		return false ;
	}
	
	public boolean isMainPage () {
		return true ;
	}
	
	public int getLeft () {
		return left ;
	}
	
	public int getTop () {
		return top ;
	}

}
