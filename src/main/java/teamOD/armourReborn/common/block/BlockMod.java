package teamOD.armourReborn.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.lib.LibMisc;

public class BlockMod extends Block {
	
	public BlockMod (Material par2Material, String name) {
		super (par2Material) ;
		setUnlocalizedName (name) ;
		
		setCreativeTab (ArmourRebornCreativeTab.INSTANCE) ;
		
		if (shouldRegister ()) {
			GameRegistry.register(this, new ResourceLocation(LibMisc.MOD_ID, name));
			GameRegistry.register(new ItemBlock (this), getRegistryName ()) ;
		}
		
	}
	
	protected boolean shouldRegister () {
		// If for some reason you don't want to register an item, override this method
		return true ;
	}
}
