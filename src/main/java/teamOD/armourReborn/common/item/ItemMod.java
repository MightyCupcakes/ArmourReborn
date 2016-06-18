package teamOD.armourReborn.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.lib.LibMisc;

public class ItemMod extends Item {
	public ItemMod (String name) {
		setCreativeTab (ArmourRebornCreativeTab.INSTANCE) ;
		
		if (shouldRegister()) {
			GameRegistry.register(this, new ResourceLocation (LibMisc.MOD_ID, name)) ;
		}
		
		setUnlocalizedName (name) ;
	}
	
	protected boolean shouldRegister () {
		// If for some reason you don't want to register an item, override this method
		return true ;
	}
}
