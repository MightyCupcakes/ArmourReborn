package teamOD.armourReborn.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibUtil;

public class ItemModifiersMaterials extends ItemMod {
	
	public final int types = 2 ;
	
	public ItemModifiersMaterials(String name) {
		super(name) ;
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
		return "item." + LibUtil.getPrefix(LibItemNames.MODIFIERS_MATERIALS_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())]);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		String s ;
		
		switch (stack.getItemDamage()) {
		case 0:
			s = "Predating the shattering, this artifact is said to be a small fragment of the powers of a powerful magus" ;
			break ;
			
		default:
			s = "" ;
		}
		
		tooltip.add(s) ;
	}
}
