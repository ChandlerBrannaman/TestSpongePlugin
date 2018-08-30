package com.dust2.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class GodExecutor implements CommandExecutor{

	
	public static List<String> gods = new ArrayList<>();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	{
		
		Player player;
		
		if(!args.hasAny("player"))
		{
		
			if(!(src instanceof Player))
			{
				
				src.sendMessage(Text.of(TextColors.RED, "Console cannot toggle God Mode on itself. Please provide a player name!"));
				return CommandResult.success();
				
			}
			
			player = (Player)src;
			
			if(gods.contains(player.getName())){
				gods.remove(player.getName());
				player.sendMessage(Text.of(TextColors.RED, "God Mode Disabled"));
				return CommandResult.success();
			}else
			{
				gods.add(player.getName());
				player.sendMessage(Text.of(TextColors.RED, "God Mode Enabled"));
				return CommandResult.success();
			}
			
		}
		
		player = args.<Player>getOne("player").get();
		
		if(gods.contains(player.getName())){
			gods.remove(player.getName());
			player.sendMessage(Text.of(TextColors.RED, "God Mode Disabled"));
			return CommandResult.success();
		}else
		{
			gods.add(player.getName());
			player.sendMessage(Text.of(TextColors.RED, "God Mode Enabled"));
			src.sendMessage(Text.of(TextColors.RED, "God Mode Enabled for " + player.getName()));
			return CommandResult.success();
		}
		
	}
	
	public List<String> getList()
	{
		return gods;
	}

	
	
}
