package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.text.TextFormatting;

public abstract class AbstractModifier extends AbstractTrait implements IModifier {
	
	protected ItemStack item ;

	public AbstractModifier(String identifier, TextFormatting colour, ItemStack item) {
		super(identifier, colour);
		this.item = item ;
	}
	
	public AbstractModifier (String identifier, int level, TextFormatting colour, ItemStack item) {
		super (identifier, level, colour) ;
		this.item = item ;
	}

	@Override
	public ItemStack getItemStack () {
		return item ;
	}
	
	@Override
	public Item getItem () {
		return item.getItem() ;
	}
}
