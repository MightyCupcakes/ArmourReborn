package teamOD.armourReborn.common.item.equipment;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.leveling.ILevelable;
import teamOD.armourReborn.common.lib.LibMisc;

public class ItemModArmour extends ItemArmor implements ISpecialArmor, ILevelable  {
	
	public EntityEquipmentSlot type;
	
	public static final ArmorMaterial paperArmourMat = EnumHelper.addArmorMaterial("PAPER", "paper", 1, new int[] {1,2,3,2}, 1, SoundEvents.item_armor_equip_generic) ;
	public static final ArmorMaterial ironArmourMat = EnumHelper.addArmorMaterial("IRON", "modIron", 16, new int[] {2,4,4,2}, 1, SoundEvents.item_armor_equip_iron) ;
	public static final ArmorMaterial steelArmourMat = EnumHelper.addArmorMaterial("STEEL", "steel", 20, new int[] {4,4,4,2}, 1, SoundEvents.item_armor_equip_gold) ;
	public static final ArmorMaterial aluminiumArmourMat = EnumHelper.addArmorMaterial("ALUMINIUM", "aluminium", 34, new int[] {3,8,6,3}, 1, SoundEvents.item_armor_equip_diamond) ;
	
	public ItemModArmour (EntityEquipmentSlot type, String name, ArmorMaterial mat) {
		super (mat, 0, type) ;
		
		this.type = type;
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
	
	@Override
	public void addBaseTags (NBTTagCompound tag) {
		tag.setInteger(TAG_LEVEL, 1) ;
		tag.setInteger(TAG_EXP, 0) ;
	}
	
	@Override
	public int getLevel(NBTTagCompound tags) {
		return tags.getInteger(TAG_LEVEL) ;
	}

	@Override
	public int getExp(NBTTagCompound tags) {
		return tags.getInteger(TAG_EXP) ;
	}

	@Override
	public boolean hasLevel(NBTTagCompound tags) {
		return tags.hasKey(TAG_LEVEL) ;
	}

	@Override
	public boolean hasExp(NBTTagCompound tags) {
		return tags.hasKey(TAG_EXP) ;
	}

	@Override
	public boolean isMaxLevel(NBTTagCompound tags) {
		return tags.getInteger(TAG_LEVEL)  == 10 ;
	}

	@Override
	public void setExp(ItemStack armour, EntityPlayer player, int armourExp) {
		NBTTagCompound tag = armour.getTagCompound().getCompoundTag("ArmourReborn") ;
		
		if ( !hasLevel (tag) || !hasExp (tag) ) {
			return ;
		}
		
		if ( isMaxLevel (tag) ) return ;
		
		tag.setInteger(TAG_EXP, armourExp);
		
	}

	@Override
	public void addExp(ItemStack armour, EntityPlayer player, int armourExp) {
		NBTTagCompound tag = armour.getTagCompound().getCompoundTag("ArmourReborn") ;
		
		if ( !hasLevel (tag) || !hasExp (tag) ) {
			return ;
		}
		
		if ( isMaxLevel (tag) ) return ;
		
		
		setExp (armour, player, tag.getInteger(TAG_EXP) + armourExp) ;
	}

	@Override
	public void levelUpArmour(ItemStack armour, EntityPlayer player) {
		NBTTagCompound tag = armour.getTagCompound().getCompoundTag("ArmourReborn") ;
		
		tag.setInteger(TAG_LEVEL, tag.getInteger(TAG_LEVEL) + 1 ) ;
		tag.setInteger(TAG_EXP, 0) ;
		
	}	
	
}
