package teamOD.armourReborn.common.tweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamOD.armourReborn.common.lib.LibMisc;

public class NerfVanillaArmours {
	@SubscribeEvent
	public void onHurt (LivingHurtEvent event) {
		if ( ! (event.getEntity() instanceof EntityPlayer) ) {
			return ;
		}
		
		EntityPlayer player = (EntityPlayer) event.getEntity() ;
		
		boolean hasVanilla = false ;
		
		// I give up... if the player wears even one piece of vanilla armor, he shall receive pure damage.
		for (ItemStack stack : player.getArmorInventoryList()) {
			if (stack == null) continue ;
			
			int itemID = Item.getIdFromItem(stack.getItem()) ;
			
			if (itemID >= 298 && itemID <= 317) {
				hasVanilla = true ;
				break ;
			}
		}
		
		if (hasVanilla) {
			event.getSource().setDamageBypassesArmor() ;
		}
	}
	
	@SubscribeEvent
	public void onToolTip (ItemTooltipEvent event) {
		
		if (event.getEntityPlayer() == null) return ;
		
		if (event.getItemStack().getItem() instanceof ItemArmor) {
			int itemID = Item.getIdFromItem(event.getItemStack().getItem()) ;
			
			if (itemID >= 298 && itemID <= 317) {
				event.getToolTip().add(TextFormatting.DARK_RED + "This armour is useless! Equip at your own risk!") ;
			}
		}
	}
	
}
