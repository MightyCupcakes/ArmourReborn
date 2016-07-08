package teamOD.armourReborn.common.leveling;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.modifiers.ModifierEvents;

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
			
			ILevelable armour = (ILevelable) stack.getItem() ;
			int exp = Math.round (event.getAmount() * LibItemStats.EXP_PER_DMG) ;
			
			// If its player damage, the exp is doubled.
			if (event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
				exp = Math.round(exp * LibItemStats.EXP_MULTIPLIER_PLAYER_DMG) ;
			}
			
			armour.addExp(stack, player, exp) ;
			
		}
	}
	
	@SubscribeEvent
	public void onRepair (ModifierEvents.OnRepair event) {
		if (! (event.stack.getItem() instanceof ILevelable) ) {
			return ;
		}
		
		ILevelable armour = (ILevelable) event.stack.getItem() ;
		armour.addExp(event.stack, event.player, event.amount * LibItemStats.EXP_PER_REPAIR) ;
	}
	
	@SubscribeEvent
	public void onTooltipEvent (ItemTooltipEvent event) {
		if (event.getEntityPlayer() == null) return ;
		
		if (event.getItemStack().getItem() instanceof ILevelable) {
			ILevelable armour = (ILevelable) event.getItemStack().getItem() ;
			int level = armour.getLevel(event.getItemStack()) ;
			
			event.getItemStack().clearCustomName() ;
			event.getItemStack().setStackDisplayName(ModLevels.getLevelInfo(level).getColour() + event.getItemStack().getDisplayName()) ;
		}
	}
}
