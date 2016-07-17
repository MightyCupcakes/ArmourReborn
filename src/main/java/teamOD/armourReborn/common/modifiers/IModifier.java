package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;

/**
 * A modifier is a trait that can be associated to an item and added to an armour via the anvil.
 * Unlike traits, modifiers can be added and removed.
 *
 */

public interface IModifier extends ITrait {
	
	public static final String MODIFIERS = "modifiers" ;
	public static final String COOLDOWN = "cooldown" ;
	
	/**
	 * Returns the associated item to this modifier. That is, given this item in anvil, this modifier will be added to the armour
	 * @param item
	 */	
	public ItemStack getItemStack () ;

	public Item getItem() ;
}
