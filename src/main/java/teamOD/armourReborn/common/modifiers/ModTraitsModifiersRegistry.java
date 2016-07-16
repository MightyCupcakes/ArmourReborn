package teamOD.armourReborn.common.modifiers;

import java.util.HashMap;

import net.minecraft.util.text.TextFormatting;

public class ModTraitsModifiersRegistry {
	
	protected static HashMap<String, ITrait> traitRegistry = new HashMap<String, ITrait> () ;
	
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
	public static ITrait frostbite = new ModifierFrostbite() ;	
	public static ITrait unburnt1 = new ModifierUnburnt(1) ;	
	public static ITrait enderference = new ModifierEnderference() ;	
	public static ITrait stability = new ModifierUnyielding() ;
	public static ITrait nullField = new TraitNullField() ;
	public static ITrait rusty = new TraitRust() ;	
	
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
		registerTrait (unburnt1) ;
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
		
		traitRegistry.put(trait.getIdentifier(), trait) ;
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
