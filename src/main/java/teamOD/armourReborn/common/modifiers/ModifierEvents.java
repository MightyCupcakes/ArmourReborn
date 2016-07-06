package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;
import teamOD.armourReborn.common.lib.LibUtil;

public class ModifierEvents {
	
	@SubscribeEvent
	public void updatePlayerMovementStatus (LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving() ;

			Iterable<ItemStack> armour = player.getArmorInventoryList() ;
			
			for (ItemStack armourPiece : armour) {
				if (armourPiece == null) continue ;
				
				if (armourPiece.getItem() instanceof IModifiable) {
					
					for (ITrait modifier : LibUtil.getTraitsModifiersList(armourPiece)) {
						modifier.modifyMovementSpeed(player, armourPiece) ;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerDamaged (LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving() ;
			
			Iterable<ItemStack> armour = player.getArmorInventoryList() ;
			
			for (ItemStack armourPiece : armour) {
				if (armourPiece == null) continue ;
				
				if (armourPiece.getItem() instanceof IModifiable) {
					
					for (ITrait modifier : LibUtil.getTraitsModifiersList(armourPiece)) {
						
						EntityLivingBase entity = null ;
						
						if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityLivingBase) {
							entity = (EntityLivingBase) event.getSource().getEntity() ; 
						}
						
						modifier.onHit(armourPiece, entity, player, event.getAmount());
						
						if (modifier.negateDamage(armourPiece, player)) {
							event.setCanceled(true) ;
							break ;
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRepair (ModifierEvents.OnRepair event) {
		if (event.stack.getItem() instanceof IModifiable) {
			for ( ITrait trait : LibUtil.getTraitsModifiersList(event.stack) ) {
				trait.onRepair(event.stack, event.amount);
			}
		}
	}
	
	public static class OnRepair extends Event {
		
		public final int amount ;
		public final ItemStack stack ;
		
		public OnRepair (ItemStack stack, int amount) {
			this.stack = stack ;
			this.amount = amount ;
		}
		
		public static boolean fireEvent (ItemStack stack, int amount) {
			OnRepair event = new OnRepair (stack, amount) ;
			
			return !MinecraftForge.EVENT_BUS.post(event) ;
		}
	}
	
}
