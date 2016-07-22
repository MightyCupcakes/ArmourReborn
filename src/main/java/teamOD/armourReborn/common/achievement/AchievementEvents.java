package teamOD.armourReborn.common.achievement;

import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class AchievementEvents {
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		if(event.crafting != null && event.crafting.getItem() instanceof IOnCraftAchievement) {
			Achievement achievement = ((IOnCraftAchievement) event.crafting.getItem()).getAchievementOnCraft(event.crafting, event.player, event.craftMatrix);
			
			if(achievement != null) {
				event.player.addStat(achievement, 1);
			}
		}
	}
}
