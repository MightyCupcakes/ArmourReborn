package teamOD.armourReborn.common.block.tile;

import com.sun.xml.internal.stream.Entity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileForgeComponent extends TileMultiBlock {

	@Override
	public void checkMultiBlockForm() {
		
		TileMultiBlock entity = (TileMultiBlock) getMasterBlock() ;
		entity.checkMultiBlockForm() ;
	}

	@Override
	public void resetStructure() {
		TileMultiBlock entity = (TileMultiBlock) getMasterBlock() ;
		entity.resetStructure() ;
	}

	@Override
	public void setupStructure() {
		TileMultiBlock entity = (TileMultiBlock) getMasterBlock() ;
		entity.setupStructure() ;
	}

}
