package teamOD.armourReborn.common.modifiers;


import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import teamOD.armourReborn.common.item.ModItems;
import teamOD.armourReborn.common.lib.LibUtil;

public final class ModTraitsModifiersRegistry {
	
	private static Map<String, ITrait> traitRegistry = Maps.newHashMap() ;
	private static Map<String, IModifier[]> modifierRegistry = Maps.newHashMap() ;
	
	// Traits
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
	public static ITrait fireResist = new TraitFireResistant() ;
	public static ITrait angel = new TraitGuardianAngel() ;
	public static ITrait commanding1 = new TraitCommandingPresence(1) ;
	public static ITrait commanding2 = new TraitCommandingPresence(2) ;
	
	// Modifiers
	public static ITrait frostbite = new ModifierFrostbite(new ItemStack (ModItems.MOD_MODIFIERS_MATERIALS, 1, 1)) ;
	public static ITrait unburnt = new ModifierUnburnt(new ItemStack (ModItems.MOD_MODIFIERS_MATERIALS, 1, 2)) ;
	public static ITrait enderference = new ModifierEnderference(new ItemStack (Items.ender_pearl)) ;	
	public static ITrait stability = new ModifierUnyielding(new ItemStack (Blocks.sand)) ; // TODO
	public static ITrait reinforced1 = new ModifierReinforced(1, new ItemStack(Blocks.obsidian)) ;
	public static ITrait reinforced2 = new ModifierReinforced(2, new ItemStack(Blocks.obsidian, 9)) ;
	public static ITrait reinforced3 = new ModifierReinforced(3, new ItemStack(Blocks.obsidian, 27)) ;
	public static ITrait invisible1 = new ModifierInvisibility (1, new ItemStack(Items.skull)) ; // TODO
	public static ITrait invisible2 = new ModifierInvisibility (2, new ItemStack(Items.skull, 8)) ; // TODO
	public static ITrait invisible3 = new ModifierInvisibility (3, new ItemStack(Items.skull, 16)) ; // TODO
	public static ITrait expBoost = new ModifierExpTome (new ItemStack(Blocks.gold_block)) ;
	public static ITrait featherfall1= new ModifierShockAbsorber(1, new ItemStack(Items.feather, 16)) ;
	public static ITrait featherfall2 = new ModifierShockAbsorber(2, new ItemStack(Items.feather, 32)) ;
	public static ITrait featherfall3 = new ModifierShockAbsorber(3, new ItemStack(Items.feather, 64)) ;
	public static ITrait underTheSea = new ModifierDepthStrider(new ItemStack(Blocks.prismarine)) ;
	
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
		registerTrait (fireResist) ;
		registerTrait (angel) ;
		registerTrait (commanding1) ;
		registerTrait (commanding2) ;
		
		registerTrait (frostbite) ;
		registerTrait (unburnt) ;
		registerTrait (enderference) ;
		registerTrait (stability) ;
		registerTrait (reinforced1) ;
		registerTrait (reinforced2) ;
		registerTrait (reinforced3) ;
		registerTrait (invisible1) ;
		registerTrait (invisible2) ;
		registerTrait (invisible3) ;
		registerTrait (expBoost) ;
		registerTrait (featherfall1) ;
		registerTrait (featherfall2) ;
		registerTrait (featherfall3) ;
		registerTrait (underTheSea) ;
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
			
			String name = item.getUnlocalizedName() ;
			IModifier[] modifierList ;
			
			if (modifierRegistry.containsKey(name)) {
				IModifier[] currModifiers = modifierRegistry.get(name) ;
				modifierList = new IModifier[currModifiers.length + 1] ;
				
				int i = 0 ;
				
				for (IModifier mod : currModifiers) {
					if (modifier.getLevel() < mod.getLevel()) {
						modifierList[i] = modifier ;
						modifierList[++i] = mod ;
						
						i ++ ;
						
					} else {
						modifierList[i++] = mod ;
					}
				}
				
				if (i == currModifiers.length) {
					modifierList[i++] = modifier ;
				}
				
			} else {
				modifierList = new IModifier[] { modifier } ;
			}
			
			modifierRegistry.put(name, modifierList ) ;

		}
		
		traitRegistry.put(trait.getIdentifier(), trait) ;
	}
	
	/**
	 * Returns the modifiers associated to this itemstack; null if no such modifier exists
	 * @param item
	 * @return
	 */
	public static List<IModifier> getModifierByItem (ItemStack item) {
		
		String name = item.getUnlocalizedName() ;
		
		return (modifierRegistry.containsKey(name)) ? ImmutableList.copyOf(modifierRegistry.get(name)) : ImmutableList.<IModifier>of() ;
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
