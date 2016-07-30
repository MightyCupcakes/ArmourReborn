package teamOD.armourReborn.common.modifiers;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TraitNullField extends AbstractTrait{
	
	public static ImmutableList<Potion> potionIDs = ImmutableList.of(MobEffects.POISON, MobEffects.BLINDNESS, MobEffects.NAUSEA, MobEffects.WEAKNESS) ;
	public static final String NULLFIELD_COOLDOWN = "nullfield" + IModifier.COOLDOWN ;
	
	public TraitNullField() {
		super("null Field", TextFormatting.GRAY);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armour) {
		List<PotionEffect> toRemove = Lists.newLinkedList() ;
		boolean hasRemoved = false ;
		
		if (player.worldObj.isRemote) return ;
		
		if (!armour.getTagCompound().hasKey(NULLFIELD_COOLDOWN)) {
			armour.getTagCompound().setLong(NULLFIELD_COOLDOWN, world.getTotalWorldTime()) ;
		}
		
		long time = armour.getTagCompound().getLong(NULLFIELD_COOLDOWN) ;
		
		if (world.getTotalWorldTime() >= time) {
		
			for (PotionEffect potion: player.getActivePotionEffects()) {
				if ( potionIDs.contains(potion.getPotion()) ) {
					toRemove.add(potion) ;
				}
			}
			
			for (PotionEffect potion: toRemove) {
				player.removePotionEffect(potion.getPotion());
				hasRemoved = true ;
			}
			
			if (hasRemoved) {
				armour.getTagCompound().setLong(NULLFIELD_COOLDOWN, world.getTotalWorldTime() + (5 * 20));
			}
		}
	}
}
