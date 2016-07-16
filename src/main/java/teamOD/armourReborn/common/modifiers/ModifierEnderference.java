package teamOD.armourReborn.common.modifiers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.potion.ModPotions;

public class ModifierEnderference extends AbstractModifier {
	
	public ModifierEnderference () {
		super ("enderference", TextFormatting.LIGHT_PURPLE) ;
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		List<Entity> entities = LibUtil.getEntitiesAroundPlayer(player, 5) ;
		
		for (Entity entity : entities) {
			if (entity instanceof EntityEnderman) {
				PotionEffect potion = new PotionEffect (ModPotions.enderference, 100, 1, false, false) ;
				((EntityEnderman) entity).addPotionEffect(potion) ;
			}
		}
	}
}
