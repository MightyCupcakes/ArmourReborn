package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.leveling.ILevelable;
import teamOD.armourReborn.common.leveling.ModLevels;
import teamOD.armourReborn.common.leveling.ModLevels.LevelInfo;
import teamOD.armourReborn.common.lib.LibItemStats;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.IModifier;
import teamOD.armourReborn.common.modifiers.ITrait;
import teamOD.armourReborn.common.modifiers.ModTraitsModifiersRegistry;

public abstract class ItemModArmour extends ItemArmor implements ISpecialArmor, ILevelable, IModifiable  {
	
	public EntityEquipmentSlot type;
	
	// Armour items in this set. Index 0 should contain the helmet, 1 the chest, 2 the leggings and 3 the boots
	public ItemStack[] armourSet ;
	
	protected int modifiersSlot = LibItemStats.DEFAULT_MODIFIER_SLOTS ; 
	
	public static double armourModifier = 1 ;
	public static double durabilityModifier = 1 ;
	
	public ItemModArmour (EntityEquipmentSlot type, String name, ArmorMaterial mat, int index) {
		super (mat, index, type) ;
		
		this.type = type;
		this.setNoRepair() ;
		
		setCreativeTab(ArmourRebornCreativeTab.INSTANCE);
		GameRegistry.register(this, new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(LibUtil.getPrefix(name));
		
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,
			int slot) {
		
		if (source.isUnblockable()) {
			return new ArmorProperties (0, 0, 0) ;
		}
		
		return new ArmorProperties (0, damageReduceAmount / 25D, armor.getMaxDamage() - armor.getItemDamage() + 1) ;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return damageReduceAmount ;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (itemStack.getItem() instanceof ILevelable) {
			levelingUpdate (itemStack, player) ;
		}
		
		if (itemStack.getItem() instanceof IModifiable) {
			for ( ITrait traits : LibUtil.getModifiersListAll(itemStack) ) {
				traits.onArmorTick(world, player, itemStack) ;
			}
		}
		
		if ( hasArmourSet (player) ) {
			addArmourSetEnchantments (itemStack) ;
		
		} else {
			
			if (itemStack.getItem() instanceof ItemModArmour) {
				LibUtil.removeVanillaEnchantment(itemStack);
			}
		}
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		
		for (ITrait modifier : LibUtil.getModifiersListMaterials(stack)) {
			damage = modifier.onDamage(stack, damage, damage, entity);
		}
		
		stack.damageItem(damage, entity); 
	}
	
	public boolean hasArmourSet (EntityPlayer player) {
		if (armourSet == null) return false ;
		
		return hasArmourSetItem (player, 0) && hasArmourSetItem (player, 1) && hasArmourSetItem (player, 2) && hasArmourSetItem (player, 3) ;
		
	}
	
	public boolean hasArmourSetItem (EntityPlayer player, int slot) {
		ItemStack stack = player.inventory.armorItemInSlot(slot);
		
		if (stack == null) return false ;
		
		switch (slot) {
		
		case 0 :
			return stack.getItem() == armourSet[0].getItem() ;
		case 1:
			return stack.getItem() == armourSet[1].getItem() ;
		case 2:
			return stack.getItem() == armourSet[2].getItem() ;
		case 3:
			return stack.getItem() == armourSet[3].getItem() ;
 		}
		
		return false ;
	}
	
	protected abstract void addArmourSetEnchantments (ItemStack armour) ;
	
	
	@Override
	public void addBaseTags (NBTTagCompound tag) {
		tag.setInteger(TAG_LEVEL, 1) ;
		tag.setInteger(TAG_EXP, 0) ;
	}
	

	@Override
	public boolean isMaxLevel(NBTTagCompound tags) {
		return tags.getInteger(TAG_LEVEL)  == MAX_LEVEL ;
	}

	@Override
	public void setExp(ItemStack armour, EntityPlayer player, int armourExp) {
		NBTTagCompound tag = armour.getTagCompound() ;
		
		if ( !tag.hasKey(TAG_EXP) || !tag.hasKey(TAG_LEVEL)) {
			return ;
		}
		
		if ( isMaxLevel (tag) ) return ;
		
		tag.setInteger(TAG_EXP, armourExp);
		
	}

	@Override
	public void addExp(ItemStack armour, EntityPlayer player, int armourExp) {
		NBTTagCompound tag = armour.getTagCompound() ;
		
		if ( !tag.hasKey(TAG_LEVEL) || !tag.hasKey(TAG_EXP) ) {
			return ;
		}
		
		if ( isMaxLevel (tag) ) return ;
		
		
		setExp (armour, player, tag.getInteger(TAG_EXP) + armourExp) ;
	}
	
	@Override
	public int getLevel (ItemStack armour) {
		NBTTagCompound tag = armour.getTagCompound() ;
		
		return tag.getInteger(TAG_LEVEL) ;
	}
	
	public int getExp (ItemStack armour) {
		NBTTagCompound tag = armour.getTagCompound() ;
		
		return tag.getInteger(TAG_EXP) ;
	}
	
	protected void levelingUpdate (ItemStack armour, EntityPlayer player) {
		
		if ( getExp (armour) >= ModLevels.getLevelInfo(getLevel(armour)).getExpNeeded() ) {
			levelUpArmour (armour, player) ;
		}
	}

	@Override
	public void levelUpArmour(ItemStack armour, EntityPlayer player) {
		NBTTagCompound tag = armour.getTagCompound() ;
		
		if (isMaxLevel(tag)) return ;
		
		LevelInfo info = ModLevels.getLevelInfo (getLevel(armour)) ;
		
		if (info.getTraitIdentifiers() != null) {
			for (ITrait trait : info.getTraitIdentifiers()) {
				addModifier (armour, trait) ;
			}
		}
		
		tag.setInteger(TAG_EXP, tag.getInteger(TAG_EXP) - info.getExpNeeded() ) ;
		tag.setInteger(TAG_LEVEL, getLevel(armour) + 1 ) ;
		
		player.addChatComponentMessage(new TextComponentString ("Your armour mastery level has increased! It is now " + info.getSkillString()));
		
	}
	
	@Override
	public boolean updateItemStackNBT(NBTTagCompound cmp) {
		if ( cmp.hasKey("traits") ) {
			// TODO: Rebuild armour from NBT
		}
		return true ;
	}

	public abstract List<ITrait> getArmourTypeTrait () ;
	
	@Override
	public void addModifier (ItemStack armour, IModifier modifier) {
		addModifier (armour, modifier) ;
	}
	
	/**
	 * For traits provided by leveling up of armour.
	 * @param armour
	 * @param modifier
	 */
	private void addModifier (ItemStack armour, ITrait modifier) {
		NBTTagCompound tag = armour.getTagCompound() ;
		NBTTagList modTag ;
		
		if (tag.hasKey(IModifier.MODIFIERS)) {
			modTag = tag.getTagList(IModifier.MODIFIERS, 10) ;
		} else {
			modTag = new NBTTagList () ;
		}
		
		NBTTagCompound newMod = new NBTTagCompound () ;
		newMod.setString(IDENTIFIER, modifier.getIdentifier()) ;
		modTag.appendTag(newMod) ;
		
		tag.setTag(IModifier.MODIFIERS, modTag);
	}
	
	@Override
	public ItemStack buildItem(List<MaterialsMod> materials) {
		ItemStack armour = new ItemStack (this) ;
		armour.setTagCompound( buildItemTag (materials) );
		
		return armour ;
	}
	
	public NBTTagCompound buildItemTag (List<MaterialsMod> materials) {
		NBTTagCompound tag = new NBTTagCompound () ;
		NBTTagList modTag ;
		
		// Levels
		this.addBaseTags(tag) ;
		
		// Modifiers and traits
		modTag = new NBTTagList () ;
				
		// Materials Traits
		for (MaterialsMod material : materials) {
			NBTTagCompound materialTag = new NBTTagCompound () ;
			material.writeToNBT(materialTag) ;
			modTag.appendTag(materialTag) ;
		}
		
		tag.setTag(ITrait.MATERIAL_TRAITS, modTag);
		
		// Armour type specific traits
		modTag = new NBTTagList () ;
		
		for (ITrait trait: getArmourTypeTrait() ) {
			NBTTagCompound armourTypeTag = new NBTTagCompound () ;
			armourTypeTag.setString(IDENTIFIER, trait.getIdentifier()) ;
			modTag.appendTag(armourTypeTag) ;
		}
		
		tag.setTag(ITrait.ARMOUR_SET_TRAITS, modTag);
		
		return tag ;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.COMMON ;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		// Leveling stuff
		LevelInfo level = ModLevels.getLevelInfo(getLevel(stack)) ;
		
		tooltip.add(level.getColour() + level.getSkillString()) ;
		
		// Modifiers and traits
		NBTTagCompound tag = stack.getTagCompound() ;
		NBTTagList thisList ;
		
		// Materials Traits
		thisList = tag.getTagList(ITrait.MATERIAL_TRAITS, 10) ;
		
		for (int i = 0; i < thisList.tagCount(); i++ ) {
			NBTTagCompound thisTag = thisList.getCompoundTagAt(i) ;
			ITrait trait = ModTraitsModifiersRegistry.getTraitFromIdentifier(thisTag.getString(IDENTIFIER)) ;
			
			tooltip.add(LibUtil.formatIdentifier(trait)) ;
		}
		
		// Armour set specific traits
		if (stack.getItem() instanceof ItemModArmour) {
			ItemModArmour armour = (ItemModArmour) stack.getItem() ;
			
			if (armour.hasArmourSet(playerIn)) {
				thisList = tag.getTagList(ITrait.ARMOUR_SET_TRAITS, 10) ;
				
				for (int i = 0; i < thisList.tagCount(); i++ ) {
					NBTTagCompound thisTag = thisList.getCompoundTagAt(i) ;
					ITrait trait = ModTraitsModifiersRegistry.getTraitFromIdentifier(thisTag.getString(IDENTIFIER)) ;
					
					tooltip.add(LibUtil.formatIdentifier(trait)) ;
				}
			}
		}
		
		// Additional modifiers (if any)
		if (tag.hasKey(IModifier.MODIFIERS)) {
			thisList = tag.getTagList(IModifier.MODIFIERS, 10) ;
			
			for (int i = 0; i < thisList.tagCount(); i++ ) {
				NBTTagCompound thisTag = thisList.getCompoundTagAt(i) ;
				ITrait trait = ModTraitsModifiersRegistry.getTraitFromIdentifier(thisTag.getString(IDENTIFIER)) ;
				
				tooltip.add(LibUtil.formatIdentifier(trait)) ;
			}
		}
	}
}
