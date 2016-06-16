package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;

@SideOnly (Side.CLIENT)
public class ForgeGui extends GuiContainer {
	
	private TileForgeMaster forge ;

	public ForgeGui(Container inventorySlotsIn, TileForgeMaster forge) {
		super(inventorySlotsIn) ;
		
		this.forge = forge ;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY) ;
		
	}

}
