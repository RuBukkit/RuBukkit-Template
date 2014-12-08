package org.rubukkit.template;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPluginMain extends JavaPlugin implements Listener
{
	public static final Logger consoleLog = Bukkit.getLogger();
	@Override
	public void onLoad()
	{
		saveDefaultConfig();
		consoleLog.log(Level.INFO, "[RBTemplate] Plugin has been loaded.");
	}
	@Override
	public void onEnable()
	{
		// Register event's dispatcher
		getServer().getPluginManager().registerEvents(this, this);
		// Read settings 
		reloadConfig();
		// Done
		consoleLog.log(Level.INFO, "[RBTemplate] Plugin has been successfully enabled.");
	}
	@Override
	public void onDisable()
	{
		// Save settings
		saveConfig();
		// Unregister all event listeners
		getServer().getServicesManager().unregisterAll(this);
		consoleLog.info("[RBTemplate] Plugin has been disabled.");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		switch(label.toLowerCase())
		{
			case "rubukkit":
				sender.sendMessage("Yeah, this is RuBukkit!");
				return true;
			case "testperm":
				if(sender.hasPermission("someperm"))
					sender.sendMessage("someperm yes! :)");
				else
					sender.sendMessage("someperm no! :(");
				return true;
			case "testconsole":
				if(sender instanceof ConsoleCommandSender)
					sender.sendMessage("You are console! :)");
				else
					sender.sendMessage("You are player! :(");
				return true;
		}
		return false;
	}
}