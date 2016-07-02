package teamOD.armourReborn.common.crafting;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.ITrait;

public class MaterialsMod {
	
	private String identifier ;
	private List<ITrait> traits ;
	
	public MaterialsMod (String identifier, List<ITrait> traits) {
		this.identifier = identifier ;
		this.traits = traits ;
	}
	
	public String getIdentifier () {
		return identifier ;
	}
	
	public List<ITrait> getTraits () {
		return traits ;
	}
	
	public void writeToNBT (NBTTagCompound cmp) {
		for (ITrait trait: traits) {			
			cmp.setString(IModifiable.IDENTIFIER, trait.getIdentifier()) ;
		}
	}

}
