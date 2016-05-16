package teamOD.armourReborn.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.lib.LibMisc;

public class BlockMod extends Block {
	public BlockMod (Material par2Material, String name) {
		super (par2Material) ;
		setUnlocalizedName (name) ;
		
		GameRegistry.register(this, new ResourceLocation(LibMisc.MOD_ID, name));
		GameRegistry.register(new ItemBlock (this), getRegistryName ()) ;
		
	}
}
