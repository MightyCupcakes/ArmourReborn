package teamOD.armourReborn.client.core.gui.book;

import java.util.List;

import com.google.common.collect.Lists;

import teamOD.armourReborn.common.lib.LibMisc;

public class BookEntry {
	
	public final String name ;
	public final List<BookPage> pages = Lists.newArrayList() ;
	public String buttonName ;
	
	public BookEntry (String name, BookPage... entries) {
		this.name = name ;
		
		if (entries[0] != null && entries[0] instanceof BookPageText) {
			((BookPageText) entries[0]).setTitle( LibMisc.PREFIX_MOD + ".entry." + name);
		}
		
		for (BookPage page : entries) {
			page.unlocalizedName = LibMisc.PREFIX_MOD + ".page." + name + page.unlocalizedName ;
			this.pages.add(page) ;
		}
	}

}
