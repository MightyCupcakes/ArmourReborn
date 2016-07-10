package teamOD.armourReborn.common.modifiers;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
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
			
			player.stepHeight = 0.5F ;

			Iterable<ItemStack> armour = player.getArmorInventoryList() ;
			
			for (ItemStack armourPiece : armour) {
				if (armourPiece == null) continue ;
				
				if (armourPiece.getItem() instanceof IModifiable) {
					
					for (ITrait modifier : LibUtil.getArmourTraits(player, armourPiece)) {
						modifier.modifyMovementSpeed(player, armourPiece) ;
						modifier.emitAuraEffect(player, armourPiece);
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
					
					for (ITrait modifier : LibUtil.getArmourTraits(player, armourPiece)) {
						
						EntityLivingBase entity = null ;
						
						if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityLivingBase) {
							entity = (EntityLivingBase) event.getSource().getEntity() ; 
						}
						
						modifier.onHit(armourPiece, entity, player, event.getAmount());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerAttacked (LivingAttackEvent event) {
		if (! (event.getEntityLiving() instanceof EntityPlayer) ) {
			return ;
		}
		
		EntityPlayer player = (EntityPlayer) event.getEntityLiving() ;
		
		Iterable<ItemStack> armour = player.getArmorInventoryList() ;
		
		if (event.getSource().getEntity() == null) return ;
		
		for (ItemStack armourPiece : armour) {
			if (armourPiece == null) continue ;
			
			if (armourPiece.getItem() instanceof IModifiable) {
				
				for (ITrait modifier : LibUtil.getArmourTraits(player, armourPiece)) {
					if (modifier.negateDamage(armourPiece, player)) {
						event.setCanceled(true) ;
						break ;
					}
				}
			}
		}
	}
	
	@SubscribeEvent 
	public void onTravelToNether (EntityTravelToDimensionEvent event) {
		if ( ! (event.getEntity() instanceof EntityPlayer )) {
			return ;
		}
		
		if (event.getDimension() != -1) return ;
		
		EntityPlayer player = (EntityPlayer) event.getEntity() ;
		
		Iterable<ItemStack> armour = player.getArmorInventoryList() ;
		
		for (ItemStack armourPiece : armour) {
			if (armourPiece == null) continue ;
			
			if (armourPiece.getItem() instanceof IModifiable) {
				
				for (ITrait modifier : LibUtil.getArmourTraits(player, armourPiece)) {
					modifier.onLeavingDimension(player, armourPiece);
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
		
		public final EntityPlayer player ;
		public final int amount ;
		public final ItemStack stack ;
		
		public OnRepair (EntityPlayer player, ItemStack stack, int amount) {
			this.player = player ;
			this.stack = stack ;
			this.amount = amount ;
		}
		
		public static boolean fireEvent (EntityPlayer player, ItemStack stack, int amount) {
			OnRepair event = new OnRepair (player, stack, amount) ;
			
			return !MinecraftForge.EVENT_BUS.post(event) ;
		}
	}
	
}
