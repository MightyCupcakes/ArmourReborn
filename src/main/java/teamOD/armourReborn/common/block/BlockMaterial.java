package teamOD.armourReborn.common.block;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamOD.armourReborn.common.item.ItemBlockWithMeta;

public class BlockMaterial extends BlockMod {
	
	public static final PropertyEnum<Ores> TYPE = PropertyEnum.create("type", Ores.class) ;
	private Ores[] values ;
	
	public BlockMaterial(String name) {
		super(name);
		
		this.setHarvestLevel("pickaxe", 2);
        this.setHardness(10f);
        this.setResistance(15f);
        
        values = Ores.class.getEnumConstants() ;
        
        GameRegistry.register (new ItemBlockWithMeta(this), this.getRegistryName()) ;
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE) ;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMeta() ;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, (meta < 0 || meta >= values.length) ? values[0] : values[meta]) ;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState (state) ;
	}
	
	@Override
	public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, @Nonnull List<ItemStack> stacks) {
		for (Ores type: values) {
			stacks.add (new ItemStack (this, 1, type.getMeta())) ;
		}
	}
	
	public enum Ores implements IStringSerializable {
		Aluminium ,
		Copper ;
		
		public final int meta ;
		
		private Ores () {
			meta = this.ordinal() ;
		}
		
		@Override
		public String getName() {
			return this.toString().toLowerCase() ;
		}
		
		public int getMeta () {
			return meta ;
		}
		
	}

}
