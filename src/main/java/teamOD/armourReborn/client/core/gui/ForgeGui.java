package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;

public class ForgeGui extends GuiContainer {
	
	private TileForgeMaster forge ;

	public ForgeGui(Container inventorySlotsIn, TileForgeMaster forge) {
		super(inventorySlotsIn) ;
		
		this.forge = forge ;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

}
