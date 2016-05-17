package teamOD.armourReborn.common.leveling;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LevelingEventHandler {
	@SubscribeEvent
	public void onDamage (LivingHurtEvent event) {
		// When a player is damaged, their armours level up
		if ( ! (event.getEntity() instanceof EntityPlayer) ) {
			return ;
		}
		
		EntityPlayer player = (EntityPlayer) event.getEntity() ;
		
		for (ItemStack stack : player.getArmorInventoryList() ) {
			if (stack == null) continue ;
			
			if ( ! (stack.getItem() instanceof ILevelable) ) continue ;
			
			
		}
	}
}
