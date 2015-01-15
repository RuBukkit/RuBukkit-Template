package org.rubukkit.template.examples;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.rubukkit.template.BukkitPluginMain;

public class ExampleProcessCommands
{
	public static void processCommandHub(BukkitPluginMain plugin, CommandSender sender, String[] args) throws CommandAnswerException
	{
		// Если никаких параметров передано не было, вернём базовую информацию:
		if(args == null || args.length == 0)
			throw new CommandAnswerException(new String[]
			{
				// Название плагина, версия, авторство
				"§a" + plugin.getDescription().getName()
					+ " v" + plugin.getDescription().getVersion()
					+ " © " + plugin.getDescription().getAuthors().get(0),
				// Ссылка на "домашнюю страницу" плагина
				"§aWebsite: §e" + plugin.getDescription().getWebsite(),
			});
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
			// Типичная ситуация: перезагрузка плагина
			case "reload":
				if(sender.hasPermission("rbtmplt.admin"))
				{
					final PluginManager pluginMan = plugin.getServer().getPluginManager();
					// В методе onDisable следует сохранить все рабочие данные
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
