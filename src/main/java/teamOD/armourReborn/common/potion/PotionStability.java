package teamOD.armourReborn.common.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;
import teamOD.armourReborn.common.lib.LibUtil;

public class PotionStability extends PotionMod {

	public PotionStability(ResourceLocation location, boolean badEffect, boolean showInInventory, int colour) {
		super(location, badEffect, showInInventory, colour);
	}
	
	@Override
	public void fillAttributes () {
		attributes.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getAttributeUnlocalizedName(), new AttributeModifier(LibUtil.getPrefix("stability"), 1, 0)) ;
	}

}
