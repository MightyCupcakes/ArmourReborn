package teamOD.armourReborn.common.modifiers;

import java.util.HashMap;

public class ModTraitsModifiersRegistry {
	
	protected static HashMap<String, ITrait> traitRegistry = new HashMap<String, ITrait> () ;
	
	public static void init () {
		
	}
	
	public static ITrait getTraitFromIdentifier (String identifier) {
		return traitRegistry.get(identifier) ;
	}
}
