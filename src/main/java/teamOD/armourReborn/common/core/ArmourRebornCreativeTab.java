package teamOD.armourReborn.common.core;

import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import teamOD.armourReborn.common.block.ModBlocks;
import teamOD.armourReborn.common.fluids.ModFluids;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibMisc;

public class ArmourRebornCreativeTab extends CreativeTabs {
	public static ArmourRebornCreativeTab INSTANCE = new ArmourRebornCreativeTab () ;
	
	List <ItemStack> list ;
	
	public ArmourRebornCreativeTab () {
		super (LibMisc.MOD_ID) ;
	}
	
	@Override
	public ItemStack getIconItemStack () {
		return new ItemStack (ModItems.getArmourByName("paperleather").get(EntityEquipmentSlot.CHEST)) ;
	}
	
	@Override
	public Item getTabIconItem () {
		return getIconItemStack().getItem() ;
	}
	
	@Override
	public void displayAllRelevantItems (List<ItemStack> list) {
		this.list = list ;
		
		addItem( ModItems.MATERIALS ) ;
		addItem( Item.getItemFromBlock(ModBlocks.forgeMaster) ) ;
		addItem( Item.getItemFromBlock(ModBlocks.forgeBlocks) ) ;
		addItem( Item.getItemFromBlock(ModBlocks.forgeHeater) ) ;
		addItem( Item.getItemFromBlock(ModBlocks.oresMaterials) ) ;
		addItem( Item.getItemFromBlock(ModBlocks.forgeAnvil) ) ;
		
		for (Map <EntityEquipmentSlot, Item> armour : ModItems.getAllModArmour()) {
			for (Item armourPiece : armour.values()) {
				addItem ( armourPiece ) ;
			}
		}
		
	}
	
	private void addItem (Item item) {
		item.getSubItems(item, this, list);
	}
}
