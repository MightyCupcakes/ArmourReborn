package teamOD.armourReborn.client.core.gui.book;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.lib.LibMisc;

public final class BookEntriesRegistry {
	
	private static List<BookEntry> entriesRegistry = Lists.newArrayList();
	
	public static BookEntry introduction ;
	public static BookEntry forgeMultiblock ;
	
	public static void init () {
		introduction = new BookEntry ("introduction", new BookPageText("0"),
				new BookPageText("1")) ;
		registerBookEntry(introduction).buttonName = "Introduction" ;
		
		forgeMultiblock = new BookEntry ("multiblock", new BookPageText ("0"),
				new BookPagePicture("1", new ResourceLocation (LibMisc.MOD_ID, "textures/gui/entries/forgeFront.png") ),
				new BookPagePicture("2", new ResourceLocation (LibMisc.MOD_ID, "textures/gui/entries/forgeBack.png") ),
				new BookPageRecipe ("3", ModBlocks.forgeMaster ),
				new BookPageRecipe ("4", ModBlocks.forgeHeater ),
				new BookPageRecipe ("5", ModBlocks.forgeBlocks )
				) ;
		registerBookEntry(forgeMultiblock).buttonName = "The Furnance Multiblock" ;
	}
	
	private static BookEntry registerBookEntry (BookEntry book) {
		entriesRegistry.add(book) ;
		
		return book ;
	}
	
	public static Iterable<BookEntry> getRegisteredEntries () {
		return ImmutableList.<BookEntry>copyOf(entriesRegistry) ;
	}

}
