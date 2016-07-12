package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

/**
 * A Trait that applies burning to the player and reduces the durability of the armour by half
 * upon entering the nether
 *
 */
public class TraitFlammable extends AbstractTrait {
	
	public TraitFlammable() {	
		super("flammable", TextFormatting.RED) ;
	}
	
	@Override
	public void onLeavingDimension (EntityPlayer player, ItemStack armour) {
		player.setFire(5) ;
		
		armour.setItemDamage(armour.getItemDamage() / 2);
	}
}
