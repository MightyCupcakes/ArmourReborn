package teamOD.armourReborn.client.core.gui;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.block.tile.TileForgeAnvil;
import teamOD.armourReborn.common.block.tile.inventory.ContainerMod;
import teamOD.armourReborn.common.lib.LibMisc;

@SideOnly (Side.CLIENT)
public class ForgeAnvilGui extends GuiContainer {
	
	public static final ResourceLocation BACKGROUND = new ResourceLocation (LibMisc.MOD_ID + ":textures/gui/guiAnvil.png") ;
	public static final GuiSubElement FLUID_BAR = new GuiSubElement (152, 65, 12, 52) ;
	
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
		
		FluidTankInfo tankInfo = anvil.getTankInfo();
		
		if (tankInfo.fluid != null && tankInfo.fluid.amount > 0) {
			int x = FLUID_BAR.x + this.guiLeft ;
			int y = FLUID_BAR.y + this.guiTop;
			int w = FLUID_BAR.w ;
			int h = FLUID_BAR.h ;
			
			h = (int) (h * (float) tankInfo.fluid.amount / tankInfo.capacity) ;
			
			RenderUtils.renderTiledFluid(x, y - h, w, h, this.zLevel, tankInfo.fluid);
		}		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreen(mouseX, mouseY, partialTicks) ;
		drawTooltipForFluids (mouseX, mouseY) ;
	}
	
	private void drawTooltipForFluids (int mouseX, int mouseY) {
		int x = FLUID_BAR.x + this.guiLeft ;
		int y = FLUID_BAR.y + this.guiTop ;
		
		if (mouseX >= x && mouseX <= x + FLUID_BAR.w && mouseY >= y && mouseY <= y + FLUID_BAR.h) {
			FluidTankInfo tankInfo = anvil.getTankInfo();
			
			if (tankInfo.fluid != null && tankInfo.fluid.amount > 0) {
				
				RenderUtils.renderTooltip(mouseX, mouseY, ImmutableList.<String>of( formatFluidString (tankInfo.fluid) ) );
			}
		}
	}
	
	private static String formatFluidString (FluidStack fluid) {
		return fluid.getLocalizedName() + " " + fluid.amount + "mB" ;
	}

}
