package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;

public abstract class AbstractModifier implements IModifier {

	@Override
	public abstract String getIdentifier() ;

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public void modifyMovementSpeed(EntityPlayer player, ItemStack armour) {
		// NO OP
	}

	@Override
	public boolean canApplyTogether(IModifier modifier) {
		return true ;
	}

	@Override
	public void onHit(ItemStack armour, EntityLivingBase target, float damage) {
		// NO OP		
	}

	@Override
	public void onStatusEffect(ItemStack armour, EntityLivingBase target, PotionType status) {
		// NO OP		
	}

}
