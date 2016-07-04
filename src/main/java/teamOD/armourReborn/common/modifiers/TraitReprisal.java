package teamOD.armourReborn.common.modifiers;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

/**
 * Armour with this trait will have a chance to do the same damage back to the source
 * The incoming damage is not negated, however.
 *
 */
public class TraitReprisal extends AbstractTrait {
	
	public static final ImmutableList<Float> percentages = ImmutableList.of(0F, 0.05F, 0.1F, 0.2F) ;
	
	public TraitReprisal(int level) {
		
		super("reprisal", level, TextFormatting.RED) ;
	}
	
	@Override
	public void onHit(ItemStack armour, EntityLivingBase entity, EntityLivingBase target, float damage) {
		float percentage = percentages.get(level) ;
		
		float randNum = LibUtil.getRandomFloat() ;
		
		if (randNum <= percentage && entity != null) {
			entity.attackEntityFrom (DamageSource.generic, damage) ;
		}
	}
}
