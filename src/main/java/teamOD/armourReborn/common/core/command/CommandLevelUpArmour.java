package teamOD.armourReborn.common.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;

public class CommandLevelUpArmour extends CommandBase {

	@Override
	public String getCommandName() {
		return "leveluparmour" ;
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "<player> <expAmt>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getPlayer (server, sender, args[0]) ;

		if (player != null) {
			ItemStack stack = player.getHeldItemMainhand() ;

			if (stack != null && stack.getItem() instanceof ItemModArmour) {
				ItemModArmour armour = (ItemModArmour) stack.getItem() ;

				armour.addExp(stack, player, parseInt(args[1]) );

			} else if (stack == null) {
				throw new CommandException ("no item in main hand.") ;

			} else if ( ! (stack.getItem() instanceof ItemModArmour) ) {
				throw new CommandException ("item in main hand is not from the mod ArmourReborn") ;
			}
		}
		
	}

}
