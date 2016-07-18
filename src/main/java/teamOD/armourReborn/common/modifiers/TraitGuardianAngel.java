package teamOD.armourReborn.common.modifiers;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TraitGuardianAngel extends AbstractTrait {
	
	public static final String GUARDIAN_COOLDOWN = "guardian" + IModifier.COOLDOWN ;
	
	public static final Potion INSTANT = Potion.getPotionById(6) ;
	public static final Potion DEFENSE = Potion.getPotionById(11) ;
	public static final Potion REGEN = Potion.getPotionById(10) ;
	
	public TraitGuardianAngel () {
		super ("guardian Angel", TextFormatting.YELLOW) ;
	}
	
	@Override
	public boolean canApplyToEquipment (ItemStack armour) {
		if (armour.getItem() instanceof ItemArmor) {
			ItemArmor armourPiece = (ItemArmor) armour.getItem() ;
			
			return armourPiece.armorType == EntityEquipmentSlot.CHEST ;
		}
		
		return false ;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (!itemStack.getTagCompound().hasKey(GUARDIAN_COOLDOWN)) {
			itemStack.getTagCompound().setLong(GUARDIAN_COOLDOWN, world.getTotalWorldTime()) ;
		}
		
		float health = player.getHealth() / player.getMaxHealth() ;
		long time = itemStack.getTagCompound().getLong(GUARDIAN_COOLDOWN) ;
		
		if (player.getActivePotionEffect(DEFENSE) != null) return ;
		
		if (health <= 0.25 && world.getTotalWorldTime() >= time) {
			PotionEffect healPlayer = new PotionEffect (INSTANT, 1, 0, false, false) ;
			PotionEffect fortitude = new PotionEffect (DEFENSE, 200, 0, true, true) ;
			PotionEffect regeneration = new PotionEffect (REGEN, 200, 0, true, true) ;
			
			PotionEffect[] effects = new PotionEffect[] { healPlayer, fortitude, regeneration } ;
			
			for (PotionEffect effect : effects) {
				player.addPotionEffect(effect) ;
			}
			
			itemStack.getTagCompound().setLong(GUARDIAN_COOLDOWN, world.getTotalWorldTime() + (45 * 20));
		}
	}
}
