package com.dust2.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class FlyExecutor implements CommandExecutor{

	public static List<String> flyList = new ArrayList<>();
	public static List<String> needsOneTimeFallDamageCancel = new ArrayList<>();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Player player;
		
		if (!args.hasAny("player"))
		{
		
			if(!(src instanceof Player))
			{
			
				src.sendMessage(Text.of(TextColors.RED, "Console can not toggle fly on themselves. Please provide a player name!"));
				return CommandResult.success();
				
			}else
			{
				
				player = (Player)src;
				
				if (flyList.contains(player.getName()))
				{
					deactivateFly(player);
					flyList.remove(player.getName());
					needsOneTimeFallDamageCancel.add(player.getName());
					return CommandResult.success();
				}else
				{
					activateFly(player);
					flyList.add(player.getName());
					return CommandResult.success();
				}
				
			}
		
		}else
		{
			
			player = args.<Player>getOne("player").get();
			
			if (flyList.contains(player.getName()))
			{
				deactivateFly(player);
				flyList.remove(player.getName());
				needsOneTimeFallDamageCancel.add(player.getName());
				src.sendMessage(Text.of(TextColors.RED, "Disabled fly for " + player.getName()));
				return CommandResult.success();
			}else
			{
				activateFly(player);
				flyList.add(player.getName());
				src.sendMessage(Text.of(TextColors.RED, "Enabled fly for " + player.getName()));
				return CommandResult.success();
			}			
		}	
	}

	private void activateFly(Player player)
	{	
		DataTransactionResult fly = player.offer(Keys.CAN_FLY, true);
		player.sendMessage(Text.of(TextColors.RED, "Fly has been enabled"));
	}
	
	private void deactivateFly(Player player)
	{		
		DataTransactionResult fly = player.offer(Keys.CAN_FLY, false);
		DataTransactionResult currentlyFlying = player.offer(Keys.IS_FLYING, false);
		player.sendMessage(Text.of(TextColors.RED, "Fly has been disabled"));
	}
	
}
