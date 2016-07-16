package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ModifierUnburnt extends AbstractModifier {
	
	public ModifierUnburnt () {
		super ("unburnt", TextFormatting.RED) ;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (player.isBurning()) {
			player.extinguish() ;
		}
	}
}
