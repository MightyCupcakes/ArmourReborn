package teamOD.armourReborn.common.modifiers;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class TraitSurge extends AbstractTrait {
	
	public static final ImmutableList<Float> speeds = ImmutableList.of(0F, 0.035F, 0.065F, 0.085F) ;
	
	public TraitSurge(int level) {
		super("surge", level, TextFormatting.BLUE) ;
	}
	
	@Override
	public void modifyMovementSpeed(EntityPlayer player, ItemStack armour) {
		player.stepHeight = 1F ;
		
		if((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F && !player.isInWater()) {
			player.moveFlying(0F, 1F, speeds.get(getLevel()));
		}
	}
	
	@Override
	public boolean canApplyToEquipment (ItemStack armour) {
		if (armour.getItem() instanceof ItemArmor) {
			ItemArmor armourPiece = (ItemArmor) armour.getItem() ;
			
			return armourPiece.armorType == EntityEquipmentSlot.FEET ;
		}
		
		return false ;
	}
}
