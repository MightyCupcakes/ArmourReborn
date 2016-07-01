package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.text.TextFormatting;

public abstract class AbstractModifier implements IModifier {
	
	protected String identifier ;
	protected TextFormatting colour ;
	
	public AbstractModifier (String identifier, TextFormatting colour) {
		this.identifier = identifier ;
		this.colour = colour ;
	}

	@Override
	public String getIdentifier() {
		return identifier ;
	}

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

	@Override
	public boolean negateDamage(ItemStack armour, EntityLivingBase target) {
		return false;
	}

}
