package teamOD.armourReborn.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.lib.LibMisc;

public class BlockModFluid extends BlockFluidClassic {

	public BlockModFluid(Fluid fluid, Material material) {
		super(fluid, material);
		
		setCreativeTab (ArmourRebornCreativeTab.INSTANCE) ;
		setRegistryName (new ResourceLocation (LibMisc.MOD_ID, fluid.getName())) ;
		setUnlocalizedName(fluid.getUnlocalizedName());
		
		GameRegistry.register(this) ;
		GameRegistry.register( new ItemBlock (this), getRegistryName() ) ;
	}
	
	public BlockModFluid(Fluid fluid) {
		this (fluid, Material.LAVA) ;
	}
}
