package teamOD.armourReborn.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.block.tile.TileForgeComponent;
import teamOD.armourReborn.common.block.tile.TileForgeTank;

public class BlockForgeHeater extends BlockMod implements ITileEntityProvider {
	
	public BlockForgeHeater (Material par2Material, String name) {
		super(par2Material, name);
		
		this.isBlockContainer = true ; // This is a Tile entity
		this.setHardness(4F) ;
		this.setResistance(20F) ;
		
		GameRegistry.registerTileEntity(TileForgeTank.class, "forgeHeater") ;
	}
	
	public BlockForgeHeater (String name) {
		this (Material.ROCK, name) ;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileForgeTank() ;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side,
            float clickX, float clickY, float clickZ) {
		if (world.isRemote) return true ;
		
		TileEntity entity = world.getTileEntity(pos) ;
		
		if ( entity == null || !entity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side) ) {
			return false ;
		}
		
		IFluidHandler tank = entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side) ;
		FluidUtil.interactWithFluidHandler(stack, tank, player) ;
		
		return stack != null && !(stack.getItem() instanceof ItemBlock) ;
	}
}
