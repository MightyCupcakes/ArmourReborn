package teamOD.armourReborn.common.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import teamOD.armourReborn.common.item.equipment.ItemModArmour;

public class CommandGiveModArmour extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "<player>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
	}

}
