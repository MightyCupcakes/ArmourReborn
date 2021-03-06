package teamOD.armourReborn.common.modifiers;

import java.util.List;

import net.minecraft.item.ItemStack;
import teamOD.armourReborn.common.crafting.MaterialsMod;

/**
 * An modifiable armour is an armour that will receive extra modifiers from its materials.
 * @author MightyCupcakes
 *
 */

public interface IModifiable {
	
	public static final String IDENTIFIER = "identifier" ;
	public static final String MODIFIER_SLOTS = "slotsLeft" ;
	
	public ItemStack buildItem (List<MaterialsMod> materials) ;
	
	public void addModifier (ItemStack armour, IModifier modifier) ;
}
