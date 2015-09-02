package org.rubukkit.template;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.rubukkit.template.examples.*;

public final class BukkitPluginMain extends JavaPlugin implements Listener
{
	public static final Logger consoleLog = Bukkit.getLogger();
	public static final String pluginNameShort = "rbtmplt";
	@Override
	public void onLoad()
	{
		// Если в папке плагина отсутствует config.yml, этот метод берёт его из тела плагина и копирует в папку
		saveDefaultConfig();
		consoleLog.log(Level.INFO, "[{0}] Plugin has been loaded.", getDescription().getName());
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
		consoleLog.log(Level.INFO, "[{0}] Plugin has been successfully enabled.", getDescription().getName());
	}
	@Override
	public void onDisable()
	{
		// Save settings
		saveConfig();
		// Unregister all event listeners
		getServer().getServicesManager().unregisterAll(this);
		consoleLog.log(Level.INFO, "[{0}] Plugin has been disabled.", getDescription().getName());
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		// Краткое описание плагина в двух строчках: оно нам пригодится ниже
		final PluginDescriptionFile desc = this.getDescription();
		final String[] info = new String[]
		{
			"{_LP}" + desc.getName() + " {_LS}" + desc.getVersion() + "{_LP} © " + desc.getAuthors().get(0),
			"{_LP}Website: {GOLD}" + desc.getWebsite(),
		};
		try
		{
			switch(command.getName().toLowerCase())
			{
				// Для примера существует только одна команда, с различными подкомандами
				case "rbtmplt":
					// Если команде не переданы аргументы, выбросим краткое описание плагина
					if(args.length == 0)
						throw new CommandAnswerException(info);
					// Переходим к обработке команд
					ExampleProcessCommands.processCommandHub(this, sender, args);
					// Если обработка ничего не сообщила игроку, она всё равно была! true
					return true;
			}
			// Никакая команда не была обработана? false
			return false;
		} catch(CommandAnswerException ex) {
			// Отправка текста с результатами выполнения команды
			for(String answer : ex.getMessageArray())
				// Каждую строчку будем предварять красивым префиксом
				sender.sendMessage("§2[" + pluginNameShort + "] §a" + answer);
		}
		// Если кто-то выбросил исключение, значит команды всё-таки обработались! true
		return true;
	}
}
