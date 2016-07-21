package teamOD.armourReborn.client.core.gui.book;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public final class BookEntriesRegistry {
	
	private static List<BookEntry> entriesRegistry = Lists.newArrayList();
	
	public static BookEntry introduction ;
	public static BookEntry forgeMultiblock ;
	public static BookEntry armourTypes ;
	public static BookEntry materialTypes ;
	public static BookEntry traits ;
	public static BookEntry modifiers ;
	public static BookEntry levels ;
	
	public static void init () {
		
		introduction = new BookEntry ("introduction", new BookPageText("0"),
				new BookPageText("1"),
				new BookPageText("2"),
				new BookPageRecipe ("3", ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.FEET),
						ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.LEGS),
						ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.CHEST),
						ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.HEAD)
						)
				) ;
		registerBookEntry(introduction).buttonName = "Introduction" ;
		
		forgeMultiblock = new BookEntry ("multiblock", new BookPageText ("0"),
				new BookPageText ("7"),
				new BookPagePicture("1", new ResourceLocation (LibMisc.MOD_ID, "textures/gui/entries/forgeFront.png") ),
				new BookPagePicture("2", new ResourceLocation (LibMisc.MOD_ID, "textures/gui/entries/forgeBack.png") ),
				new BookPageRecipe ("3", ModBlocks.forgeMaster ),
				new BookPageRecipe ("4", ModBlocks.forgeHeater ),
				new BookPageRecipe ("5", ModBlocks.forgeBlocks ),
				new BookPageRecipe ("6", ModBlocks.forgeAnvil ),
				new BookPageRecipe ("8", ModItems.COPPER_CASTS, 0 ),
				new BookPageRecipe ("9", ModItems.COPPER_CASTS, 1 )
				) ;
		registerBookEntry(forgeMultiblock).buttonName = "The Furnance Multiblock" ;
		
		armourTypes = new BookEntry ("armourTypes", new BookPageText ("0") 
				) ;
		registerBookEntry(armourTypes).buttonName = "The Armour Sets" ;
		
		materialTypes = new BookEntry ("materialTypes", new BookPageText ("0")
				) ;
		registerBookEntry(materialTypes).buttonName = "The Armour Materials" ;
		
		traits = new BookEntry ("traits", new BookPageText ("0"),
				new BookPageText("1", true),
				new BookPageText("2", true),
				new BookPageText("3", true),
				new BookPageText("4", true),
				new BookPageText("5", true),
				new BookPageText("6", true),
				new BookPageText("7", true),
				new BookPageText("8", true),
				new BookPageText("9", true),
				new BookPageText("10", true)
				) ;
		registerBookEntry(traits).buttonName = "Armour Traits" ;
		
		modifiers = new BookEntry ("modifiers", new BookPageText ("0"),
				new BookPageModifier ("1", ModTraitsModifiersRegistry.expBoost),
				new BookPageModifier ("2", ModTraitsModifiersRegistry.frostbite),
				new BookPageModifier ("3", ModTraitsModifiersRegistry.featherfall1, 
						ModTraitsModifiersRegistry.featherfall2,
						ModTraitsModifiersRegistry.featherfall3
						),
				new BookPageModifier ("4", ModTraitsModifiersRegistry.invisible1,
						ModTraitsModifiersRegistry.invisible2,
						ModTraitsModifiersRegistry.invisible3
						),
				new BookPageModifier ("5", ModTraitsModifiersRegistry.reinforced1,
						ModTraitsModifiersRegistry.reinforced2,
						ModTraitsModifiersRegistry.reinforced3
						),
				new BookPageModifier ("6", ModTraitsModifiersRegistry.stability),
				new BookPageModifier ("7", ModTraitsModifiersRegistry.unburnt),
				new BookPageModifier ("8", ModTraitsModifiersRegistry.underTheSea)
				) ;
		registerBookEntry(modifiers).buttonName = "Armour Modifiers" ;
		
		levels = new BookEntry ("levels", new BookPageText ("0")
				) ;
		registerBookEntry(levels).buttonName = "Armour Mastery Levels" ;
	}
	
	private static BookEntry registerBookEntry (BookEntry book) {
		entriesRegistry.add(book) ;
		
		return book ;
	}
	
	public static Iterable<BookEntry> getRegisteredEntries () {
		return ImmutableList.<BookEntry>copyOf(entriesRegistry) ;
	}

}
