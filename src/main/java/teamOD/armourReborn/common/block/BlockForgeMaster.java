package teamOD.armourReborn.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.ArmourReborn;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;
import teamOD.armourReborn.common.core.ArmourRebornCreativeTab;
import teamOD.armourReborn.common.lib.LibItemNames;
import teamOD.armourReborn.common.lib.LibMisc;
import teamOD.armourReborn.common.lib.LibUtil;

public class BlockForgeMaster extends BlockContainer {

	protected BlockForgeMaster() {
		super(Material.iron);
		
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
	
	public boolean isStructureActive (World world, BlockPos pos) {
		TileEntity entity = world.getTileEntity(pos) ;
		
		if (entity instanceof TileForgeMaster) {
			return ( (TileForgeMaster) entity).isActive() ;
		}
		
		return false ;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side,
            float clickX, float clickY, float clickZ) {
		if (player.isSneaking()) {
			return false ;
		}
		
		// TODO: Handle GUI HERE
		
		if (isStructureActive (world, pos) ) {
			LibUtil.LogToFML(1, "ForgeMaster inventory opened", "");
		}
		
		return true ;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}