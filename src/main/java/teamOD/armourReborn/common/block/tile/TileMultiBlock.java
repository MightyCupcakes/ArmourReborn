package teamOD.armourReborn.common.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileMultiBlock extends TileMod {
	
	private boolean hasMaster, isMaster ;
	private int masterX, masterY, masterZ ;
	
    @Override
    public void updateEntity() {
    }
    
    public boolean checkMultiBlockForm() {
        int i = 0;
        // Scan a 3x3x3 area, starting with the bottom left corner
        for (int x = masterX - 1; x < masterX + 2; x++) {
            for (int y = masterY; y < masterY + 3; y++) {
                for (int z = masterZ - 1; z < masterZ + 2; z++) {
                     TileEntity tile = worldObj.getTileEntity(new BlockPos (x, y, z) );
                     // Make sure tile isn't null, is an instance of the same Tile, and isn't already a part of a multiblock
                     if (tile != null && (tile instanceof TileMultiBlock)) {
                         if (this.isMaster()) {
                             if (((TileMultiBlock)tile).hasMaster())
                                 i++;
                         } else if (!((TileMultiBlock)tile).hasMaster())
                             i++;
                     }
                 }
            }
        }
         // check if there are 26 blocks present ((3*3*3) - 1) and check that center block is empty
         return i > 25 && worldObj.isAirBlock(new BlockPos (masterX, masterY + 1, masterZ)) ;
    }
    
    /** Reset method to be run when the master is gone or tells them to */
    public void reset() {
        masterX = 0;
        masterY = 0;
        masterZ = 0;
        hasMaster = false;
        isMaster = false;
    }
 
    /** Check that the master exists */
    public boolean checkForMaster() {
        TileEntity tile = worldObj.getTileEntity(new BlockPos (masterX, masterY, masterZ)) ;
        return (tile != null && (tile instanceof TileMultiBlock));
    }
    
    /** Reset all the parts of the structure */
    public void resetStructure() {
        for (int x = masterX - 1; x < masterX + 2; x++) {
            for (int y = masterY; y < masterY + 3; y++) {
                for (int z = masterZ - 1; z < masterZ + 2; z++) {
                    TileEntity tile = worldObj.getTileEntity( new BlockPos (x, y, z) );
                    if (tile != null && (tile instanceof TileMultiBlock))
                        ((TileMultiBlock) tile).reset();
                }
            }
        }
    }
    
    /** Setup all the blocks in the structure*/
    public void setupStructure() {
        for (int x = masterX - 1; x < masterX + 2; x++) {
            for (int y = masterY; y < masterY + 3; y++) {
                for (int z = masterZ - 1; z < masterZ + 2; z++) {
                    TileEntity tile = worldObj.getTileEntity( new BlockPos (x, y, z) );
                    // Check if block is bottom center block
                    boolean master = (x == masterX && y == masterY && z == masterZ);
                    if (tile != null && (tile instanceof TileMultiBlock)) {
                        ((TileMultiBlock) tile).setMasterCoords(masterX, masterY, masterZ);
                        ((TileMultiBlock) tile).setHasMaster(true);
                        ((TileMultiBlock) tile).setIsMaster(master);
                    }
                }
            }
        }
    }
 
    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("masterX", masterX);
        data.setInteger("masterY", masterY);
        data.setInteger("masterZ", masterZ);
        data.setBoolean("hasMaster", hasMaster);
        data.setBoolean("isMaster", isMaster);
        if (hasMaster() && isMaster()) {
            // Any other values should ONLY BE SAVED TO THE MASTER
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        masterX = data.getInteger("masterX");
        masterY = data.getInteger("masterY");
        masterZ = data.getInteger("masterZ");
        hasMaster = data.getBoolean("hasMaster");
        isMaster = data.getBoolean("isMaster");
        if (hasMaster() && isMaster()) {
            // Any other values should ONLY BE READ BY THE MASTER
        }
    }

    public boolean hasMaster() {
        return hasMaster;
    }

    public boolean isMaster() {
        return isMaster;
    }
    
    public int getMasterCoords (char axis) {
    	switch (axis) {
    	case 'x' :
    		return masterX ;
    	case 'y' :
    		return masterY ;
    	case 'z' :
    		return masterZ ;
    	default :
    		return 0 ;
    	}
    }

    public void setHasMaster(boolean bool) {
        hasMaster = bool;
    }

    public void setIsMaster(boolean bool) {
        isMaster = bool;
    }

    public void setMasterCoords(int x, int y, int z) {
        masterX = x;
        masterY = y;
        masterZ = z;
    }
	
}

