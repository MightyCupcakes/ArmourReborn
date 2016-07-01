package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;

public interface IModifier {
	
	/**
	 * Returns a string identifier that uniquely identifies this modifier
	 *
	 */
	public String getIdentifier () ;
	
	/**
	 * A modifier may have multiple levels of increasing effects.
	 * @return int representing the modifier level. Default is 1
	 */
	public int getLevel () ;
	
	/**
	 * Called when this modifier modifies the movement speed of a player in any way
	 * 
	 * @param player	The player whose movement speed is to be modified
	 * @param armour	The armour the player is currently wearing
	 */
	public void modifyMovementSpeed (EntityPlayer player, ItemStack armour) ;
	
	/**
	 * Some modifiers cannot be applied together for technical and balance reasons.
	 * 
	 * @param modifier	The modifier to be to applied alongside this modifier to a armour
	 * @return true if given modifier can be applied with this modifier; false otherwise
	 */
	public boolean canApplyTogether (IModifier modifier) ;
	
	/**
	 * Called when an player entity is hit, just before damage is dealt. Damage is the final damage dealt after critical hit calculations (if any)
	 * 
	 * @param armour		The amour the target is wearing
	 * @param target		The player targeted
	 * @param damage		The final damage value dealt to the target player
	 */
	public void onHit (ItemStack armour, EntityLivingBase target, float damage) ;
	
	/**
	 * Called before applying status effect to the target.
	 * 
	 * @param armour	The armourset the target is wearing
	 * @param target	The target player the status effect is applied upon
	 * @param status	The status effect to be applied to the target
	 */
	public void onStatusEffect (ItemStack armour, EntityLivingBase target, PotionType status) ;
	
}
