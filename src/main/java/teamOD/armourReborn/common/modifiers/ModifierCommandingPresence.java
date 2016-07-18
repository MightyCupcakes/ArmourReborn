package teamOD.armourReborn.common.modifiers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

public class ModifierCommandingPresence extends AbstractModifier {
	
	public ModifierCommandingPresence (ItemStack item) {
		super ("commander's Aura", TextFormatting.GREEN, item) ;
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		List<Entity> entities = LibUtil.getEntitiesAroundPlayer(player, 3) ;
		entities.add(player) ;
		
		PotionEffect haste = new PotionEffect (Potion.getPotionById(3), 100, 0, false, false) ;
		PotionEffect strength = new PotionEffect (Potion.getPotionById(5), 100, 0, false, false) ;
		
		for (Entity entity : entities) {
			if ( !(entity instanceof EntityPlayer) ) continue ;
			
			EntityPlayer p = (EntityPlayer) entity ;
			
			p.addPotionEffect(haste) ;
			p.addPotionEffect(strength) ;
			
		}
	}

}
