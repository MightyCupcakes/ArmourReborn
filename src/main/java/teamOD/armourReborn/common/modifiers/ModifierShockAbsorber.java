package teamOD.armourReborn.common.modifiers;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ModifierShockAbsorber extends AbstractModifier {
	
	public static ImmutableList<Integer> ABSORBED = ImmutableList.of(0, 5, 10, 15) ;
	
	public ModifierShockAbsorber (int level, ItemStack item) {
		super ("shock Absorber", level, TextFormatting.DARK_AQUA, item) ;
	}
	
	@Override
	public float onPlayerFalling (EntityPlayer player, ItemStack armour, float distance) {
		float result = distance - ABSORBED.get(getLevel()) ;
		
		return (result > 0) ? result : 0F ;
	}
	
	@Override
	public boolean canApplyToEquipment (ItemStack armour) {
		if (armour.getItem() instanceof ItemArmor) {
			ItemArmor armourPiece = (ItemArmor) armour.getItem() ;
			
			return armourPiece.armorType == EntityEquipmentSlot.FEET ;
		}
		
		return false ;
	}

}
