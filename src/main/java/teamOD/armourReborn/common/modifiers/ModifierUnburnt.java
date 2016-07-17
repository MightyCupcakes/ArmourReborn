package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import teamOD.armourReborn.common.potion.ModPotions;

public class ModifierUnburnt extends AbstractModifier {
	
	public ModifierUnburnt (ItemStack item) {
		super ("unburnt", TextFormatting.RED, item) ;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {

		if (player.getActivePotionEffect(ModPotions.fireImmune) == null) {
			PotionEffect potion = new PotionEffect (ModPotions.fireImmune, 200, 1, false, false) ;
			player.addPotionEffect(potion);
		}
	}
}
