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
    
    public abstract boolean checkMultiBlockForm() ;
    
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
    public abstract void resetStructure() ;
    
    /** Setup all the blocks in the structure*/
    public abstract void setupStructure() ;
 
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

