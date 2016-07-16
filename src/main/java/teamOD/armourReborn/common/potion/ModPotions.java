package teamOD.armourReborn.common.potion;

import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.common.lib.LibMisc;

public final class ModPotions {
	
	public static PotionMod enderference ;
	
	public static void init () {
		enderference = new PotionMod (new ResourceLocation(LibMisc.MOD_ID, "enderference"), true, false, 0x21985f) ;
	}
}
