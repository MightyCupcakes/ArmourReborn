package teamOD.armourReborn.common.lib;

public final class LibItemNames {
	public static final String FORGE_BLOCK = "forgeBlock" ;
	public static final String FORGE_CONTROLLER = "forgeController" ;
	public static final String FORGE_HEATER = "forgeHeater" ;
	public static final String FORGE_ANVIL = "forgeAnvil" ;
	public static final String MATERIALS = "materials" ;
	public static final String oreMATERIALS = "oreMaterials" ;
	public static final String ModBook = "knowledgeTome" ;
	public static final String ModDust = "AghanimsDust" ;
	
	public static final String[] MATERIALS_NAMES = {
			"ingotSteel",
			"ingotAluminium",
			"ingotCopper",
			"ingotAluminiumAlloy"
	} ;
	
	public static final String[] ORE_MATERIALS_NAMES = {
			"oreAluminium",
			"oreCopper"
	} ;
	
	public enum ArmourTypeNames {
		LEATHER ("leather"),
		CHAIN ("chain"),
		PLATE ("plate") ;
		
		private String name ;
		
		private ArmourTypeNames (String name) {
			this.name = name ;
		}
		
		public String getName () {
			return name ;
		}
		
		public boolean matches (ArmourTypeNames other) {
			return other.getName().equals(this.getName()) ;
		}
	}
	
}
