package teamOD.armourReborn.common.block;

import java.util.Random;

import net.minecraft.block.Block;
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
import net.minecraftforge.fluids.FluidStack;
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

	public BlockForgeMaster (String name) {
		super(Material.IRON);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
		
		setUnlocalizedName (LibUtil.getPrefix(name)) ;
		
		setCreativeTab (ArmourRebornCreativeTab.INSTANCE) ;
		
		GameRegistry.register(this, new ResourceLocation (LibMisc.MOD_ID, name)) ;
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
		
		if (isStructureActive(worldIn, pos)) {
			this.setLightLevel(10F) ;
		} else {
			this.setLightLevel(0F) ;
		}
		
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
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		this.getDefaultState().withProperty (ACTIVE, isStructureActive (world, pos)) ;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side,
            float clickX, float clickY, float clickZ) {
		if (player.isSneaking()) {
			return false ;
		}
		
		if (isStructureActive (world, pos) && !world.isRemote) {
			// TODO GUI to display all relevant information
			LibUtil.LogToFML(1, "Inventory opened", "") ;
			
			FluidStack fluid = getTileEntity(world, pos).getHeater().fluid ;
			if (fluid != null) {
				LibUtil.LogToFML(1, "Fuel: %s, %d mB, temp: %d ", fluid.getUnlocalizedName(), fluid.amount, fluid.getFluid().getTemperature()) ;
			}
			
			int size = getTileEntity(world, pos).getInternalTank().size() ;
			String fluids = "" ;
			
			for (FluidStack liquid: getTileEntity(world, pos).getInternalTank()) {
				fluids += liquid.getUnlocalizedName() + ": " + liquid.amount + " mB" ;
			}
			
			LibUtil.LogToFML(1, "INTERNAL TANK: %s", fluids);
			
			
			player.openGui(ArmourReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
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
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileForgeMaster entity = getTileEntity (worldIn, pos) ;
		
		entity.resetStructure() ;
		
		super.breakBlock(worldIn, pos, state) ;
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (!this.isStructureActive(world, pos)) {
			return ;
		}
		
		EnumFacing facing = state.getValue(FACING) ;
		
		double x = pos.getX() + 0.5D ;
		double y = pos.getY() + 0.5D + (rand.nextFloat() * 0.3D);
		double z = pos.getZ() + 0.5D ;
		
		double offset = 0.55D ;
		double random = rand.nextDouble() * 0.5D ;
		
		switch (facing) {
		case EAST:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + offset, y, z + random, 0D, 0D, 0D) ;
			world.spawnParticle(EnumParticleTypes.FLAME, x + offset, y, z + random, 0D, 0D, 0D) ;
			break ;
		case WEST:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x - offset, y, z + random, 0D, 0D, 0D) ;
			world.spawnParticle(EnumParticleTypes.FLAME, x - offset, y, z + random, 0D, 0D, 0D) ;
			break ;
		case NORTH:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + random, y, z - offset, 0D, 0D, 0D) ;
			world.spawnParticle(EnumParticleTypes.FLAME, x + random, y, z - offset, 0D, 0D, 0D) ;
			break ;
		case SOUTH:
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + random, y, z + offset, 0D, 0D, 0D) ;
			world.spawnParticle(EnumParticleTypes.FLAME, x + random, y, z + offset, 0D, 0D, 0D) ;
			break ;
		default:
			break ;
		}
	}
}
