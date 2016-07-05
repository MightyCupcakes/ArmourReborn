package teamOD.armourReborn.common.leveling;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamOD.armourReborn.common.lib.LibMisc;

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
	
	@SubscribeEvent
	public void onTooltipEvent (ItemTooltipEvent event) {
		if (event.getEntityPlayer() == null) return ;
		
		if (event.getItemStack().getItem() instanceof ILevelable) {
			ILevelable armour = (ILevelable) event.getItemStack().getItem() ;
			int level = armour.getLevel(event.getItemStack()) ;
			
			event.getItemStack().setStackDisplayName(ModLevels.levels.get(level).getColour() + event.getItemStack().getDisplayName()) ;
		}
	}
}
