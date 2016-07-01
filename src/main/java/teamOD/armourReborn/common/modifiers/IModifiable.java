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
	
	public static final String LEVEL = "level" ;
	public static final String IDENTIFIER = "identifier" ;
	
	public ItemStack buildItem (List<MaterialsMod> materials) ;
	
	public void addModifier (ItemStack armour) ;
	
	public List<IModifier> getModifiers () ;
}
