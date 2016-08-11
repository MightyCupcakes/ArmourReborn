package teamOD.armourReborn.common.modifiers;

import java.util.List;

import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

public class ModifierFrostbite extends AbstractModifier {

	public ModifierFrostbite (ItemStack item) {
		super("frostbite", TextFormatting.AQUA, item);
	}
	
	@Override
	public void emitAuraEffect (EntityPlayer player, ItemStack item) {
		ItemArmor armour = (ItemArmor) item.getItem() ;
		
		if ( armour.armorType == EntityEquipmentSlot.FEET && !player.isSneaking() ) {
			EnchantmentFrostWalker.freezeNearby(player, player.worldObj, new BlockPos(player), 2);
		} else {
			
			List<Entity> entities = LibUtil.getEntitiesAroundPlayer(player, 4) ;
			
			for (Entity entity : entities) {
				if (entity instanceof EntityMob) {
					
					EntityMob mob = (EntityMob) entity ;
					mob.addPotionEffect(new PotionEffect (MobEffects.SLOWNESS, 8 * 20, 1)) ;
				}
			}
		}
	}

}
