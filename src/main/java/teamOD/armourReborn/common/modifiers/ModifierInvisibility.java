package teamOD.armourReborn.common.modifiers;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ModifierInvisibility extends AbstractModifier{
	
	public ModifierInvisibility (int level, ItemStack item) {
		super ("invisibility", level, TextFormatting.DARK_PURPLE, item) ;
	}
	
	@Override
	public void onPlayerTargeted (EntityLiving attacker, EntityPlayer player, ItemStack armour) {
		int level = this.getLevel() ;
		
		if (level >= 1 && attacker instanceof EntityZombie) {
			attacker.setAttackTarget(null) ;
		}
		
		else if (level >= 2 && attacker instanceof EntitySkeleton) {
			attacker.setAttackTarget(null) ;
		}
		
		else if (level >= 3 && attacker instanceof EntityCreeper) {
			attacker.setAttackTarget(null) ;
		}
	}
}
