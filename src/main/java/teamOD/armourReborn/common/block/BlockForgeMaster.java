package teamOD.armourReborn.common.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.ArmourReborn;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.lib.LibUtil;

public class BlockForgeMaster extends BlockContainer {
	
	public static PropertyBool ACTIVE = PropertyBool.create("active") ;
	public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL) ;

	protected BlockForgeMaster() {
		super(Material.iron);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
		
		setUnlocalizedName (LibItemNames.FORGE_CONTROLLER) ;
		
		setCreativeTab (ArmourRebornCreativeTab.INSTANCE) ;
		
		GameRegistry.register(this, new ResourceLocation (LibMisc.MOD_ID, LibItemNames.FORGE_CONTROLLER)) ;
		GameRegistry.register(new ItemBlock (this), getRegistryName() ) ;
		GameRegistry.registerTileEntity(TileForgeMaster.class, "forgeMaster") ;
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
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(ACTIVE, isStructureActive(worldIn, pos));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer (this, ACTIVE, FACING) ;
	}
	
	public boolean isStructureActive (IBlockAccess world, BlockPos pos) {
		return getTileEntity(world, pos).isActive() ;
		
	}
	
	private TileForgeMaster getTileEntity (IBlockAccess world, BlockPos pos) {
		return (TileForgeMaster) world.getTileEntity(pos) ;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side,
            float clickX, float clickY, float clickZ) {
		if (player.isSneaking()) {
			return false ;
		}
		
		// TODO: Handle GUI HERE
		
		if (isStructureActive (world, pos) && !world.isRemote) {
			LibUtil.LogToFML(1, "Inventory opened", "");
		}
		
		return true ;
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (!this.isStructureActive(world, pos)) {
			return ;
		}
		
		EnumFacing facing = state.getValue(FACING) ;
		
		double x = pos.getX() + 0.5D ;
		double y = pos.getY() + 0.5D ;
		double z = pos.getZ() + 0.5D ;
		
		double offset = 0.55D ;
		
		switch (facing) {
		case EAST:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + offset, y, z, 0D, 0D, 0D) ;
			break ;
		case WEST:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x - offset, y, z, 0D, 0D, 0D) ;
			break ;
		case NORTH:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z - offset, 0D, 0D, 0D) ;
			break ;
		case SOUTH:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z + offset, 0D, 0D, 0D) ;
			break ;
		default:
			break ;
		}
	}
}
