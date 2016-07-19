package teamOD.armourReborn.common.potion;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.common.lib.LibMisc;

public final class ModPotions {
	
	private static Map<String, PotionMod> modPotionsRegistry = Maps.newHashMap() ;
	
	public static PotionMod enderference ;
	public static PotionMod stability ;
	public static PotionMod fireImmune ;
	
	// Potions that does nothing. To prevent overlaps.
	public static PotionMod guardianPotion ;
	
	public static void init () {
		enderference = new PotionMod (new ResourceLocation(LibMisc.MOD_ID, "enderference"), true, false, 0x21985f) ;
		
		guardianPotion = new PotionMod (new ResourceLocation(LibMisc.MOD_ID, "guardianPotion"), false, false, 0x000000) ;
		
		stability = new PotionStability (new ResourceLocation(LibMisc.MOD_ID, "stability"), false, false, 0x000000) ;
		registerModPotion (stability) ;
		
		fireImmune = new PotionFireImmunity (new ResourceLocation(LibMisc.MOD_ID, "fireimmunity"), false, false, 0x000000) ;
		registerModPotion (fireImmune) ;
	}
	
	/**
	 * Register this potion for handling in LivingUpdateEvent
	 * @param potion
	 */
	private static void registerModPotion (PotionMod potion) {
		modPotionsRegistry.put(potion.getName(), potion) ;
	}
	
	public static Iterable<PotionMod> getModPotions () {
		return modPotionsRegistry.values() ;
	}
}
