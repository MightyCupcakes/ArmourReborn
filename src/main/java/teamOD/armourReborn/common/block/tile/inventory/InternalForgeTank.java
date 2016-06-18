package teamOD.armourReborn.common.block.tile.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import teamOD.armourReborn.common.block.tile.TileForgeMaster;
import teamOD.armourReborn.common.block.tile.network.ForgeFluidUpdatePacket;
import teamOD.armourReborn.common.network.PacketHandler;

public class InternalForgeTank {
	
	protected List <FluidStack> liquids ;
	protected final int maxCapacity = 5000 ;
	protected final TileForgeMaster master ;
	
	public InternalForgeTank (TileForgeMaster master) {
		this.master = master ;
		liquids = Lists.newArrayList() ;
	}
	
	public List<FluidStack> getFluids () {
		return liquids ;
	}
	
	public int getUsedCapacity () {
		int amt = 0 ;
		
		for (FluidStack liquid: liquids) {
			amt += liquid.amount ;
		}
		
		return amt ;
	}
	
	public int fill (FluidStack resource, boolean doFill) {
		int used = getUsedCapacity() ;
		
		int filledAmt = Math.min(maxCapacity - used, resource.amount) ;
		
		if (!doFill) return filledAmt ;
		
		for (FluidStack liquid: liquids) {
			if (liquid.isFluidEqual(resource)) {
				liquid.amount += filledAmt ;
				
				return filledAmt ;
			}
		}
		
		resource = resource.copy() ;
		resource.amount = filledAmt ;
		
		liquids.add(resource) ;
		tankChanged ();
		
		return filledAmt ;
	}
	
	public FluidStack drain (FluidStack resource, boolean doDrain) {
		
		for (FluidStack liquid: liquids) {
			if (liquid.isFluidEqual(resource)) {
				 int maxDrained = Math.min(resource.amount, liquid.amount) ;
				
				if (doDrain) {
					liquid.amount -= maxDrained ;
					
					if (liquid.amount <= 0) {
						liquids.remove(liquid) ;
					}
				}
				
				resource = resource.copy() ;
				resource.amount = maxDrained ;
				tankChanged () ;
				return resource ;
			}
		}
		
		return null ;
	}
	
	public void updateFluidsFromPacket (List<FluidStack> fluid) {
		this.liquids = fluid ;
	}
	
	public void tankChanged () {
		if (!master.getWorld().isRemote) {
			PacketHandler.sendToAll(new ForgeFluidUpdatePacket (master.getPos(), liquids));
		}
	}
	
	public void writeToNBT (NBTTagCompound tag) {
		NBTTagList tagList = new NBTTagList () ;
		
		for (FluidStack liquid: liquids) {
			NBTTagCompound cmp = new NBTTagCompound () ;
			
			liquid.writeToNBT(cmp) ;
			tagList.appendTag(cmp) ;
		}
		
		tag.setTag("liquids", tagList);
	}
	
	public void readToNBT (NBTTagCompound tag) {
		NBTTagList tagList = tag.getTagList("liquids", 10) ;
		
		liquids.clear() ;
		
		for (int i = 0; i < tagList.tagCount(); i ++) {
			NBTTagCompound cmp = tagList.getCompoundTagAt(i) ;
			FluidStack liquid = FluidStack.loadFluidStackFromNBT(cmp) ;
			
			if (liquid != null) {
				liquids.add(liquid) ;
			}
		}
	}

}
