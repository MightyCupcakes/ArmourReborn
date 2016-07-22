package teamOD.armourReborn.common.achievement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public interface IOnCraftAchievement {
	
	public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory inventory);
}
