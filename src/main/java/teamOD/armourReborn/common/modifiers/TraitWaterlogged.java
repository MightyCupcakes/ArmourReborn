package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class TraitWaterlogged extends AbstractModifier {

	@Override
	public String getIdentifier() {
		return "Waterlogged" ;
	}
	
	@Override
	public void modifyMovementSpeed(EntityPlayer player, ItemStack armour) {
		if (player.isInWater()) {
			player.addPotionEffect(new PotionEffect (Potion.getPotionById(2), 1, 1));
		}
	}
}
