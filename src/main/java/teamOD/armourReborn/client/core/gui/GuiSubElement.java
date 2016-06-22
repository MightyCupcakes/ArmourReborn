package teamOD.armourReborn.client.core.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class GuiSubElement {
	
	public static final int defaultTexW = 256 ; // Minecraft default texture file image size = 256 px by 256 px
	public static final int defaultTexH = 256 ;
	
	public final int x ;
	public final int y ;
	public final int w ;
	public final int h ;

	public int texW ;
	public int texH ;
	
	public GuiSubElement (int x, int y, int w, int h) {
		this.x = x ;
		this.y = y ;
		this.w = w ;
		this.h = h ;
		
		texW = defaultTexW ;
		texH = defaultTexH ;
	}
	
	public void drawElement (int xPos, int yPos) {
		GuiScreen.drawModalRectWithCustomSizedTexture(xPos, yPos, x, y, w, h, texW, texH);
	}
}
