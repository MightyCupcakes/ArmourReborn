package teamOD.armourReborn.common.modifiers;


import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.lib.LibUtil;

public final class ModTraitsModifiersRegistry {
	
	private static Map<String, ITrait> traitRegistry = Maps.newHashMap() ;
	private static Map<String, IModifier> modifierRegistry = Maps.newHashMap() ;
	
	public static ITrait evasion1 = new TraitEvasion(1) ;
	public static ITrait evasion2 = new TraitEvasion(2) ;
	public static ITrait evasion3 = new TraitEvasion(3) ;	
	public static ITrait reprisal1 = new TraitReprisal(1) ;
	public static ITrait reprisal2 = new TraitReprisal(2) ;
	public static ITrait reprisal3 = new TraitReprisal(3) ;	
	public static ITrait waterlogged = new TraitWaterlogged() ;	
	public static ITrait surge1 = new TraitSurge(1) ;
	public static ITrait surge2 = new TraitSurge(2) ;	
	public static ITrait flammable = new TraitFlammable() ;	
	public static ITrait nullField = new TraitNullField() ;
	public static ITrait rusty = new TraitRust() ;
	public static ITrait frostbite = new ModifierFrostbite(new ItemStack (Blocks.stone)) ;	// TODO
	public static ITrait unburnt = new ModifierUnburnt(new ItemStack (Blocks.stone)) ;	// TODO
	public static ITrait enderference = new ModifierEnderference(new ItemStack (Items.ender_pearl)) ;	
	public static ITrait stability = new ModifierUnyielding(new ItemStack (Blocks.stone)) ; // TODO
	
	private static ITrait nullTrait = new TraitNone() ;
	
	
	public static void init () {
		registerTrait (evasion1) ;
		registerTrait (evasion2) ;
		registerTrait (evasion3) ;
		registerTrait (reprisal1) ;
		registerTrait (reprisal2) ;
		registerTrait (reprisal3) ;
		registerTrait (waterlogged) ;
		registerTrait (surge1) ;
		registerTrait (surge2) ;
		registerTrait (flammable) ;
		registerTrait (nullField) ;
		registerTrait (rusty) ;
		
		registerTrait (frostbite) ;
		registerTrait (unburnt) ;
		registerTrait (enderference) ;
		registerTrait (stability) ;
	}
	
	/**
	 * Returns the ITrait object registered by the given identifier string.
	 * If no such trait exists, this will return a trait object that does nothing.
	 * 
	 */
	public static ITrait getTraitFromIdentifier (String identifier) {
		ITrait trait = traitRegistry.get(identifier) ;
		
		return (trait == null) ? nullTrait : trait ;
	}
	
	private static void registerTrait (ITrait trait) {
		if ( traitRegistry.containsKey(trait.getIdentifier()) ) return ;
		
		if (trait instanceof IModifier) {
			IModifier modifier = (IModifier) trait ;
			ItemStack item = modifier.getItemStack() ;
			
			if (item == null) {
				LibUtil.LogToFML(2, "Modifier registration error: Cannot register %s - no item is associated to it", modifier.getIdentifier());
				return ;
			}
			
			modifierRegistry.put(item.getUnlocalizedName(), modifier) ;

		}
		
		traitRegistry.put(trait.getIdentifier(), trait) ;
	}
	
	/**
	 * Returns the modifier associated to this itemstack; null if no such modifier exists
	 * @param item
	 * @return
	 */
	public static IModifier getModifierByItem (ItemStack item) {
		
		String name = item.getUnlocalizedName() ;
		
		return  modifierRegistry.get(name) ;
	}
	
	/**
	 * A trait that does nothing. The registry will return this instead of null to prevent nullpointers.
	 *
	 */
	private static class TraitNone extends AbstractTrait {
		
		public TraitNone () {
			super ("NOOP", TextFormatting.BLACK) ;
		}
	}
}
