package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class TraitWaterlogged extends AbstractTrait {
	
	public TraitWaterlogged() {
		
		super("waterlogged", TextFormatting.AQUA) ;
	}

	@Override
	public void modifyMovementSpeed(EntityPlayer player, ItemStack armour) {
		if (player.isInWater()) {
			player.addPotionEffect(new PotionEffect (Potion.getPotionById(2), 1, 1, true, true));
		}
	}
}
