package teamOD.armourReborn.common.achievement;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import teamOD.armourReborn.common.lib.LibMisc;

public class AchievementMod extends Achievement {
	
	public static final List<Achievement> achievements = Lists.newArrayList();

	public AchievementMod(String name, int x, int y, ItemStack icon, Achievement parent) {
		super("achievement." + LibMisc.PREFIX_MOD + ":" + name, LibMisc.PREFIX_MOD + ":" + name, x, y, icon, parent);
		
		achievements.add(this);
		this.registerStat() ;
	}
}
