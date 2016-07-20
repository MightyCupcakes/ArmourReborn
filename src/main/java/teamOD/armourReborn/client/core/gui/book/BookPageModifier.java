package teamOD.armourReborn.client.core.gui.book;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.client.core.gui.GuiDocumentation;
import teamOD.armourReborn.client.core.gui.RenderUtils;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.modifiers.IModifier;
import teamOD.armourReborn.common.modifiers.ITrait;

public class BookPageModifier extends BookPage {
	
	public static List<Integer> xPos = ImmutableList.of(25, 60, 95) ;
	
	public final List<IModifier> modifiers = Lists.newLinkedList() ;

	public BookPageModifier(String unlocalizedName, ITrait... modifier) {
		super(unlocalizedName);

		for (ITrait mod : modifier) {
			
			if (mod instanceof IModifier) {
				modifiers.add( (IModifier) mod) ;
			} else {
				LibUtil.LogToFML(1, "GuiBook: Cannot display details about a Trait in a Modifier page");
			}
		}
		
		if (modifiers.size() > 1) {
			this.texture = new ResourceLocation (LibMisc.MOD_ID, "textures/gui/guiBookModifierLevelable.png") ;
		} else {
			this.texture = new ResourceLocation (LibMisc.MOD_ID, "textures/gui/guiBookModifier.png") ;
		}
	}
	
	@Override
	public void renderPage (GuiDocumentation parent, int mx, int my) {
		if (modifiers.size() == 0) return ;
		
		String title = I18n.format(this.unlocalizedName + "." + "title") ;
		
		BookPageText.renderText(parent.getLeft() + 15, parent.getTop() + 15, title, unlocalizedName);
		
		if (modifiers.size() > 1) {
			int i = 0 ;
			
			for (IModifier modifier : modifiers) {
				renderItem (parent.getLeft() + xPos.get(i++), parent.getTop() + 125, modifier.getItemStack(), true) ;
			}
		} else {
			renderItem (parent.getLeft() + 58, parent.getTop() + 125, modifiers.get(0).getItemStack(), true) ;
			
			if(mx >= parent.getLeft() + 58 && my >= parent.getTop() + 125 && mx < parent.getLeft() + 74 && my < parent.getTop() + 141) {
				String name = I18n.format(modifiers.get(0).getItemStack().getUnlocalizedName()) ;
				RenderUtils.renderTooltip(mx, my, Collections.singletonList((name))) ;
			}
		}
	}
}
