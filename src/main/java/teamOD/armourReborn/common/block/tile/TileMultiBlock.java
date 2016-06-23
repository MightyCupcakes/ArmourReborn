package teamOD.armourReborn.common.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileMultiBlock extends TileMod {
	
	private boolean hasMaster ;
	private boolean isMaster;
	private int masterX, masterY, masterZ ;
    
    public abstract void checkMultiBlockForm() ;
    
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
    protected abstract void setupStructure() ;
 
    @Override
    public void writeCustomNBT(NBTTagCompound data) {
        data.setInteger("masterX", masterX);
        data.setInteger("masterY", masterY);
        data.setInteger("masterZ", masterZ);
        data.setBoolean("hasMaster", hasMaster);
        data.setBoolean("isMaster", isMaster);
    }

    @Override
    public void readCustomNBT(NBTTagCompound data) {
        masterX = data.getInteger("masterX");
        masterY = data.getInteger("masterY");
        masterZ = data.getInteger("masterZ");
        hasMaster = data.getBoolean("hasMaster");
        isMaster = data.getBoolean("isMaster");
    }

    public boolean hasMaster() {
        return hasMaster && (worldObj.getTileEntity(new BlockPos (masterX, masterY, masterZ)) instanceof TileForgeMaster);
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
    
    public boolean isMasterCoords (BlockPos pos) {
    	return masterX == pos.getX() && masterY == pos.getY() && masterZ == pos.getZ() ;
    }
    
    public TileForgeMaster getMasterBlock () {
    	if (!hasMaster) return null ;
    	
    	return (TileForgeMaster) worldObj.getTileEntity(new BlockPos (masterX, masterY, masterZ)) ;
    }
    
    public void setIsMaster(boolean bool) {
        isMaster = bool;
    }

    public void setMasterCoords (int x, int y, int z) {
        masterX = x;
        masterY = y;
        masterZ = z;
        
        hasMaster = true ;
    }
    
    public void setMasterCoords (BlockPos pos) {
    	setMasterCoords (pos.getX(), pos.getY(), pos.getZ() ) ;
    }
	
}

