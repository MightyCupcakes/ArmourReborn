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
	
	public TraitCommandingPresence (int level) {
		super ("commander's Aura", level, TextFormatting.GREEN) ;
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		
		List<Entity> entities = LibUtil.getEntitiesAroundPlayer(player, 3) ;
		entities.add(player) ;
		
		PotionEffect haste = new PotionEffect (MobEffects.HASTE, 100, this.getLevel() - 1, false, false) ;
		PotionEffect strength = new PotionEffect (MobEffects.STRENGTH, 100, this.getLevel() - 1, false, false) ;
		
		for (Entity entity : entities) {
			if ( !(entity instanceof EntityPlayer) ) continue ;
			
			EntityPlayer p = (EntityPlayer) entity ;
			
			if (p.getActivePotionEffect(MobEffects.HASTE) != null || p.getActivePotionEffect(MobEffects.STRENGTH) != null) continue ;
			
			p.addPotionEffect(haste) ;
			p.addPotionEffect(strength) ;
			
		}
	}

}
