package teamOD.armourReborn.common.item.equipment;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.crafting.ModMaterials;
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
	
	public static final String TOUGHNESS = "toughnessValue" ;
	public static final String ARMOUR_VALUE = "armourValue" ;
	
	// Vanilla stuff
	public static UUID[] field_185084_n ;
	//public static final UUID[] field_185084_n = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	
	public EntityEquipmentSlot type;
	
	/** Armour items in this set. Index 0 should contain the helmet, 1 the chest, 2 the leggings and 3 the boots */
	public ItemStack[] armourSet ;
	
	protected Map<Enchantment, Integer> armourSetEnchantments = Collections.emptyMap() ;
	protected MaterialsMod materials ;
	
	public ItemModArmour (EntityEquipmentSlot type, String name, ArmorMaterial mat, int index) {
		super (mat, index, type) ;
		
		this.type = type;
		this.armourSet = new ItemStack[4] ;
		this.setNoRepair() ;
		
		field_185084_n = ReflectionHelper.getPrivateValue(ItemArmor.class, this, "field_185084_n") ;
		
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
		
		float toughness = 0;
		float armourValue = damageReduceAmount;
		
		if (armor.getTagCompound() != null) {
			toughness = armor.getTagCompound().getFloat(TOUGHNESS) ;		
			armourValue = armor.getTagCompound().getFloat(ARMOUR_VALUE) ;
		}
		
		return new ArmorProperties (0, LibUtil.calculateArmourReduction(damage, armourValue, toughness), armor.getMaxDamage() - armor.getItemDamage() + 1) ;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (armor.getTagCompound().hasKey(ARMOUR_VALUE)) ? (int) armor.getTagCompound().getFloat(ARMOUR_VALUE) : damageReduceAmount ;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		// NO-OP for now
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
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
		
		for (ITrait modifier : LibUtil.getTraitsListMaterials(stack)) {
			damage = modifier.onDamage(stack, damage, damage, entity);
		}
		
		stack.damageItem(damage, entity); 
	}
	
	public boolean hasArmourSet (EntityPlayer player) {
		if (armourSet == null) return false ;
		
		return hasArmourSetItem (player, 0) && hasArmourSetItem (player, 1) && hasArmourSetItem (player, 2) && hasArmourSetItem (player, 3) ;
		
	}
	
	public boolean hasArmourSetItem (EntityPlayer player, int slot) {
		ItemStack stack = player.inventory.armorInventory[3 - slot] ;
		
		if (stack == null) return false ;
		
		switch (slot) {
		
		case 0:
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
	
	protected void addArmourSetEnchantments (ItemStack armour) {
		if (armour.isItemEnchanted()) return ;
		
		for (Enchantment ench : armourSetEnchantments.keySet()) {
			LibUtil.addVanillaEnchantment(armour, ench, armourSetEnchantments.get(ench)) ;
		}
	}
	
	
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
		
		levelingUpdate (armour, player) ;
		
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
		
		int expNeededToLevel = ModLevels.getLevelInfo (getLevel(armour)).getExpNeeded() ;
		LevelInfo info = ModLevels.getLevelInfo (getLevel(armour) + 1) ;
		
		if (info.getTraitIdentifiers() != null) {
			for (ITrait trait : info.getTraitIdentifiers()) {
				addModifier (armour, trait, false, true) ;
			}
		}
		
		tag.setFloat(ARMOUR_VALUE, tag.getFloat(ARMOUR_VALUE) + info.getArmourValue());
		tag.setFloat(TOUGHNESS, tag.getFloat(TOUGHNESS) + info.getToughness()) ;
		tag.setInteger(MODIFIER_SLOTS, tag.getInteger(MODIFIER_SLOTS) + info.getNumModifierSlots());
		tag.setInteger(TAG_EXP, tag.getInteger(TAG_EXP) - expNeededToLevel ) ;
		tag.setInteger(TAG_LEVEL, getLevel(armour) + 1 ) ;
		
		player.addChatComponentMessage(new TextComponentString ("Your armour mastery level has increased! It is now " + info.getSkillString()));
		
		// For cheaters who can somehow level up their armour more than once in one update
		levelingUpdate (armour, player) ;
		
	}
	
	@Override
	public boolean updateItemStackNBT(NBTTagCompound cmp) {
		if ( cmp.hasKey("traits") ) {
			// TODO: Rebuild armour from NBT
		}
		return true ;
	}

	public abstract List<ITrait> getArmourTypeTrait () ;
	
	/**
	 * Add the given modifier to the armour in the itemstack
	 */
	@Override
	public void addModifier (ItemStack armour, IModifier modifier) {
		addModifier (armour, modifier, true, false) ;
	}
	
	/**
	 * For traits provided by leveling up of armour.
	 * @param armour
	 * @param modifier
	 * @param requireSlot	if set to true, this modifier uses up a slot when added.
	 * @param forced	if set to true, the modifier will be added if it belongs to the same trait family
	 */
	public void addModifier (ItemStack armour, ITrait modifier, boolean requireSlot, boolean forced) {
		NBTTagCompound tag = armour.getTagCompound() ;
		NBTTagList modTag ;
		
		// Check for slots left
		if (requireSlot) {
			
			if (tag.hasKey(MODIFIER_SLOTS) && tag.getInteger(MODIFIER_SLOTS) > 0) {
				tag.setInteger(MODIFIER_SLOTS, tag.getInteger(MODIFIER_SLOTS) - 1);
			
			} else {
				// No slots left - abort! abort!
				return ;
			}
		}
		
		if (tag.hasKey(IModifier.MODIFIERS)) {
			modTag = tag.getTagList(IModifier.MODIFIERS, 10) ;
		} else {
			modTag = new NBTTagList () ;
		}
		
		// Check if this modifier can be added to this armour type
		if (!modifier.canApplyToEquipment(armour)) {
			return ;
		}
		
		// Check if said modifier can co-exist with its peers
		for (ITrait trait: LibUtil.getModifiersListAll (armour)) {
			if ( !trait.canApplyTogether(modifier) && !forced) {
				LibUtil.LogToFML(1, "Modifier error: %s and %s cannot co-exist with each other!", trait.getIdentifier(), modifier.getIdentifier()) ;

				return ;
			} 

			else if (!trait.canApplyTogether(modifier) && forced) {
				if (trait.getTraitFamily().equals(modifier.getTraitFamily())) {
					
					for (int i = 0; i < modTag.tagCount(); i ++ ) {
						NBTTagCompound nbt = modTag.getCompoundTagAt(i) ;
						
						if ( nbt.getString(IDENTIFIER).equals(trait.getIdentifier()) ) {
							modTag.removeTag(i) ;
							break ;
						}
					}
				}
			}
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
		
		// toughness and armour
		tag.setFloat(ARMOUR_VALUE, damageReduceAmount) ;
		tag.setFloat(TOUGHNESS, 0) ;
		
		// Modifiers and traits
		tag.setInteger(MODIFIER_SLOTS, LibItemStats.DEFAULT_MODIFIER_SLOTS);
		
		modTag = new NBTTagList () ;
				
		// Materials Traits
		for (MaterialsMod material : materials) {
			NBTTagCompound materialTag = new NBTTagCompound () ;
			LibUtil.writeMaterialTraitsToNBT(material, materialTag) ;
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
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		
		boolean shift = LibUtil.isShiftKeyDown() ;
		
		if (stack.getTagCompound() == null) return ;
		
		// Leveling stuff
		LevelInfo level = ModLevels.getLevelInfo(getLevel(stack)) ;
		
		tooltip.add("") ; // A empty line so it looks nice
		tooltip.add(level.getColour() + level.getSkillString()) ;
		
		tooltip.add("") ; // More empty lines
		
		// Modifiers and traits
		NBTTagCompound tag = stack.getTagCompound() ;
		NBTTagList thisList ;
		
		// Materials Traits
		thisList = tag.getTagList(ITrait.MATERIAL_TRAITS, 10) ;
		
		for (ITrait trait: LibUtil.getMaterialsTraitsFromNBT(thisList) ) {
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
		
		tooltip.add("") ; // more empty spaces!
		
		if (!shift) {
			tooltip.add(TextFormatting.WHITE + "Press shift to show more information.") ;
		
		} else {			
			tooltip.add (TextFormatting.WHITE + "Modifier slots: " + LibUtil.getStackModifierSlots(stack)) ;
			tooltip.add (TextFormatting.WHITE + "Durability: " + LibUtil.getItemCurrentDurability(stack) + "/" + stack.getMaxDamage()) ;
			tooltip.add (TextFormatting.WHITE + "Exp: " + this.getExp(stack) + "/" + ModLevels.getLevelInfo(this.getLevel(stack)).getExpNeeded() ) ;
		}
		
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		ItemStack armour = buildItem (ImmutableList.of(materials)) ;
		subItems.add(armour) ; 
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {

		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create() ;

		if (slot == this.armorType && stack.getItem() instanceof ItemModArmour && stack.getTagCompound() != null) {
			NBTTagCompound tag = stack.getTagCompound() ;
			float armour = tag.getFloat(ARMOUR_VALUE) ;
			
			multimap.put(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName(), new AttributeModifier(field_185084_n[slot.getIndex()], "Armor modifier", (double)armour, 0));
		}

		return multimap;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false ;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.COMMON ;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return false ;
	}
}
