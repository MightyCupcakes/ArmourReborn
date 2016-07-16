package teamOD.armourReborn.common.modifiers;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TraitNullField extends AbstractTrait{
	
	public static ImmutableList<Integer> potionIDs = ImmutableList.of(19, 20) ;

	public TraitNullField() {
		super("null Field", TextFormatting.GRAY);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armour) {
		List<PotionEffect> toRemove = Lists.newLinkedList() ;
		
		if (player.worldObj.isRemote) return ;
		
		for (PotionEffect potion: player.getActivePotionEffects()) {
			if ( potionIDs.contains(Potion.getIdFromPotion(potion.getPotion())) ) {
				toRemove.add(potion) ;
			}
		}
		
		for (PotionEffect potion: toRemove) {
			player.removePotionEffect(potion.getPotion());
		}
	}
}
