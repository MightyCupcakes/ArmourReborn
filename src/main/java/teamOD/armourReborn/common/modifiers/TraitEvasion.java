package teamOD.armourReborn.common.modifiers;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class TraitEvasion extends AbstractTrait {

	public TraitEvasion(String identifier, TextFormatting colour) {
		super("evasion", TextFormatting.GREEN) ;
	}
	
	@Override
	public boolean negateDamage (ItemStack armour, EntityLivingBase target) {
		float percentage = 0 ;
		
		switch (this.getLevel()) {
		case 1:
			percentage = 0.05F ;
			break ;
		
		case 2:
			percentage = 0.1F ;
			break ;
			
		case 3:
			percentage = 0.2F ;
			break ;
		}
		
		Random rand = new Random () ;
		float num = rand.nextFloat() ;
		
		if (num <= percentage) {
			return true ;
		}
		
		return false ;
	}

}
