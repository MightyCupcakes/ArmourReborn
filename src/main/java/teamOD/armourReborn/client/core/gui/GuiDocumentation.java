package teamOD.armourReborn.client.core.gui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.common.lib.LibMisc;

public class GuiDocumentation extends GuiScreen {
	
	public static GuiDocumentation currentBook = new GuiDocumentation () ;
	
	public static final ResourceLocation texture = new ResourceLocation (LibMisc.MOD_ID, "textures/gui/guiBook.png") ;
	
	public final int guiWidth = 146 ;
	public final int guiHeight = 180 ;
	
	public int left, top ;
	
	@Override
	public final void initGui() {
		super.initGui() ;
		
		left = this.width / 2 - guiWidth / 2 ;
		top = this.height / 2 - guiHeight / 2 ;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1F, 1F, 1F, 1F) ;
		
		mc.renderEngine.bindTexture(texture) ;
		this.drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight) ;
		mc.fontRendererObj.drawString("hi", left + 10, top + 10, 0x666666) ;
	}
	
	@Override
	public boolean doesGuiPauseGame () {
		return false ;
	}

}
