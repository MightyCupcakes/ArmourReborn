package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;
import teamOD.armourReborn.common.lib.LibMisc;

@SideOnly (Side.CLIENT)
public class ForgeGui extends GuiContainer {
	
	public static final ResourceLocation BACKGROUND = new ResourceLocation (LibMisc.MOD_ID + ":textures/gui/basegui.png") ;
	
	private TileForgeMaster forge ;

	public ForgeGui(Container inventorySlotsIn, TileForgeMaster forge) {
		super(inventorySlotsIn) ;
		
		this.forge = forge ;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f) ;
		this.mc.getTextureManager().bindTexture(BACKGROUND) ;
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize) ;
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = this.forge.getDisplayName().getFormattedText() ;
		
		this.fontRendererObj.drawString(title, 88 - this.fontRendererObj.getStringWidth(title), 6, 0x404040) ;
	}

}
