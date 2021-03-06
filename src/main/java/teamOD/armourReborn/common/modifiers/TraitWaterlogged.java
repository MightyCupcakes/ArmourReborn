package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

/**
 * Armour with this trait tends to absorb too much water and becomes a burden
 *
 */
public class TraitWaterlogged extends AbstractTrait {
	
	public TraitWaterlogged() {
		
		super("waterlogged", TextFormatting.AQUA) ;
	}

	@Override
	public void modifyMovementSpeed(EntityPlayer player, ItemStack armour) {
		Potion weakness = Potion.getPotionById(2) ;
		
		if (player.isInWater() ) {
			
			if (player.getActivePotionEffect(weakness) != null && player.getActivePotionEffect(weakness).getDuration() > 1) return ;
			
			player.addPotionEffect(new PotionEffect (MobEffects.SLOWNESS, 5 * 20, 1, true, true));
		}
	}
}
