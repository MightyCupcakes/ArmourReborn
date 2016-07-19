package teamOD.armourReborn.client.core.gui.book;

import teamOD.armourReborn.common.block.ModBlocks;

public final class BookEntriesRegistry {
	
	public static BookEntry introduction ;
	public static BookEntry forgeMultiblock ;
	
	public static void init () {
		//introduction = new BookEntry () ;
		forgeMultiblock = new BookEntry ("multiblock", new BookPageText ("0"),
				new BookPageRecipe ("1", ModBlocks.forgeMaster ),
				new BookPageRecipe ("2", ModBlocks.forgeHeater ),
				new BookPageRecipe ("3", ModBlocks.forgeBlocks )
				) ;
	}

}
