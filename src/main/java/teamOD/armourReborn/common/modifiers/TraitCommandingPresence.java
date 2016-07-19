package teamOD.armourReborn.common.modifiers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

public class TraitCommandingPresence extends AbstractTrait {
	
	public TraitCommandingPresence () {
		super ("commander's Aura", TextFormatting.GREEN) ;
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		
		List<Entity> entities = LibUtil.getEntitiesAroundPlayer(player, 3) ;
		entities.add(player) ;
		
		PotionEffect haste = new PotionEffect (MobEffects.digSpeed, 100, 0, false, false) ;
		PotionEffect strength = new PotionEffect (MobEffects.damageBoost, 100, 0, false, false) ;
		
		for (Entity entity : entities) {
			if ( !(entity instanceof EntityPlayer) ) continue ;
			
			EntityPlayer p = (EntityPlayer) entity ;
			
			if (p.getActivePotionEffect(MobEffects.digSpeed) != null || p.getActivePotionEffect(MobEffects.damageBoost) != null) continue ;
			
			p.addPotionEffect(haste) ;
			p.addPotionEffect(strength) ;
			
		}
	}

}
