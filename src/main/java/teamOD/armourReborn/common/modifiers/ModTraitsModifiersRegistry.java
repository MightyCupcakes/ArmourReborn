package teamOD.armourReborn.common.modifiers;

import java.util.HashMap;

public class ModTraitsModifiersRegistry {
	
	protected static HashMap<String, ITrait> traitRegistry = new HashMap<String, ITrait> () ;
	
	public static ITrait evasion1 = new TraitEvasion(0) ;
	public static ITrait evasion2 = new TraitEvasion(1) ;
	public static ITrait evasion3 = new TraitEvasion(2) ;
	public static ITrait reprisal1 = new TraitReprisal(0) ;
	public static ITrait reprisal2 = new TraitReprisal(1) ;
	public static ITrait reprisal3 = new TraitReprisal(2) ;
	public static ITrait waterlogged = new TraitWaterlogged() ;
	
	public static void init () {
		registerTrait (evasion1) ;
		registerTrait (evasion2) ;
		registerTrait (evasion3) ;
		registerTrait (reprisal1) ;
		registerTrait (reprisal2) ;
		registerTrait (reprisal3) ;
		registerTrait (waterlogged) ;
	}
	
	public static ITrait getTraitFromIdentifier (String identifier) {
		return traitRegistry.get(identifier) ;
	}
	
	private static void registerTrait (ITrait trait) {
		if ( traitRegistry.containsKey(trait.getIdentifier()) ) return ;
		
		traitRegistry.put(trait.getIdentifier(), trait) ;
	}
}
