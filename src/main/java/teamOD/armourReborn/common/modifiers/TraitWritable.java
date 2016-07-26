package teamOD.armourReborn.common.modifiers;

import net.minecraft.util.text.TextFormatting;

public class TraitWritable extends AbstractTrait {
	public TraitWritable () {
		super ("writable", TextFormatting.WHITE) ;
	}
	
	@Override
	public int providesAdditionalModifier () {
		return 1 ;
	}
}
