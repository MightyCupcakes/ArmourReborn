package teamOD.armourReborn.common.core;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
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
		return new ItemStack (Items.diamond_chestplate) ;
	}
	
	@Override
	public Item getTabIconItem () {
		return getIconItemStack().getItem() ;
	}
	
	@Override
	public void displayAllRelevantItems (List<ItemStack> list) {
		this.list = list ;
		
		addItem (ModItems.MATERIALS) ;
		addItem( Item.getItemFromBlock(ModFluids.steel.getBlock()) ) ;
		addItem( Item.getItemFromBlock(ModFluids.iron.getBlock()) ) ;
		addItem( Item.getItemFromBlock(ModBlocks.forgeMaster) ) ;
		
	}
	
	private void addItem (Item item) {
		item.getSubItems(item, this, list);
	}
}
