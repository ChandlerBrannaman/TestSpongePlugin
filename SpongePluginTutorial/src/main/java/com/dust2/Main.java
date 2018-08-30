package com.dust2;

import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.dust2.commands.FlyExecutor;
import com.dust2.commands.GodExecutor;
import com.dust2.commands.HealExecutor;
import com.dust2.commands.SendMessageExecutor;
import com.dust2.commands.SpongePluginTutorialExecuter;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

@Plugin(id="tutorialplugin", name="TutorialPlugin", version="1.0")
public class Main {
	
	@Inject
	Game game;
	
	@Inject
	private Logger logger;
	
	public Logger getLogger(){
		return logger;
	}

	@Listener
	public void onInitialize(GameInitializationEvent event){
		this.getLogger().info("Loading...");
		
		CommandSpec tutorialCommandSpec = CommandSpec.builder()
				.description(Text.of("Says different things based on the sender"))
				.executor(new SpongePluginTutorialExecuter())
				.build();
		
		CommandSpec sendMessageCommandSpec = CommandSpec.builder()
				.description(Text.of("Sends a message to a player"))
				.permission("Dust2Plugin.command.message")
				.arguments(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.remainingJoinedStrings(Text.of("message")))
				.executor(new SendMessageExecutor())
				.build();
		
		CommandSpec healCommand = CommandSpec.builder()
				.description(Text.of("Heals the player or yourself"))
				.permission("Dust2Plugin.command.heal")
				.arguments(GenericArguments.optional(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(new HealExecutor())
				.build();
		
		CommandSpec godCommand = CommandSpec.builder()
				.description(Text.of("Toggles God mode on a player or yourself"))
				.permission("Dust2Plugin.command.god")
				.arguments(GenericArguments.optional(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(new GodExecutor())
				.build();
		
		CommandSpec flyCommand = CommandSpec.builder()
				.description(Text.of("Toggles creative fly on a player or yourself"))
				.permission("Dust2Plugin.command.fly")
				.arguments(GenericArguments.optional(
						GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(new FlyExecutor())
				.build();
				
		
		game.getCommandManager().register(this, tutorialCommandSpec, Lists.newArrayList("tutorial", "tut"));
		game.getCommandManager().register(this, sendMessageCommandSpec, Lists.newArrayList("msg", "message", "m"));
		game.getCommandManager().register(this, healCommand, "heal");
		game.getCommandManager().register(this, godCommand, "god");
		game.getCommandManager().register(this, flyCommand, "fly");
		game.getEventManager().registerListeners(this, new DamageListener());
		
		
		this.getLogger().info("Enabled.");
	}
	
	public class DamageListener
	{
		
		GodExecutor gExc;

		
		@Listener
		public void onDamageEvent(DamageEntityEvent e)
		{
			
			Entity ent = e.getTargetEntity();
			
			if(!(ent instanceof Player))
			{				
				return;				
			}else
			{
			
				Player player = (Player)ent;
				gExc = new GodExecutor();
				List<String> gods = gExc.getList();
				
				if(!gods.contains(player.getName()))
				{					
					return;					
				}else
				{					
					e.setCancelled(true);					
				}				
			}			
		}
		
		@Listener
		public void onInteractionEvent(InteractEntityEvent e)
		{
			
			Entity ent = e.getTargetEntity();
			
			if(!(ent instanceof Player))
			{				
				return;				
			}else
			{
				
				Player player = (Player)ent;
				gExc = new GodExecutor();
				List<String> gods = gExc.getList();
				
				if(!gods.contains(player.getName()))
				{					
					return;					
				}else
				{					
					e.setCancelled(true);					
				}
				
			}
			
		}
		
		@Listener
		public void onFallForFlyDeactivation(DamageEntityEvent event, @Root DamageSource source)
		{
		
			if (!source.getType().equals(DamageTypes.FALL))
			{
				return;
			}
			
			Entity ent = event.getTargetEntity();
			
			if(!(ent instanceof Player))
			{
				return;
			}else
			{
				Player player = (Player)ent;
				
				if (!FlyExecutor.needsOneTimeFallDamageCancel.contains(player.getName()))
				{
					return;
				}else
				{					
					event.setCancelled(true);
					FlyExecutor.needsOneTimeFallDamageCancel.remove(player.getName());					
				}
			}
			
		}
		
	}
	
}
