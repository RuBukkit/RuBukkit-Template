package org.rubukkit.template.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.rubukkit.template.API.Settings;
import org.rubukkit.template.BukkitPluginMain;
import org.rubukkit.template.API.CommandAnswerException;
import org.rubukkit.template.Examples.DatabaseExample;
import org.rubukkit.template.Examples.Example_SaveLoadJSON;

public class BukkitCommands implements CommandExecutor
{
	private final BukkitPluginMain plugin;
	private final String[]         helpHeader = new String[2];
	public BukkitCommands(BukkitPluginMain plugin)
	{
		this.plugin = plugin;
	}
	public void onEnable()
	{
		for(String command : plugin.getDescription().getCommands().keySet())
		{
			final PluginCommand cmd = plugin.getCommand(command);
			if(cmd != null)
				cmd.setExecutor(this);
		}
		// Краткое описание плагина в двух строчках: оно нам пригодится ниже
		final PluginDescriptionFile description = plugin.getDescription();
		helpHeader[0] = "{_LP}" + description.getName()
			+ " {_LS}"   + description.getVersion()
			+ " {_LP}© " + description.getAuthors().get(0);
		helpHeader[1] = "{_LP}Website: {GOLD}" + description.getWebsite();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		try
		{
			execute(sender, cmd, args);
			// Никакая команда не была обработана? Вернём false
			return false;
		} catch(CommandAnswerException ex) {
			// Отправка текста с результатами выполнения команды
			// Каждую строчку будем предварять красивым префиксом
			for(String answer : ex.getMessageArray())
				sender.sendMessage(Settings.CHAT_PREFIX + answer);
		}
		// Если кто-то выбросил исключение, значит команды всё-таки обработались! Вернём true
		return true;
	}
	private void execute(CommandSender sender, Command cmd, String[] args) throws CommandAnswerException
	{
		switch(cmd.getName().toLowerCase())
		{
			// Для примера существует только одна команда, с различными подкомандами
			case "rbtmplt":
				// Если команде не переданы аргументы, выбросим краткое описание плагина
				if(args.length == 0)
					throw new CommandAnswerException(helpHeader);
				// Переходим к обработке команд
				processCommandHub(sender, args);
				// Если обработка ничего не сообщила игроку, она всё равно была! true
				throw new CommandAnswerException();
		}
	}
	public void processCommandHub(CommandSender sender, String[] args) throws CommandAnswerException
	{
		// Если никаких параметров передано не было, вернём базовую информацию:
		if(args == null || args.length == 0 || args[0] == null || "".equals(args[0]))
			throw new CommandAnswerException(helpHeader);
		// Первый аргумент в args — подкоманда.
		switch(args[0].toLowerCase())
		{
			// Определение того, кто вызвал команду: игрок или консоль?
			case "testwho":
				throw new CommandAnswerException(
					sender instanceof ConsoleCommandSender
						? "§aВы — §6консоль§a!"
						: "§aВы — §2игрок§a!");
			// Имеет ли вызывающий какое-то определённое право?
			case "testperm":
				throw new CommandAnswerException(
					sender.hasPermission("rbtmplt.testperm")
						? "§aУ Вас есть нужное право! :)"
						: "§cНет прав! :(");
			// Пример создания таблицы в СУБД MySQL и занесения в неё строки
			// Требует указания правильных данных подключения в config.yml.
			case "testdb":
				DatabaseExample.test(plugin, sender);
				return;
			case "jw":
				// Пример сериализации какого-то класса в Json строку (файл)
				new Example_SaveLoadJSON(plugin).testSave(plugin.getDataFolder());
				return;
			case "jr":
				// Пример десериализации какого-то класса из Json строки (файла)
				new Example_SaveLoadJSON(plugin).testLoad(plugin.getDataFolder());
				return;
			// Типичная ситуация: перезагрузка плагина
			case "reload":
				if(sender.hasPermission("rbtmplt.admin"))
				{
					final PluginManager pluginMan = plugin.getServer().getPluginManager();
					// В методе onDisable следует сохранить все рабочие данные (обычно кроме config.yml)
					pluginMan.disablePlugin(plugin);
					// А в методе onEnable — восстановить их из нужных источников
					pluginMan.enablePlugin(plugin);
					throw new CommandAnswerException("§aПлагин успешно перезагружен!");
				}
				throw new CommandAnswerException("§cНет прав! :(");
			default:
				break;
		}
		throw new CommandAnswerException("§cНеизвестная подкоманда");
	}
}
