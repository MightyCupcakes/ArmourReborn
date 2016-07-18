package teamOD.armourReborn.common.leveling;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public final class ModLevels {
	private static List<LevelInfo> levels = Lists.newArrayListWithCapacity(10);
	
	public static void init () {
		
		levels.add(new LevelInfo (1, "Uncomfortable", 2000, TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (2, "Acclimated", 2110, TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (3, "Comfortable", 2379, TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (4, "Accustomed", 2674, TextFormatting.BLUE, 1)) ;
		levels.add(new LevelInfo (5, "Well Accustomed", 2999, TextFormatting.BLUE, 1, ModTraitsModifiersRegistry.surge1)) ;
		levels.add(new LevelInfo (6, "Adept", 3343, TextFormatting.GREEN, 0, 1, 0)) ;
		levels.add(new LevelInfo (7, "Expert", 3719, TextFormatting.YELLOW, 0, 0, 0.25F)) ;
		levels.add(new LevelInfo (8, "Master", 4129, TextFormatting.GOLD, 0, 0, 0.25F)) ;
		levels.add(new LevelInfo (9, "Grandmaster", 4567, TextFormatting.RED, 0, 0, 0.5F, ModTraitsModifiersRegistry.surge2)) ;
		levels.add(new LevelInfo (10, "Legendary", 0, TextFormatting.DARK_PURPLE, 1, 1, 1, ModTraitsModifiersRegistry.angel)) ;
	}
	
	public static LevelInfo getLevelInfo (int level) {
		if (level > levels.size()) return null ;
		
		return levels.get(level - 1 ) ;
	}
	
	public static int getMaxLevel () {
		return levels.size() ;
	}
	
	public static class LevelInfo {
		
		/** The numerical value of this levelinfo */
		private final int level ;
		
		/** For the fancy colouration of the item name */
		private final TextFormatting colour ;
		
		/** The skill string associated to this level. Like rarity */
		private final String skill ;
		
		/** Exp the armour needs in order to level up from this level. Note that exp are reset to 0 upon leveling up */
		private final int expNeeded ;
		
		/** A array of traits that this level will provide to the armour upon leveling */
		private final ITrait[] traitsIdentifiers ;
		
		/** The number of modifier slots this level provides upon attainment on top of existing slots */
		private final int modifierSlots ;
		
		/** Provides extra toughness (if any) ON TOP of existing toughness */
		private final float toughness ;
		
		/** Provides extra armour (if any) ON TOP of existing armour */
		private final float armourValue ;
		
		public LevelInfo (int level, String skill, int exp, TextFormatting colour, ITrait... traitsIdentifiers) {
			this (level, skill, exp, colour, 0, traitsIdentifiers) ;
		}
		
		public LevelInfo (int level, String skill, int exp, TextFormatting colour, int slots, ITrait... traitsIdentifiers) {
			this (level, skill, exp, colour, slots, 0, 0, traitsIdentifiers) ; 
		}
		
		public LevelInfo (int level, String skill, int exp, TextFormatting colour, int slots, float armour, float toughness, ITrait... traitsIdentifiers) {
			this.level = level ;
			this.skill = skill ;
			this.colour = colour ;
			this.expNeeded = exp ;
			this.traitsIdentifiers = traitsIdentifiers ;
			this.modifierSlots = slots ;
			this.armourValue = armour ;
			this.toughness = toughness ;
		}
		
		public int getLevel () {
			return level ;
		}
		
		public TextFormatting getColour () {
			return colour ;
		}
		
		public String getSkillString () {
			return skill ;
		}
		
		public ITrait[] getTraitIdentifiers () {
			return traitsIdentifiers ;
		}
		
		public int getExpNeeded () {
			return expNeeded ;
		}
		
		public int getNumModifierSlots () {
			return modifierSlots ;
		}
		
		public float getToughness () {
			return toughness ;
		}
		
		public float getArmourValue () {
			return armourValue ;
		}
	}
}
