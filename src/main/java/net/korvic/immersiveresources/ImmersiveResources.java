package net.korvic.immersiveresources;

import co.lotc.core.bukkit.command.Commands;
import net.korvic.immersiveresources.cmd.NodeCommands;
import net.korvic.immersiveresources.cmd.QuarryCommands;
import net.korvic.immersiveresources.resources.node.Node;
import net.korvic.immersiveresources.resources.quarry.Quarry;
import net.korvic.immersiveresources.resources.ResourcesList;
import net.korvic.immersiveresources.resources.quarry.QuarrySigns;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

public final class ImmersiveResources extends JavaPlugin {

	public static final String PREFIX = (ChatColor.DARK_GREEN + "");
	public static final String ALT_COLOR = (ChatColor.YELLOW + "");

	private static ImmersiveResources instance;
	public static ImmersiveResources get() {
		return instance;
	}
	private static FileConfiguration config;
	public static FileConfiguration getConfigFile() {
		return config;
	}

	@Override
	public void onEnable() {
		instance = this;

		saveDefaultConfig();
		config = getConfig();
		loadFromConfig();

		Bukkit.getPluginManager().registerEvents(new QuarrySigns(), this);

		registerParameters();

		Commands.build(getCommand("immersiveresources"), QuarryCommands::new);
		Commands.build(getCommand("immersiveresources"), NodeCommands::new);

		registerOmniEvents();
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	public void loadFromConfig() {
		// Load existing nodes/pits
	}

	// Register ResourcePit and Worlds as a tab-autocomplete.
	private void registerParameters() {
		Commands.defineArgumentType(Quarry.class)
				.defaultName("Quarry")
				.defaultError("Failed to find a quarry with that name.")
				.completer(() -> ResourcesList.getQuarryList().stream().map(Quarry::getName).collect(Collectors.toList()))
				.mapperWithSender((sender, name) -> ResourcesList.getPit(name.toUpperCase()))
				.register();

		Commands.defineArgumentType(World.class)
				.defaultName("World")
				.defaultError("Failed to find a world by that name.")
				.completer(() -> Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList()))
				.mapperWithSender((sender, name) -> Bukkit.getWorld(name))
				.register();

		Commands.defineArgumentType(Node.class)
				.defaultName("Node ID")
				.defaultError("Failed to find a node with that ID.")
				.mapperWithSender((sender, id) -> ResourcesList.getNode(Integer.parseInt(id)))
				.register();
	}

	private void registerOmniEvents() {
		// Register refill events.
	}

}
