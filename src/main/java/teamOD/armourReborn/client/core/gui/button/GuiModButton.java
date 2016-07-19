package teamOD.armourReborn.client.core.gui.button;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import teamOD.armourReborn.client.core.gui.book.BookEntry;

public class GuiModButton extends GuiButton {
	
	private BookEntry linkTo ;

	public GuiModButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, BookEntry entry) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		linkTo = entry ;

	}
	
	public BookEntry getEntry () {
		return linkTo ;
	}

}
