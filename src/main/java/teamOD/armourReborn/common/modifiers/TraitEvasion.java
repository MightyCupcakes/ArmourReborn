package teamOD.armourReborn.common.modifiers;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

/**
 * Armour with this trait has a chance to negate the incoming damage totally.
 *
 */
public class TraitEvasion extends AbstractTrait {
	
	public static final ImmutableList<Float> percentages = ImmutableList.of(0F, 0.05F, 0.1F, 0.2F) ;

	public TraitEvasion(int level) {
		super("evasion", level, TextFormatting.GREEN) ;
	}
	
	@Override
	public boolean negateDamage (ItemStack armour, EntityLivingBase target) {
		float percentage = percentages.get(level) ;
		
		float randNum = LibUtil.getRandomFloat() ;
		
		if (randNum <= percentage) {
			return true ;
		}
		
		return false ;
	}

}
