package teamOD.armourReborn.common.tweaks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibUtil;

public class AddDropsToMobs {
	
	@SubscribeEvent
	public void onMonsterDrop (LivingDropsEvent event) {
		if ( !(event.getSource().getEntity() instanceof EntityPlayer) ) {
			return ;
		}
		
		float rand = LibUtil.getRandomFloat() ;
		
		if (rand <= 0.2F) {
			ItemStack newDrop = new ItemStack (ModItems.MOD_MODIFIERS_MATERIALS, 1, 0) ;
			event.getDrops().add(new EntityItem (event.getEntity().worldObj, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, newDrop)) ;
		}
	}

}
