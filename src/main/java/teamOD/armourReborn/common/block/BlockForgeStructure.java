package teamOD.armourReborn.common.block;

import com.google.common.collect.ImmutableSet;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.actors.threadpool.Arrays;
import teamOD.armourReborn.common.block.tile.TileForgeComponent;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;

public class BlockForgeStructure extends BlockMod implements ITileEntityProvider {
	
	public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL) ;
	public static PropertyEnum BLOCK = PropertyEnum.create("block", EnumBlockType.class) ;
	
	private static final ImmutableSet<EnumFacing> invertFacing = ImmutableSet.of(EnumFacing.NORTH, EnumFacing.EAST) ;

	public BlockForgeStructure(Material par2Material, String name) {
		super(par2Material, name);
		
		this.isBlockContainer = true ; // This is a Tile entity
		this.setHardness(4F) ;
		this.setResistance(20F) ;
		
		GameRegistry.registerTileEntity(TileForgeComponent.class, "forgeComponent") ;
	}
	
	public BlockForgeStructure (String name) {
		this (Material.rock, name) ;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileForgeComponent ();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer (this, FACING, BLOCK) ;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
			
		TileEntity entity = worldIn.getTileEntity(pos) ;
		
		if (entity instanceof TileForgeComponent) {
			( (TileForgeComponent) entity).notifyMaster() ;
		}
		
		super.breakBlock(worldIn, pos, state) ;
		
	}
	
	@Override
	public IBlockState getStateFromMeta (int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta)) ;
	}
	
	@Override
	public int getMetaFromState (IBlockState state) {
		return state.getValue(FACING).getIndex() ;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(BLOCK, makeConnectedTextures (worldIn, pos)) ;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
		for (EnumFacing direction: EnumFacing.values()) {
			TileEntity entity = worldIn.getTileEntity(pos.offset(direction)) ;
			
			if (entity == null) continue ;
			
			if (entity instanceof TileForgeComponent) {
				((TileForgeComponent) entity).notifyMaster() ;
				break ;
			} else if (entity instanceof TileForgeMaster) {
				((TileForgeMaster) entity).checkMultiBlockForm() ;
				break ;
			}
		}
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		this.getDefaultState().withProperty(BLOCK, makeConnectedTextures (worldIn, pos));

		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		this.getDefaultState().withProperty(BLOCK, makeConnectedTextures (world, pos)) ;
		
	}
	
	private EnumBlockType makeConnectedTextures (IBlockAccess world, BlockPos pos) {
		
		Boolean[] neighbors = new Boolean[6] ; // follows EnumFacing.VALUES
		Arrays.fill(neighbors, false) ;
		
		int i = 0 ;
		
		TileForgeComponent tile = (TileForgeComponent) world.getTileEntity(pos) ;
		
		EnumFacing state ;
		
		try {
			 state = world.getBlockState(pos).getValue(FACING) ;
		} catch (IllegalArgumentException e) {
			return EnumBlockType.NONE ;
		}
		
		if (tile == null || !tile.hasMaster()) {
			return EnumBlockType.NONE ;
		}
		
		for (EnumFacing direction: EnumFacing.VALUES) {
			
			if (direction == state.getOpposite()) {
				neighbors[i] = false ;
				continue ;
			}
			
			if (world.getTileEntity(pos.offset(direction)) instanceof TileForgeMaster) {
				//neighbors[0] = false ;
				neighbors[i] = true ;
			}
			
			else if (world.getTileEntity(pos.offset(direction)) instanceof TileForgeComponent) {
				neighbors[i] = true ;
			}
			
			else {
				neighbors[i] = false ;
			}
			
			i ++ ;
		}

		if (!neighbors[0]) {
			
			if (neighbors[3] || neighbors[5]) {
				return (invertFacing.contains(state)) ? EnumBlockType.BTMRIGHT : EnumBlockType.BTMLEFT ;
			}
			else if (neighbors[2] || neighbors[4]) {
				return (invertFacing.contains(state)) ? EnumBlockType.BTMLEFT : EnumBlockType.BTMRIGHT ;
			}
			else {
				return EnumBlockType.NONE ;
			}
		} else {
			if ( (neighbors[4] && neighbors[5]) || (neighbors[2] && neighbors[3]) ) {
				return EnumBlockType.CENTER ;
			}
			else if (neighbors[3] || neighbors[5]) {
				return (invertFacing.contains(state)) ? EnumBlockType.TOPRIGHT : EnumBlockType.TOPLEFT ;
			}
			else if (neighbors[2] || neighbors[4]) {
				return (invertFacing.contains(state)) ? EnumBlockType.TOPLEFT : EnumBlockType.TOPRIGHT ;
			}
			else {
				return EnumBlockType.NONE ;
			}
		}
	}
	
	private enum EnumBlockType implements IStringSerializable {
		
		NONE ("none"),
		BTMLEFT ("btmleft"),
		BTMRIGHT ("btmright"),
		TOPLEFT ("topleft"),
		CENTER ("center"),
		TOPRIGHT ("topright") ;
		
		private String name ;
		
		private EnumBlockType (String name) {
			this.name = name ;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
	}
}
