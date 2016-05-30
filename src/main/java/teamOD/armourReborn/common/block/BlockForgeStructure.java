package teamOD.armourReborn.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import teamOD.armourReborn.common.block.tile.TileForgeComponent;

public class BlockForgeStructure extends BlockMod implements ITileEntityProvider {

	public BlockForgeStructure(Material par2Material, String name) {
		super(par2Material, name);
		
		this.isBlockContainer = true ; // This is a Tile entity
		this.setHardness(4F) ;
		this.setResistance(20F) ;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileForgeComponent ();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity entity = worldIn.getTileEntity(pos) ;
		
		if (entity instanceof TileForgeComponent) {
			( (TileForgeComponent) entity).reset() ;
		}
		
		super.breakBlock(worldIn, pos, state) ;
		worldIn.removeTileEntity(pos) ;
		
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
		for (EnumFacing direction: EnumFacing.values()) {
			TileEntity entity = worldIn.getTileEntity(pos.offset(direction)) ;
			
			if (entity instanceof TileForgeComponent) {
				if ( ( (TileForgeComponent) entity).hasMaster() ) {
					((TileForgeComponent) entity).checkMultiBlockForm() ;
					break ;
				}
			}
		}
	}
}