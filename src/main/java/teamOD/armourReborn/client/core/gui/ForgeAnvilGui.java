package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.block.tile.TileForgeAnvil;
import teamOD.armourReborn.common.block.tile.inventory.ContainerMod;
import teamOD.armourReborn.common.lib.LibMisc;

@SideOnly (Side.CLIENT)
public class ForgeAnvilGui extends GuiContainer {
	
	public static final ResourceLocation BACKGROUND = new ResourceLocation (LibMisc.MOD_ID + ":textures/gui/guiAnvil.png") ;
	
	private TileForgeAnvil anvil ;
	private ContainerMod anvilContainer ;

	public ForgeAnvilGui(ContainerMod inventorySlotsIn, TileForgeAnvil tile) {
		super(inventorySlotsIn);
		
		anvil = tile ;
		anvilContainer = inventorySlotsIn ;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f) ;
		this.mc.getTextureManager().bindTexture(BACKGROUND) ;
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize) ;
		
		FluidTankInfo tankInfo = anvil.getTankInfo(EnumFacing.NORTH)[0] ;
		
		if (tankInfo.fluid != null && tankInfo.fluid.amount > 0) {
			int x = 152 + this.guiLeft ;
			int y = 65 + this.guiTop;
			int w = 12 ;
			int h = 52 ;
			
			h = (int) (h * (float) tankInfo.fluid.amount / tankInfo.capacity) ;
			
			RenderUtils.renderTiledFluid(x, y - h, w, h, this.zLevel, tankInfo.fluid);
		}		
	}

}
