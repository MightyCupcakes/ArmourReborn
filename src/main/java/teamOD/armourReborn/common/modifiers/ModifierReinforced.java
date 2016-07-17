package teamOD.armourReborn.common.modifiers;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

public class ModifierReinforced extends AbstractModifier {
	
	public static final ImmutableList<Float> percentages = ImmutableList.of(0F, 0.2F, 0.35F, 0.45F) ;

	public ModifierReinforced (int level, ItemStack item) {
		super("reinforced", level, TextFormatting.DARK_PURPLE, item) ;
	}
	
	public int onDamage (ItemStack armour, int amount, int newAmount, EntityLivingBase entity) {
		float rand = LibUtil.getRandomFloat() ;
		
		if (rand <= percentages.get(getLevel())) {
			return 0 ;
		} 
		
		return amount ;
	}

}
