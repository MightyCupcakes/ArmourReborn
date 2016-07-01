package teamOD.armourReborn.common.modifiers;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * An modifiable armour is an armour that will receive extra modifiers from its materials.
 * @author MightyCupcakes
 *
 */

public interface IModifiable {
	
	public void addModifier (ItemStack armour) ;
	
	public List<IModifier> getModifiers () ;
}
