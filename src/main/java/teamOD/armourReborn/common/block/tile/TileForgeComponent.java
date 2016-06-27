package teamOD.armourReborn.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileForgeComponent extends TileMultiBlock {

	@Override
	public void checkMultiBlockForm() {
		
		if (this.hasMaster()) {
			TileMultiBlock entity = (TileMultiBlock) getMasterBlock() ;
			entity.checkMultiBlockForm() ;
		}
	}

	@Override
	public void resetStructure() {
		// NO OP
	}

	@Override
	protected void setupStructure() {
		// NO OP
	}
	
	public void notifyMaster () {
		if (hasMaster ()) {
			if (getMasterBlock() != null) {
				getMasterBlock().checkMultiBlockForm() ;
			} else {
				this.reset() ;
			}
		}
	}

}
