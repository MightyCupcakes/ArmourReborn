package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.text.TextFormatting;

public abstract class AbstractModifier extends AbstractTrait implements IModifier {
	
	protected Item item ;

	public AbstractModifier(String identifier, TextFormatting colour) {
		super(identifier, colour);
	}

	@Override
	public void addItem(Item item) {
		this.item = item ;
	}

	@Override
	public Item getItem() {
		return item ;
	}
}
