package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

public class TraitRust extends AbstractTrait {
	
	public TraitRust () {
		super ("rust", TextFormatting.GRAY) ;
	}
	
	@Override
	public int onDamage (ItemStack armour, int amount, int newAmount, EntityLivingBase entity) {
		
		float damageRatio = ((float) LibUtil.getItemCurrentDurability(armour)) / armour.getMaxDamage() ;
		
		if (damageRatio <= 0.25) {
			NBTTagCompound tag = armour.getTagCompound() ;
			
			if ( (tag.hasKey("reduced") && tag.getFloat("reduced") > 0) || !tag.hasKey("reduced") ) {
				float currentValue = LibUtil.getCurrentArmourValue(armour) ;
				LibUtil.setArmourValue(armour, currentValue / 2) ;
				
				tag.setFloat("reduced", currentValue / 2) ;
			} 
		}
		
		return amount ;
	}
	
	@Override
	public int onRepair (ItemStack armour, int amount) {
		float damageRatio = ((float) LibUtil.getItemCurrentDurability(armour) + amount) / armour.getMaxDamage() ;
		
		if (damageRatio > 0.25) {
			NBTTagCompound tag = armour.getTagCompound() ;
			
			if (tag.hasKey("reduced") && tag.getFloat("reduced") > 0) {
				float currentValue = LibUtil.getCurrentArmourValue(armour) ;
				LibUtil.setArmourValue(armour, currentValue + tag.getFloat("reduced")) ;
				
				tag.setFloat("reduced", 0) ;
			}
		}
		
		return amount ;
	}
}
