package teamOD.armourReborn.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.lib.LibItemNames;

public class ItemMaterials extends ItemMod {
	
	public final int types = 2 ;

	public ItemMaterials() {
		
		super(LibItemNames.MATERIALS);
		setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> subitems) {
		for(int i = 0; i < types; i++) {
				subitems.add(new ItemStack(par1, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + LibItemNames.MATERIALS_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
	}

}
