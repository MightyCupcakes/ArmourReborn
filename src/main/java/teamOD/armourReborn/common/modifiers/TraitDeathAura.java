package teamOD.armourReborn.common.modifiers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

/**
 * A TRAIT FOR TESTING!!!
 *
 */
@Deprecated
public class TraitDeathAura extends AbstractTrait{

	public TraitDeathAura() {
		super("death Aura", TextFormatting.RED) ;
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		List<Entity> entities = LibUtil.getEntitiesAroundPlayer(player, 4) ;
		
		for (Entity entity : entities) {
			if (entity instanceof EntityMob) {
				
				EntityMob mob = (EntityMob) entity ;
				mob.addPotionEffect(new PotionEffect (Potion.getPotionById(20), 3 * 20, 1)) ;
			}
		}
	}
}
