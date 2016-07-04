package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class TraitSurge extends AbstractTrait {

	public TraitSurge() {
		super("surge", TextFormatting.BLUE) ;
	}
	
	@Override
	public void modifyMovementSpeed(EntityPlayer player, ItemStack armour) {
		player.stepHeight = 1F ;
		
		if((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F) {
			player.moveFlying(0F, 1F, 0.035F);
		}
	}
}
