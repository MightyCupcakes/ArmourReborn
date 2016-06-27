package teamOD.armourReborn.common.lib;

public final class LibMisc {

	// Mod Constants
	public static final String MOD_ID = "ArmourReborn";
	public static final String MOD_NAME = MOD_ID;
	public static final String BUILD = "GRADLE:BUILD";
	public static final String VERSION = "GRADLE:VERSION-" + BUILD;
	public static final String DEPENDENCIES = "required-after:Forge@[12.16.1.1887,]" ;
	public static final String MC_VERSIONS = "[1.9]";
	public static final String PREFIX_MOD = "armourreborn" ;

	// Network Contants
	public static final String NETWORK_CHANNEL = MOD_ID;
	
	// Proxy Constants
	public static final String PROXY_COMMON = "teamOD.armourReborn.common.core.proxy.CommonProxy" ;
	public static final String PROXY_CLIENT = "teamOD.armourReborn.client.core.proxy.ClientProxy" ;

	public static final int[] CONTROL_CODE_COLORS = new int[] {
		0x000000, 0x0000AA, 0x00AA00, 0x00AAAA,
		0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA,
		0x555555, 0x5555FF, 0x55FF55, 0x55FFFF,
		0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
	};

}