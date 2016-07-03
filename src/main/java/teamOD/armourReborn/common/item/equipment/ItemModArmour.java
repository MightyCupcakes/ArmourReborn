package teamOD.armourReborn.common.item.equipment;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.crafting.MaterialsMod;
import teamOD.armourReborn.common.leveling.ILevelable;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.lib.LibUtil;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.IModifier;
import teamOD.armourReborn.common.modifiers.ITrait;

public abstract class ItemModArmour extends ItemArmor implements ISpecialArmor, ILevelable, IModifiable  {
	
	public EntityEquipmentSlot type;
	
	// Armour items in this set. Index 0 should contain the helmet, 1 the chest, 2 the leggings and 3 the boots
	public ItemStack[] armourSet ;
	public int armourModifier = 1 ;
	
	public ItemModArmour (EntityEquipmentSlot type, String name, ArmorMaterial mat) {
		super (mat, 0, type) ;
		
		this.type = type;
		this.setNoRepair() ;
		
		setCreativeTab(ArmourRebornCreativeTab.INSTANCE);
		GameRegistry.register(this, new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(name);
		
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
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
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
	public void levelUpArmour(ItemStack armour, EntityPlayer player) {
		NBTTagCompound tag = armour.getTagCompound() ;
		
		tag.setInteger(TAG_LEVEL, tag.getInteger(TAG_LEVEL) + 1 ) ;
		tag.setInteger(TAG_EXP, 0) ;
		
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
	public ItemStack buildItem(List<MaterialsMod> materials) {
		ItemStack armour = new ItemStack (this) ;
		armour.setTagCompound( buildItemTag (materials) );
		
		return armour ;
	}
	
	public NBTTagCompound buildItemTag (List<MaterialsMod> materials) {
		NBTTagCompound tag = new NBTTagCompound () ;
		
		// Levels
		this.addBaseTags(tag) ;
		
		// Modifiers and traits
		NBTTagList modTag = new NBTTagList () ;
				
		// Materials Traits
		for (MaterialsMod material : materials) {
			NBTTagCompound materialTag = new NBTTagCompound () ;
			material.writeToNBT(materialTag) ;
			modTag.appendTag(materialTag) ;
		}
		
		// Armour type specific traits		
		for (ITrait trait: getArmourTypeTrait() ) {
			NBTTagCompound armourTypeTag = new NBTTagCompound () ;
			armourTypeTag.setString(IDENTIFIER, trait.getIdentifier()) ;
			modTag.appendTag(armourTypeTag) ;
		}
		
		tag.setTag("traits", modTag);
		return tag ;
	}
	
}
