package teamOD.armourReborn.common.modifiers;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class ModifierDepthStrider extends AbstractModifier {
	
	private static final double SPEED_MULT = 1.2;
	private static final double MAX_SPEED = 1.3;
	
	public ModifierDepthStrider (ItemStack item) {
		super ("depth Strider", TextFormatting.AQUA, item) ;
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) {
		
		if(player.isInsideOfMaterial(Material.water)) {
			double motionX = player.motionX * SPEED_MULT;
			double motionY = player.motionY * SPEED_MULT;
			double motionZ = player.motionZ * SPEED_MULT;
		
			if(Math.abs(motionX) < MAX_SPEED) 
				player.motionX = motionX;
			if(Math.abs(motionY) < MAX_SPEED)
				player.motionY = motionY;
			if(Math.abs(motionZ) < MAX_SPEED)
				player.motionZ = motionZ;
			
			if (player.getActivePotionEffect(MobEffects.nightVision) == null) {
				PotionEffect neweffect = new PotionEffect(MobEffects.nightVision, 600, -322, true, false);
				player.addPotionEffect(neweffect);
			}
			
			if (player.getActivePotionEffect(MobEffects.waterBreathing) == null) {
				PotionEffect neweffect = new PotionEffect(MobEffects.waterBreathing, 600, -322, true, false);
				player.addPotionEffect(neweffect);
			}
		} else {
			PotionEffect night = player.getActivePotionEffect(MobEffects.nightVision) ;
			PotionEffect breath = player.getActivePotionEffect(MobEffects.waterBreathing) ;
			
			if (night != null && night.getAmplifier() == -322) {
				player.removePotionEffect(MobEffects.nightVision);
			}
			
			if (breath != null && breath.getAmplifier() == -322) {
				player.removePotionEffect(MobEffects.waterBreathing);
			}
		}
	}
	
	@Override
	public boolean canApplyToEquipment (ItemStack armour) {
		if (armour.getItem() instanceof ItemArmor) {
			ItemArmor armourPiece = (ItemArmor) armour.getItem() ;
			
			return armourPiece.armorType == EntityEquipmentSlot.HEAD ;
		}
		
		return false ;
	}
}
