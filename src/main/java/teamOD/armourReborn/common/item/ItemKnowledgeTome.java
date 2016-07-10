package teamOD.armourReborn.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import teamOD.armourReborn.common.ArmourReborn;
import teamOD.armourReborn.common.lib.LibMisc;

public class ItemKnowledgeTome extends ItemMod {

	public ItemKnowledgeTome(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		
		playerIn.openGui(ArmourReborn.instance, LibMisc.BOOK_GUI_ID, worldIn, 0, 0, 0);
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn) ; 
	}
}
