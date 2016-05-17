package teamOD.armourReborn.common.tweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
			ResourceLocation name = stack.getItem().getRegistryName() ;
			String mod = name.toString().split(":")[0] ;
			
			if (mod == "minecraft") {
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
		if (event.getItemStack().getItem() instanceof ItemArmor) {
			ResourceLocation name = event.getItemStack().getItem().getRegistryName() ;
			String mod = name.toString().split(":")[0] ;
			
			if (mod == "minecraft") {
				event.getToolTip().add(TextFormatting.DARK_RED + "This armour is useless! Equip at your own risk!") ;
			}
		}
	}
	
}
