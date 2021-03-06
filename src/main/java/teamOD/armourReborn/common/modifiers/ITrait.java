package teamOD.armourReborn.common.modifiers;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * A trait is a special feature associated to the material itself and cannot be modified.
 * 
 */
public interface ITrait {
	
	public static final Map<Integer, String> levelStrings = ImmutableMap.<Integer, String>builder()
			.put(0, "")
			.put(1, "I")
			.put(2, "II")
			.put(3, "III")
			.build() ;
	
	public static final String MATERIAL_TRAITS = "materialTraits" ;
	public static final String ARMOUR_SET_TRAITS = "armourTraits" ;
	
	/**
	 * Returns a string identifier that uniquely identifies this modifier
	 *
	 */
	public String getIdentifier () ;
	
	public TextFormatting getColour () ;
	
	/**
	 * A modifier may have multiple levels of increasing effects.
	 * If level is set to 0, then the trait has only one level and cannot be leveled.
	 * 
	 * @return int representing the modifier level. Default is 0
	 */
	public int getLevel () ;
	
	public String getTraitFamily () ;
	
	/**
	 * Returns > 0 if this trait does provide additional modifier slot.
	 * @return number of additional slots provided by this trait on top on default slots
	 */
	public int providesAdditionalModifier () ;
	
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
	public boolean canApplyTogether (ITrait modifier) ;
	
	public boolean canApplyToEquipment (ItemStack armour) ;
	
	/**
	 * Returns true if this trait is levelable. A trait is levelable if and only if its starting level is not 0.
	 * @return
	 */
	public boolean isLevelable () ;
	
	/**
	 * Called when an player entity is hit, just before damage is dealt. Damage is the final damage dealt after critical hit calculations (if any)
	 * 
	 * @param armour		The armour the target is wearing
	 * @param entity		The entity responsible for dealing this hit
	 * @param target		The player targeted
	 * @param damage		The final damage
	 */
	public void onHit (ItemStack armour, EntityLivingBase entity, EntityLivingBase target, float damage) ;
	
	/**
	 * For modifiers that gives a chance to negate damage fully or partially
	 * 
	 * @param armour	The armour the target is wearing
	 * @param target	The player targeted
	 * @return			True if damage should be negated; false otherwise
	 */
	public boolean negateDamage (ItemStack armour, EntityLivingBase target) ;
	
	/**
	 * Called when the armour is repaired. For modifiers that does something when the armour is about to be repaired.
	 * 
	 * @param armour	The armour to be repaired
	 * @param amount	The amount repaired.
	 * @return			The amount to be repaired. Default is the given amount.
	 */
	public int onRepair (ItemStack armour, int amount) ;
	
	/**
	 * Called when a armour is about to be damaged. For traits that do something when armour is damaged
	 * 
	 * @param armour	The armour itemstack
	 * @param amount	The original amount of damage to be dealt to the armour
	 * @param newAmount	The new amount (if any) modified by traits/modifiers
	 * @param entity	The owner of the itemstack
	 * @return			The damage to deal. Default is the given amount ;
	 */
	public int onDamage (ItemStack armour, int amount, int newAmount, EntityLivingBase entity) ;
	
	/**
	 * Called every tick when the armour is equipped (by forgeAPI)
	 * 
	 * @param world
	 * @param player
	 * @param itemStack
	 */
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) ;
	
	/**
	 * For traits that does something to entites around the player like an aura
	 * 
	 * @param player
	 * @param armour
	 */
	public void emitAuraEffect (EntityPlayer player, ItemStack armour) ;
	
	/**
	 * For traits that fire when player leaves a dimension for the nether
	 * @param player
	 * @param armour
	 */
	public void onLeavingDimension (EntityPlayer player, ItemStack armour) ;
	
	/**
	 * Fires when an entity targets the player
	 * @param attacker
	 * @param player
	 * @param armour
	 */
	public void onPlayerTargeted (EntityLiving attacker, EntityPlayer player, ItemStack armour) ;
	
	public float onPlayerFalling (EntityPlayer player, ItemStack armour, float distance) ;

}
