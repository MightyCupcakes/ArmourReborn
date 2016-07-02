package teamOD.armourReborn.common.crafting;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.ITrait;

public class MaterialsMod {
	
	/** A string to uniquely identify this material */
	private final String identifier ;
	/** The item this item is associated to */
	private final Item item ;
	/** A list of traits this material possess */
	private final List<ITrait> traits ;
	
	public MaterialsMod (String identifier, Item item, List<ITrait> traits) {
		this.identifier = identifier ;
		this.traits = traits ;
		this.item = item ;
	}
	
	public String getIdentifier () {
		return identifier ;
	}
	
	public Item getItem () {
		return item ;
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
