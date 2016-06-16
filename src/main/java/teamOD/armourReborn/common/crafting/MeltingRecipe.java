package teamOD.armourReborn.common.crafting;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;

public class MeltingRecipe {
	public Item[] ignot ;
	public Fluid molten ;
	
	public MeltingRecipe (Fluid fluid, Item... item) {
		ignot = item ;
		molten = fluid ;
	}
}
