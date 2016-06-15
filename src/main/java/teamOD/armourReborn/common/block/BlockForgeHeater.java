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
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.block.tile.TileHeatingComponent;

public class BlockForgeHeater extends BlockMod implements ITileEntityProvider {
	
	public BlockForgeHeater (Material par2Material, String name) {
		super(par2Material, name);
		
		this.isBlockContainer = true ; // This is a Tile entity
		this.setHardness(4F) ;
		this.setResistance(20F) ;
		
		GameRegistry.registerTileEntity(TileHeatingComponent.class, "forgeHeater") ;
	}
	
	public BlockForgeHeater (String name) {
		this (Material.rock, name) ;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHeatingComponent() ;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side,
            float clickX, float clickY, float clickZ) {
		if (world.isRemote) return true ;
		
		TileEntity entity = world.getTileEntity(pos) ;
		
		IFluidHandler tank = null ;
		
		if (entity instanceof IFluidHandler) {
			tank = (IFluidHandler) entity ;
		} else {
			return false ;
		}
		
		ItemStack item = player.getHeldItemMainhand() ;
		
		if (item == null) return false ;
		
		return FluidUtil.interactWithTank(item, player, tank, side) ;
	}
}
