package com.dust2.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SendMessageExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	{
		
		Player player = args.<Player>getOne("player").get();
		String message = args.<String>getOne("message").get();
		
		player.sendMessage(Text.builder(message).color(TextColors.GOLD).build());
		
		return CommandResult.success();
		
	}

	
	
}
