package teamOD.armourReborn.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibMisc;

public class BlockForgeMaster extends BlockContainer {

	protected BlockForgeMaster() {
		super(Material.iron);
		
		setUnlocalizedName (LibItemNames.FORGE_CONTROLLER) ;
		
		setCreativeTab (ArmourRebornCreativeTab.INSTANCE) ;
		
		GameRegistry.register(this, new ResourceLocation (LibMisc.MOD_ID, LibItemNames.FORGE_CONTROLLER)) ;
		GameRegistry.register(new ItemBlock (this), getRegistryName() ) ;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true ;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileForgeMaster () ;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
