package teamOD.armourReborn.common.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionMod extends Potion {
	
	private boolean hasEffect ;
	
	public PotionMod (ResourceLocation location, boolean badEffect, boolean showInInventory, int colour) {
		super (badEffect, colour) ;
		
		setPotionName("potion." + location.getResourcePath());

	    this.hasEffect = showInInventory;
	    potionRegistry.register(-1, location, this);
		
	}
	
	@Override
	public boolean shouldRenderInvText(PotionEffect effect) {
		return hasEffect ;
	}
	
	public int getLevel(EntityLivingBase entity) {
		PotionEffect effect = entity.getActivePotionEffect(this);
		if(effect != null) {
			return effect.getAmplifier();
		}
		return 0;
	}

	@Override
	public boolean shouldRender(PotionEffect effect) {
		return hasEffect ;
	}
}
