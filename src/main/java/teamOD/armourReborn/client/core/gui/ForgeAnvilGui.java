package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.block.tile.TileForgeAnvil;
import teamOD.armourReborn.common.block.tile.inventory.ContainerMod;
import teamOD.armourReborn.common.lib.LibMisc;

@SideOnly (Side.CLIENT)
public class ForgeAnvilGui extends GuiContainer {
	
	public static final ResourceLocation BACKGROUND = new ResourceLocation (LibMisc.MOD_ID + ":textures/gui/guiForgecontroller.png") ;
	
	private TileForgeAnvil anvil ;
	private ContainerMod anvilContainer ;

	public ForgeAnvilGui(ContainerMod inventorySlotsIn, TileForgeAnvil tile) {
		super(inventorySlotsIn);
		
		anvil = tile ;
		anvilContainer = inventorySlotsIn ;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

}
