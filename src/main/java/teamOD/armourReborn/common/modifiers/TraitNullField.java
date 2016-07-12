package teamOD.armourReborn.common.modifiers;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class TraitNullField extends AbstractTrait{

	public TraitNullField() {
		super("null Field", TextFormatting.GRAY);
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		List<PotionEffect> toRemove = Lists.newLinkedList() ;
		
		if (player.worldObj.isRemote) return ;
		
		for (PotionEffect potion: player.getActivePotionEffects()) {
			if (potion.getPotion().isBadEffect()) {
				toRemove.add(potion) ;
			}
		}
		
		for (PotionEffect potion: toRemove) {
			player.removePotionEffect(potion.getPotion());
		}
	}
}
