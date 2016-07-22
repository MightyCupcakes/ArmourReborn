package teamOD.armourReborn.common.achievement;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibMisc;

public final class ModAchievements {
	
	public static AchievementPage modPage ;
	public static AchievementMod craftingArmour ;
	
	public static void init () {
		craftingArmour = new AchievementMod ("craftingArmour", 0, 0, new ItemStack (ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.CHEST)), null) ;
		
		modPage = new AchievementPage(LibMisc.MOD_NAME, AchievementMod.achievements.toArray(new Achievement[AchievementMod.achievements.size()]));
		AchievementPage.registerAchievementPage(modPage) ;		
	}
}
