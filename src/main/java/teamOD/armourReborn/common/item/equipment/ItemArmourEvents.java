package teamOD.armourReborn.common.item.equipment;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public final class ItemArmourEvents {
	@SubscribeEvent
	public void onArmourCraft (ItemCraftedEvent event) {
		
		if ( event.crafting == null ) return ;
		
		if ( !(event.crafting.getItem() instanceof ItemModArmour) ) return ;
		
		IInventory crafting = event.craftMatrix ;
		
		ItemStack armour = null ;
		ItemStack repairItem = null ;
		
		for (int i = 0; i < crafting.getSizeInventory(); i ++ ) {
			if (crafting.getStackInSlot(i) == null) continue ;
			
			ItemStack stack = crafting.getStackInSlot(i) ;
			
			if (stack.getItem() instanceof ItemModArmour) {
				armour = stack ;
			} else {
				repairItem = stack ;
			}
		}
		
		if (armour == null) return ;
		
		armour = ( (ItemModArmour) armour.getItem() ).replaceOrRepairArmour(event.player, armour, repairItem) ;
		
		event.crafting.setTagCompound( (NBTTagCompound) armour.getTagCompound().copy() ) ;
		event.crafting.setItemDamage( armour.getItemDamage() );
		
	}

}
