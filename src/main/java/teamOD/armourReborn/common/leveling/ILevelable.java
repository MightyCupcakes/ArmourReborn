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
	
	public void addBaseTags (NBTTagCompound tag) ;
	
	public int getLevel (NBTTagCompound tags) ;
	public int getExp (NBTTagCompound tags) ;
	
	public boolean hasLevel (NBTTagCompound tags) ;
	public boolean hasExp (NBTTagCompound tags) ;
	
	public boolean isMaxLevel (NBTTagCompound tags) ;
	
	/**
	 * This SETS the exp of an Ilevelable owned by player to armourXP
	 * @param armour
	 * @param player 
	 * @param armourXP 
	 */
	public void setExp (ItemStack armour, EntityPlayer player, int armourExp) ;
	
	public void addExp (ItemStack armour, EntityPlayer player, int armourExp) ;
	public void levelUpArmour (ItemStack armour, EntityPlayer player) ;
}
