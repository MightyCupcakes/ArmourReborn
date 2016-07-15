package teamOD.armourReborn.client.core.gui.book;

import java.util.List;

import com.google.common.collect.Lists;

public class BookEntry {
	
	public final String name ;
	public final List<BookPage> pages = Lists.newArrayList() ;
	
	public BookEntry (String name) {
		this.name = name ;
	}

}
