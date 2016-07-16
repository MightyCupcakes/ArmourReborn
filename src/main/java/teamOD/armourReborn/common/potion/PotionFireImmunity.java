package teamOD.armourReborn.common.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class PotionFireImmunity extends PotionMod {

	public PotionFireImmunity(ResourceLocation location, boolean badEffect, boolean showInInventory, int colour) {
		super(location, badEffect, showInInventory, colour) ;
	}
	
	@Override
	public void applyEffect (EntityPlayer player) {
		setImmunity (player, true) ;
	}
	
	@Override
	public void removeEffect (EntityPlayer player) {
		setImmunity (player, false) ;
	}
	
	private void setImmunity (EntityPlayer player, boolean immune) {
		ReflectionHelper.setPrivateValue(Entity.class, player, immune, "isImmuneToFire", "field_70178_ae", "Y");
	}

}
