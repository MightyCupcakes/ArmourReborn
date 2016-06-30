package teamOD.armourReborn.client.core.gui;

import java.util.Iterator;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;
import teamOD.armourReborn.common.block.tile.inventory.ContainerMod;
import teamOD.armourReborn.common.lib.LibMisc;

@SideOnly (Side.CLIENT)
public class ForgeGui extends GuiContainer {
	
	public static final ResourceLocation BACKGROUND = new ResourceLocation (LibMisc.MOD_ID + ":textures/gui/guiForgecontroller.png") ;
	
	public GuiSubElement progressBar = new GuiSubElement (176, 150, 3, 16) ;
	
	private TileForgeMaster forge ;
	private ContainerMod forgeContainer ; 

	public ForgeGui(ContainerMod inventorySlotsIn, TileForgeMaster forge) {
		super(inventorySlotsIn) ;
		
		this.forge = forge ;
		this.forgeContainer = inventorySlotsIn ;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f) ;
		this.mc.getTextureManager().bindTexture(BACKGROUND) ;
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize) ;
		
		if (forge.getHeater().fluid != null && forge.getHeater().fluid.amount > 0) {
			int x = 152 + this.guiLeft ;
			int y = 65 + this.guiTop;
			int w = 12 ;
			int h = 52 ;
			
			h = (int) (h * (float) forge.getHeater().fluid.amount / forge.getHeater().capacity) ;
			
			RenderUtils.renderTiledFluid(x, y - h, w, h, this.zLevel, forge.getHeater().fluid);
		} 
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = this.forge.getDisplayName().getFormattedText() ;
		
		//this.fontRendererObj.drawString(title, 88 - this.fontRendererObj.getStringWidth(title), 6, 0x404040) ;
		RenderHelper.disableStandardItemLighting();
		Iterator <Slot> iterator = forgeContainer.internalInventory.iterator();
		
		this.mc.getTextureManager().bindTexture(BACKGROUND) ;
		
		while (iterator.hasNext()) {
			Slot slot = iterator.next() ;
			
			if (slot.getHasStack()) {
				float progress = forge.getMeltingProgress(slot.getSlotIndex()) ;
				
				int height = 1 + Math.round(progress * (progressBar.h - 1)) ;
				int x = slot.xDisplayPosition + 18  ;
				int y = slot.yDisplayPosition + progressBar.h - height ;

				GuiScreen.drawModalRectWithCustomSizedTexture(x, y, progressBar.x, progressBar.y + progressBar.h - height, progressBar.w, progressBar.h, 256, 256);
			}
		}
	}

}
