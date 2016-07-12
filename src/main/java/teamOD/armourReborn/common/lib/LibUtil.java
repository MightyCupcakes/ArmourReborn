package teamOD.armourReborn.common.lib;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.IModifier;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;
import teamOD.armourReborn.common.modifiers.ModifierEvents;

public final class LibUtil {
	
	public static Random randGenerator = new Random () ;
	
	/**
	 * Log a message using the logger provided by ForgeModLoader
	 * @param int level. Level describes the severity, with level 1 being a log just for information and 3 being a critical error message
	 * @param format a string formatter
	 * @param message
	 */
	public static void LogToFML (int level, String format, Object... data) {
		
		switch (level) {
		case 1:
			FMLLog.info(format, data) ;
			break ;
		case 2:
			FMLLog.warning(format, data) ;
			break ;
		case 3:
			FMLLog.severe(format, data) ;
			break ;
		default:
			FMLLog.fine(format, data) ;
		}
	}
	
	public static String getPrefix (String name) {
		return LibMisc.PREFIX_MOD + "." + name ;
	}

	public static NBTTagCompound writePos(BlockPos pos) {
		NBTTagCompound tag = new NBTTagCompound();
		if(pos != null) {
			tag.setInteger("X", pos.getX());
			tag.setInteger("Y", pos.getY());
			tag.setInteger("Z", pos.getZ());
		}
		return tag;
	}
	
	public static BlockPos readPos(NBTTagCompound tag) {
		if(tag != null) {
			return new BlockPos(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
		}
		return null;
	}
	
	public static float getRandomFloat () {		
		return randGenerator.nextFloat() ;
	}
	
	public static boolean isShiftKeyDown () {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ;
	}
	
	public static int getItemCurrentDurability (ItemStack stack) {
		int damage = stack.getItemDamage() ;
		int maxDamage = stack.getMaxDamage() ;
		
		return maxDamage - damage ;
	}
	
	public static int getStackModifierSlots (ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound() ;
		
		if (tag.hasKey(IModifiable.MODIFIER_SLOTS)) {
			return tag.getInteger(IModifiable.MODIFIER_SLOTS) ;
		}
		
		return 0 ;
	}
	// Modifiers tags utilities 
	
	public static void writeMaterialTraitsToNBT (MaterialsMod material, NBTTagCompound cmp) {
		NBTTagList tagList = new NBTTagList () ;
		
		for (ITrait trait: material.getTraits()) {
			NBTTagCompound materialTag = new NBTTagCompound () ;
			materialTag.setString(IModifiable.IDENTIFIER, trait.getIdentifier()) ;
			tagList.appendTag(materialTag) ;
		}
		
		cmp.setTag("materialTraits", tagList);
	}
	
	public static List<String> readMaterialTraitsFromNBT (NBTTagCompound cmp) {
		List<String> list = Lists.newLinkedList() ;
		
		NBTTagList tagList = cmp.getTagList("materialTraits", 10) ;
		
		for (int i = 0; i < tagList.tagCount(); i ++ ) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i) ;
			list.add(tag.getString(IModifiable.IDENTIFIER)) ;			
		}
		
