package teamOD.armourReborn.common.leveling;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public final class ModLevels {
	public static List<LevelInfo> levels = Lists.newLinkedList();
	
	public static void init () {
		
		levels.add(new LevelInfo (1, "Uncomfortable", TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (2, "Acclimating", TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (3, "Comfortable", TextFormatting.WHITE)) ;
		levels.add(new LevelInfo (4, "Accustomed", TextFormatting.BLUE)) ;
		levels.add(new LevelInfo (5, "Well Accustomed", TextFormatting.BLUE, ModTraitsModifiersRegistry.surge)) ;
		levels.add(new LevelInfo (6, "Adept", TextFormatting.GREEN)) ;
		levels.add(new LevelInfo (7, "Expert", TextFormatting.GREEN)) ;
		levels.add(new LevelInfo (8, "Master", TextFormatting.YELLOW)) ;
		levels.add(new LevelInfo (9, "Grandmaster", TextFormatting.GOLD)) ;
		levels.add(new LevelInfo (10, "Legendary", TextFormatting.DARK_PURPLE)) ;
	}
	
	public static class LevelInfo {
		
		private final int level ;
		private final TextFormatting colour ;
		private final String skill ;
		private final ITrait[] traitsIdentifiers ;
		
		public LevelInfo (int level, String skill, TextFormatting colour, ITrait... traitsIdentifiers) {
			this.level = level ;
			this.skill = skill ;
			this.colour = colour ;
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
	}
}
