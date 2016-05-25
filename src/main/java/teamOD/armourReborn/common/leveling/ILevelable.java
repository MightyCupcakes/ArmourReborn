package teamOD.armourReborn.common.leveling;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/** 
 * This interface is to be implemented by all armour items that are levelable.
 * @author MightyCupcakes
 * @see ItemModArmour
 *
 */

public interface ILevelable {
	public static final String TAG_LEVEL = "ArmourLevel" ;
	public static final String TAG_EXP = "ArmourExp" ;
	
	public static final int MAX_LEVEL = 10 ;
	
	/**
	 * Associates the base EXP and LEVEL tags to this armour
	 * @param tag
	 */
	public void addBaseTags (NBTTagCompound tag) ;
	
	public boolean isMaxLevel (NBTTagCompound tags) ;
	
	/**
	 * This SETS the exp of an Ilevelable owned by player to armourXP
	 * @param armour
	 * @param player 
	 * @param armourXP 
	 */
	public void setExp (ItemStack armour, EntityPlayer player, int armourExp) ;
	
	public void addExp (ItemStack armour, EntityPlayer player, int armourExp) ;
	
	/**
	 * Levels the armour up after meeting some requirements set by the above functions
	 * @param armour
	 * @param player
	 */
	public void levelUpArmour (ItemStack armour, EntityPlayer player) ;
}