		return list ;
	}
	
	public static List<ITrait> getModifiersFromTag (NBTTagList tagList) {
		List <ITrait> list = Lists.newLinkedList() ;
		
		for (int i = 0; i < tagList.tagCount(); i ++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i) ;
			
			list.add(ModTraitsModifiersRegistry.getTraitFromIdentifier(tag.getString(IModifiable.IDENTIFIER))) ;
		}
		
		return list ;
	}
	
	/**
	 * Uses MineCraft 1.9.0 armour damage reduction formula to calculate the percentage of damage reduced
	 * 
	 * @param damage 	the raw damage to be dealt
	 * @param armourValue
	 * @param toughness		diamond armour has 2 for each piece, and 0 for the rest
	 * @return	percentage of damage reduced
	 */
	public static double calculateArmourReduction (double damage, float armourValue, float toughness) {
		
		return ( 1 - Math.min( 20, Math.max( armourValue / 5, armourValue - damage / ( 2 + toughness / 4 ) ) ) / 25 ) ;
	}
	
	/**
	 * Return all entites within the given distance from the player
	 * 
	 * @param player
	 * @param distance
	 */
	public static List<Entity> getEntitiesAroundPlayer (EntityPlayer player, double distance) {
		World world = player.getEntityWorld() ;
		
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expandXyz(distance)) ;
		
		return entities ;
	}
	
	/**
	 * Gets the list of modifiers/traits associated only to its materials from a IModifiable
	 * 
	 */
	public static List<ITrait> getTraitsListMaterials (ItemStack stack) {
		return getModifiersList (stack, true, false) ;
	}
	
	/**
	 * Returns all traits/modifiers associated to this armour
	 * Does not take into account if said trait should be active or not
	 * 
	 */
	public static List<ITrait> getModifiersListAll (ItemStack stack) {
		List<ITrait> traits = Lists.newLinkedList() ;
		
		traits.addAll(getTraitsModifiersList(stack)) ;
		traits.addAll(getTraitsListArmourSet(stack)) ;
		
		return traits ;
	}
	
	public static List<ITrait> getTraitsListArmourSet (ItemStack stack) {
		return getModifiersList (stack, false, true) ;
	}
	
	/**
	 * Gets the list of traits/modifiers from an armour excluding the ones associated to the armourset.
	 */
	public static List<ITrait> getTraitsModifiersList (ItemStack stack) {
		List<ITrait> result = Lists.newLinkedList() ;
		
		result.addAll( getTraitsListMaterials (stack) ) ;
		result.addAll( getModifiersFromArmour (stack) ) ;		
		
		return result ;
	}
	
	/**
	 * Gets the list of modifiers from the armour
	 */
	public static List<ITrait> getModifiersFromArmour (ItemStack stack) {
		List<ITrait> result = Lists.newLinkedList() ;
		
		NBTTagCompound tag = stack.getTagCompound() ;
		
		if (tag.hasKey(IModifier.MODIFIERS)) {
			NBTTagList tagList = tag.getTagList(IModifier.MODIFIERS, 10) ;
			
			for (int i = 0; i < tagList.tagCount(); i++ ) {
				NBTTagCompound cmp = tagList.getCompoundTagAt(i) ;
				
				result.add(ModTraitsModifiersRegistry.getTraitFromIdentifier( cmp.getString(IModifiable.IDENTIFIER)) ) ;
			}
		}
		
		return result ;
	}
	
	/**
	 * Gets the modifiers list from the given itemstack. Only applicable to items that implements IModifiable. Otherwise it returns null.
	 * 
	 * @param stack
	 * @param getMaterials		If set to true, only traits/modifiers associated to materials are included.
	 * @param getArmourSet		If set to true, only traits/modifiers associated to the armour set are included.
	 * 
	 */
	public static List<ITrait> getModifiersList (ItemStack stack, boolean getMaterials, boolean getArmourSet) {
		
		if ( !(stack.getItem() instanceof IModifiable) ) {
			return null ;
		}
		
		NBTTagCompound tag = stack.getTagCompound() ;
		List<ITrait> list = Lists.newLinkedList() ;
		
		NBTTagList tagList ;
		
		if (getMaterials) {
		
			tagList = tag.getTagList(ITrait.MATERIAL_TRAITS, 10) ;
		
			if (tagList == null) {
				return null ;
			}
			
			list.addAll( getMaterialsTraitsFromNBT (tagList) ) ;
		
		}
		
		if (getArmourSet) {
			tagList = tag.getTagList(ITrait.ARMOUR_SET_TRAITS, 10) ;
			
			if (tagList == null) {
				return null ;
			}
			
			list.addAll(getModifiersFromTag (tagList)) ;
		}
		
		return list ;
	}
	
	/** 
	 * Given a NBTTagList, returns a list of ITrait objects represented by the taglist
	 * 
	 * @param tagList
	 * @return
	 */
	public static List<ITrait> getMaterialsTraitsFromNBT (NBTTagList tagList) {
		List<ITrait> result = Lists.newLinkedList() ;
		
		for (int i = 0; i < tagList.tagCount(); i++ ) {
			NBTTagCompound thisTag = tagList.getCompoundTagAt(i) ;
			
			List<String> traits = readMaterialTraitsFromNBT(thisTag) ;
			
			for (String identifier : traits) {
				ITrait trait = ModTraitsModifiersRegistry.getTraitFromIdentifier(identifier) ;
			
				result.add(trait) ;
			}
		}
		
		return result ;
	}
	
	/**
	 * Given an itemstack of ItemModArmour, returns a list of ITrait objects representing its traits
	 * This function will take into account armour sets and returns the corresponding traits if its active.
	 * Should be used to find out the list of trait active for any armour piece at any given time.
	 * 
	 * @param player
	 * @param armour
	 * @return
	 */
	public static List<ITrait> getArmourTraits (EntityPlayer player, ItemStack armour) {
		List<ITrait> result = Lists.newLinkedList() ;
		
		result.addAll(getTraitsModifiersList(armour)) ;
		
		if (armour.getItem() instanceof ItemModArmour) {
			
			ItemModArmour modArmour = (ItemModArmour) armour.getItem() ;
			
			if (modArmour.hasArmourSet(player)) {
				result.addAll(getTraitsListArmourSet(armour)) ;
			}
			
		}
		
		return result ;
	}
	
	/**
	 * Format a trait's identifier for display; the original identifier is not modified.
	 * 
	 * @param trait		the trait object.
	 * @return	the formatted string
	 */
	public static String formatIdentifier (ITrait trait) {
		String s, identifier ;
		identifier = trait.getIdentifier() ;
		
		s = "" + trait.getColour() ;
		
		s += identifier.substring(0, 1).toUpperCase() + identifier.substring(1) ;
		
		return s ;
	}
	
	/**
	 * Call to repair any armour to fire the onrepair event.
	 * 
	 * @param player
	 * @param stack		The armour itemstack
	 * @param amount	The amount to repair. This might be modified later by traits.
	 */
	public static void repairArmour (EntityPlayer player, ItemStack stack, int amount) {
		
		ModifierEvents.OnRepair.fireEvent(player, stack, amount) ;
		
		int repairAmt = Math.min(amount, stack.getItemDamage()) ;
		stack.setItemDamage(stack.getItemDamage() - repairAmt) ;
	}
	
	/**
	 * Adds a vanilla enchantment to the armour. This used for set bonuses as ModArmour cannot be enchanted in a vanilla enchantment table
	 * 
	 * @param armour	The itemstack of the armour
	 * @param ench		The Enchantment
	 * @param level		The level of the enchantment
	 */
	public static void addVanillaEnchantment (ItemStack armour, Enchantment ench, int level) {
		armour.addEnchantment(ench, level);
	}
	
	/**
	 * This removes ALL vanilla enchantments from the armour. Again used only for ModArmour. If used on a vanilla item, every enchantment will be wiped!
	 *
	 * @param armour 	The itemstack of the armour
	 */
	public static void removeVanillaEnchantment (ItemStack armour) {
		NBTTagCompound tag = armour.getTagCompound() ;
		
		if (tag.hasKey("ench")) {
			tag.removeTag("ench") ;
		}
	}

}
