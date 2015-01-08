package org.rubukkit.template;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rubukkit.template.examples.*;

public final class BukkitPluginMain extends JavaPlugin implements Listener
{
	public static final Logger consoleLog = Bukkit.getLogger();
	@Override
	public void onLoad()
	{
		// Если в папке плагина отсутствует config.yml, этот метод берёт его из тела плагина и копирует в папку
		saveDefaultConfig();
		consoleLog.log(Level.INFO, "[RBTemplate] Plugin has been loaded.");
	}
	@Override
	public void onEnable()
	{
		// Register event's dispatcher
		getServer().getPluginManager().registerEvents(this, this);
		try
		{
			// Чтение и проверка настроек на корректность
			reloadConfig();
			// Пример того, как можно обновлять файл настроек при увеличении версии плагина
			UpgradeSettingsExample.checkAndProcess(this);
		} catch(IOException ex) {
			// Если при проверке файла настроек возникла ошибка...
		}
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
			case "testdb":
				DatabaseExample.test(this, sender);
				return true;
		}
		return false;
	}
}
