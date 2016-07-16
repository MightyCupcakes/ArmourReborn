package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import teamOD.armourReborn.common.potion.ModPotions;

public class ModifierUnyielding extends AbstractModifier {

	public ModifierUnyielding () {
		super ("unyielding", TextFormatting.DARK_GREEN) ;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		if (player.getActivePotionEffect(ModPotions.stability) == null) {
			PotionEffect potion = new PotionEffect (ModPotions.stability, 200, 1, false, false) ;
			player.addPotionEffect(potion) ;
		}
	}
}
