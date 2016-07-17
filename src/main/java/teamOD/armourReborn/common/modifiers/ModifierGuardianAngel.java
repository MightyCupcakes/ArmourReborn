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

public class ModifierGuardianAngel extends AbstractModifier {
	
	public static ImmutableList<Float> multipliers = ImmutableList.of(0F, 1F, 1.5F, 2F) ;
	
	public ModifierGuardianAngel (ItemStack item) {
		super ("guardian Angel", TextFormatting.YELLOW, item) ;
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
		if (!itemStack.getTagCompound().hasKey(COOLDOWN)) {
			itemStack.getTagCompound().setLong(COOLDOWN, world.getTotalWorldTime()) ;
		}
		
		float health = player.getHealth() / player.getMaxHealth() ;
		long time = itemStack.getTagCompound().getLong(COOLDOWN) ;
		
		if (health <= 0.25 && world.getTotalWorldTime() >= time) {
			PotionEffect healPlayer = new PotionEffect (Potion.getPotionById(6), 1, 0, false, false) ;
			PotionEffect fortitude = new PotionEffect (Potion.getPotionById(11), 100, 0, true, true) ;
			PotionEffect regeneration = new PotionEffect (Potion.getPotionById(10), 100, 0, true, true) ;
			
			PotionEffect[] effects = new PotionEffect[] { healPlayer, fortitude, regeneration } ;
			
			for (PotionEffect effect : effects) {
				player.addPotionEffect(effect) ;
			}
			
			itemStack.getTagCompound().setLong(COOLDOWN, world.getTotalWorldTime() + (45 * 20));
		}
	}
}
