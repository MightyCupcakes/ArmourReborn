package teamOD.armourReborn.common.item;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWithMeta extends ItemBlock {

	public ItemBlockWithMeta(Block block) {
		super(block);
		this.setHasSubtypes(true) ;
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return super.getUnlocalizedName(par1ItemStack) + par1ItemStack.getItemDamage();
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage ;
	}
}
