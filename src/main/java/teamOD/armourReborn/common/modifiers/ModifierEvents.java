package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModifierEvents {
	
	@SubscribeEvent
	public void updatePlayerMovementStatus (LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving() ;
			
			Iterable<ItemStack> armour = player.getArmorInventoryList() ;
			
			for (ItemStack armourPiece : armour) {
				if (armourPiece == null) continue ;
				
				if (armourPiece.getItem() instanceof IModifiable) {
					IModifiable thisArmour = (IModifiable) armourPiece.getItem() ;
					
					for (IModifier modifier : thisArmour.getModifiers()) {
						modifier.modifyMovementSpeed(player, armourPiece) ;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerDamage (LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving() ;
			
			Iterable<ItemStack> armour = player.getArmorInventoryList() ;
			
			for (ItemStack armourPiece : armour) {
				if (armourPiece == null) continue ;
				
				if (armourPiece.getItem() instanceof IModifiable) {
					IModifiable thisArmour = (IModifiable) armourPiece.getItem() ;
					
					for (IModifier modifier : thisArmour.getModifiers()) {
						modifier.onHit(armourPiece, player, event.getAmount());
						
						if (modifier.negateDamage(armourPiece, player)) {
							event.setCanceled(true) ;
							break ;
						}
					}
				}
			}
		}
	}
	
}
