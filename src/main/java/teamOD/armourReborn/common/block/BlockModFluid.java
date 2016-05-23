package teamOD.armourReborn.common.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;

public class BlockModFluid extends BlockFluidClassic {

	public BlockModFluid(Fluid fluid, Material material) {
		super(fluid, material);
		
		setCreativeTab (ArmourRebornCreativeTab.INSTANCE) ;
	}
	
	public BlockModFluid(Fluid fluid) {
		this (fluid, Material.lava) ;
	}

}
