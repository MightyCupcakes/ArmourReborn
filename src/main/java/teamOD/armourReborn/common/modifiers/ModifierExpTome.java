package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.leveling.ILevelable;

public class ModifierExpTome extends AbstractModifier {
	
	public ModifierExpTome (ItemStack item) {
		super ("knowledgable", TextFormatting.GOLD, item) ;
	}
	
	@Override
	public int onDamage (ItemStack armour, int amount, int newAmount, EntityLivingBase entity) {
		if (armour.getItem() instanceof ILevelable) {
			
			ILevelable item = (ILevelable) armour.getItem() ;
			item.addExp(armour, (EntityPlayer) entity, amount);
		}
		return amount ;
	}
}
