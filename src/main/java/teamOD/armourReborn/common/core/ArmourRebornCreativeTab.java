package teamOD.armourReborn.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import teamOD.armourReborn.common.lib.LibMisc;

public class ArmourRebornCreativeTab extends CreativeTabs {
	public static ArmourRebornCreativeTab INSTANCE = new ArmourRebornCreativeTab () ;
	
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
}
