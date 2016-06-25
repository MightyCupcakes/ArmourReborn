package teamOD.armourReborn.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamOD.armourReborn.common.ArmourReborn;
import teamOD.armourReborn.common.block.tile.TileForgeAnvil;
import teamOD.armourReborn.common.block.tile.TileHeatingComponent;

public class BlockForgeAnvil extends BlockMod implements ITileEntityProvider {
	
	public BlockForgeAnvil (Material par2Material, String name) {
		super(par2Material, name);
		
		this.isBlockContainer = true ; // This is a Tile entity
		this.setHardness(4F) ;
		this.setResistance(20F) ;
		
		GameRegistry.registerTileEntity(TileForgeAnvil.class, "forgeAnvil") ;
	}
	
	public BlockForgeAnvil (String name) {
		this (Material.rock, name) ;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileForgeAnvil () ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false ;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false ;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false ;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		
		if (worldIn.isRemote) return true ;
		
		
		TileForgeAnvil entity = this.getTile(worldIn, pos) ;
		
		IFluidHandler tank = null ;
		
		if (entity instanceof IFluidHandler) {
			tank = (IFluidHandler) entity ;
		} else {
			return false ;
		}
		
		ItemStack item = playerIn.getHeldItemMainhand() ;
		
		System.out.println("Internal Tank: " + entity.getTankInfo(EnumFacing.NORTH)[0].fluid.amount);
	
		
		if (item == null) {
			playerIn.openGui(ArmourReborn.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		} else {
			FluidUtil.interactWithTank(item, playerIn, tank, side) ;
		}
		/*
		if (entity.getStack() == null) {
			if (heldItem != null) {
				entity.setStack(heldItem) ;
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null) ;
				playerIn.openContainer.detectAndSendChanges() ;
				entity.sync() ;
			}
		} else {
			if (heldItem == null) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, entity.getStack()) ;
				entity.setStack(null) ;
				playerIn.openContainer.detectAndSendChanges() ;
				entity.sync() ;
			}
		}
		*/
		return true ;
	}
	
	private TileForgeAnvil getTile (World world, BlockPos pos) {
		return (TileForgeAnvil) world.getTileEntity(pos) ;
	}
}
