package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class AbstractTrait implements ITrait {
	protected String identifier ;
	protected String traitFamily ;
	protected TextFormatting colour ;
	
	protected int level ;
	
	public AbstractTrait (String identifier, TextFormatting colour) {
		this (identifier, 0, colour) ;
	}
	
	public AbstractTrait (String identifier, int level, TextFormatting colour) {
		this.traitFamily = identifier ;
		this.identifier = identifier + " " + ITrait.levelStrings.get(level) ;
		this.colour = colour ;
		this.level = level ;
	}
	
	@Override
	public TextFormatting getColour() {
		return colour ;
	}

	@Override
	public String getIdentifier() {
		return identifier ;
	}

	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public String getTraitFamily () {
		return traitFamily ;
	}
	
	@Override
	public boolean canApplyToEquipment (ItemStack armour) {
		return true ;
	}

	@Override
	public void modifyMovementSpeed(EntityPlayer player, ItemStack armour) {
		// NO OP
	}

	@Override
	public boolean canApplyTogether(ITrait modifier) {
		return !(modifier.getIdentifier().contains(traitFamily)) ;
	}

	@Override
	public void onHit(ItemStack armour, EntityLivingBase entity, EntityLivingBase target, float damage) {
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
	
	@Override
	public int onRepair (ItemStack armour, int amount) {
		return amount ;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack){
		// NO-OP
	}
	
	@Override
	public int onDamage (ItemStack armour, int amount, int newAmount, EntityLivingBase entity) {
		return amount ;
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		// NO-OP
	}
	
	@Override
	public void onLeavingDimension (EntityPlayer player, ItemStack armour) {
		// NO OP
	}
}
