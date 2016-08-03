package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;

public class ModifierHeart extends AbstractModifier {
	
	public ModifierHeart (ItemStack item) {
		super ("heart Of Tarrasque", TextFormatting.DARK_RED, item) ;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		int foodLevel = player.getFoodStats().getFoodLevel() ;
		
		if (foodLevel > 0 && foodLevel < 18 && player.shouldHeal() && player.ticksExisted % 80 == 0) {
			player.heal(1F);
		}
	}
	
	@Override
	public boolean canApplyToEquipment (ItemStack armour) {
		
		if (armour.getItem() instanceof ItemArmor) {
			ItemArmor armourPiece = (ItemArmor) armour.getItem() ;
			
			return armourPiece.armorType == EntityEquipmentSlot.CHEST ;
		}
		
		return false ;
	}
}
