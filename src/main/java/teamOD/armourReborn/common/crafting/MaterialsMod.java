package teamOD.armourReborn.common.crafting;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import teamOD.armourReborn.common.lib.LibItemNames.ArmourTypeNames;
import teamOD.armourReborn.common.modifiers.IModifiable;
import teamOD.armourReborn.common.modifiers.ITrait;

public class MaterialsMod {
	
	/** A string to uniquely identify this material */
	private final String identifier ;
	/** The item this item is associated to */
	private final ItemStack item ;
	/** A list of traits this material possess */
	private final List<ITrait> traits ;
	
	private Set<ArmourTypeNames> applicableArmourSet ;
	
	private int[] baseArmourValue ;
	private double baseDurabilityMutiplier ;
	
	public MaterialsMod (String identifier, ItemStack item, List<ITrait> traits) {
		this.identifier = identifier ;
		this.traits = traits ;
		this.item = item ;
		this.applicableArmourSet =  Sets.newHashSet() ;
	}
	
	public MaterialsMod setBaseArmourValue (int[] value){
		this.baseArmourValue = value ;
		
		return this ;
	}
	
	public MaterialsMod setBaseDurabilityMultiplier (double value) {
		this.baseDurabilityMutiplier = value ;
		
		return this ;
	}
	
	public MaterialsMod allowArmourSet (ArmourTypeNames... armourTypes) {
		
		for (ArmourTypeNames armourType : armourTypes) {
			
			if (this.applicableArmourSet.contains(armourType)) continue ;
			
			this.applicableArmourSet.add(armourType) ;
		}
		
		return this ;
	}
	
	public String getIdentifier () {
		return identifier ;
	}
	
	public ItemStack getItemstack () {
		return item ;
	}
	
	public Item getItem () {
		return item.getItem() ;
	}
	
	public List<ITrait> getTraits () {
		return traits ;
	}
	
	public int[] getBaseArmourValue () {
		return baseArmourValue ;
	}
	
	public double getBaseDurabilityMultiplier () {
		return baseDurabilityMutiplier ;
	}
	
	public boolean isApplicableArmourSet (ArmourTypeNames armour) {
		return this.applicableArmourSet.contains(armour) ;
	}

}
