package teamOD.armourReborn.client.core.gui.book;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;

public class BookPageRecipe extends BookPage {
	
	public final List<Item> recipes ;

	public BookPageRecipe(GuiScreen parent, String unlocalizedName, List<Item> recipes) {
		super(parent, unlocalizedName) ;
		this.recipes = recipes ;
	}
	
	@Override
	public void renderPage () {
		TextureManager renderer = Minecraft.getMinecraft().renderEngine ;
	}
}
