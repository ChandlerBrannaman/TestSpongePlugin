package com.dust2.commands;

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

public class HealExecutor implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	{
		
		Player player;
		
		if(!args.hasAny("player"))
		{
			if(!(src instanceof Player))
			{
				src.sendMessage(Text.of(TextColors.RED, "Console cannot heal itself. You must specify a player to be healed!"));
				return CommandResult.success();
			}
			
			player = (Player)src;
			heal(player);
			player.sendMessage(Text.of(TextColors.RED, "You were healed"));
			
			return CommandResult.success();
			
		}else
		{
			
			player = args.<Player>getOne("player").get();
			heal(player);
			player.sendMessage(Text.of(TextColors.RED, "You were healed"));
			src.sendMessage(Text.of(TextColors.RED, player.getName() + " was healed."));
			
			return CommandResult.success();
		}
		
	}

	private void heal(Player player)
	{
	
		DataTransactionResult health = player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
		
		DataTransactionResult hunger = player.offer(Keys.FOOD_LEVEL, 20);
		
	}
	
}
