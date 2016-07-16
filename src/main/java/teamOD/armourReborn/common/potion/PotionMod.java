package teamOD.armourReborn.common.potion;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionMod extends Potion {
	
	private boolean hasEffect ;
	protected final Multimap<String, AttributeModifier> attributes = HashMultimap.create();
	
	public PotionMod (ResourceLocation location, boolean badEffect, boolean showInInventory, int colour) {
		super (badEffect, colour) ;
		
		setPotionName("potion." + location.getResourcePath());

	    this.hasEffect = showInInventory;
	    potionRegistry.register(-1, location, this);
	    
	    fillAttributes () ;
		
	}
	
	public void applyEffect (EntityPlayer player) {
		player.getAttributeMap().applyAttributeModifiers(attributes) ;
	}
	
	public void removeEffect (EntityPlayer player) {
		player.getAttributeMap().removeAttributeModifiers(attributes) ;
	}
	
	public void fillAttributes () {
		// NO OP
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
