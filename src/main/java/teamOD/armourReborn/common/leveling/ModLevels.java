package teamOD.armourReborn.common.leveling;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public final class ModLevels {
	private static List<LevelInfo> levels = Lists.newLinkedList();
	
	public static void init () {
		
		levels.add(new LevelInfo (1, "Uncomfortable", 2000, TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (2, "Acclimated", 2110, TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (3, "Comfortable", 2379, TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (4, "Accustomed", 2674, TextFormatting.BLUE)) ;
		levels.add(new LevelInfo (5, "Well Accustomed", 2999, TextFormatting.BLUE, ModTraitsModifiersRegistry.surge)) ;
		levels.add(new LevelInfo (6, "Adept", 3343, TextFormatting.GREEN)) ;
		levels.add(new LevelInfo (7, "Expert", 3719, TextFormatting.YELLOW)) ;
		levels.add(new LevelInfo (8, "Master", 4129, TextFormatting.GOLD)) ;
		levels.add(new LevelInfo (9, "Grandmaster", 4567, TextFormatting.RED)) ;
		levels.add(new LevelInfo (10, "Legendary", 0, TextFormatting.DARK_PURPLE)) ;
	}
	
	public static LevelInfo getLevelInfo (int level) {
		if (level > levels.size()) return null ;
		
		return levels.get(level - 1 ) ;
	}
	
	public static int getMaxLevel () {
		return levels.size() ;
	}
	
	public static class LevelInfo {
		
		private final int level ;
		private final TextFormatting colour ;
		private final String skill ;
		private final int expNeeded ;
		private final ITrait[] traitsIdentifiers ;
		
		public LevelInfo (int level, String skill, int exp, TextFormatting colour, ITrait... traitsIdentifiers) {
			this.level = level ;
			this.skill = skill ;
			this.colour = colour ;
			this.expNeeded = exp ;
			this.traitsIdentifiers = traitsIdentifiers ;
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
	}
}
