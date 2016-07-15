package teamOD.armourReborn.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemAghaDust extends ItemMod {

	public ItemAghaDust(String name) {
		super(name) ;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add("Predating the shattering, this artifact is said to be a small fragment of the powers of a powerful magus") ;
	}
}
