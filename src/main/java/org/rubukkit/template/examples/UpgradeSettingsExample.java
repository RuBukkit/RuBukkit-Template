package org.rubukkit.template.examples;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.rubukkit.template.BukkitPluginMain;

public final class UpgradeSettingsExample
{
	public static void checkAndProcess(BukkitPluginMain plugin) throws IOException
	{
		FileConfiguration config = plugin.getConfig();
		switch(config.getInt("internal.version", 0))
		{
			case 0:
				// Это может случиться если config.yml пуст; заменим его на оригинал
				new File(plugin.getDataFolder(), "config.yml").delete();
				plugin.saveDefaultConfig();
				// Я не уверен в том, что нужно так много действий, но пускай будут, они логичны
				plugin.reloadConfig();
				config = plugin.getConfig();
			case 1:
				// Устаревшая версия: добавляю/удаляю разные поля конфига
				config.set("settings.connection.hostname", "localhost:3306");
				config.set("settings.connection.database", "minecraft");
				config.set("settings.connection.username", "user1");
				config.set("settings.connection.password", "pass1");
				config.set("settings.connection.prefixes", "rubukkit_example_");
				// Переходим к следующей версии...
				config.set("internal.version", 2);
			case 2:
				// Актуальная версия
			/*
				config.set("internal.version", 3);
			case 3:
			*/
				plugin.saveConfig();
				return;
			default:
				// В этом месте при желании можно вернуть какую-нибудь ошибку, например:
				throw new IOException("Incorrect config.yml version!");
		}
	}
}
