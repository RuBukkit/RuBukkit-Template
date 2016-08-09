package org.rubukkit.template.Bukkit;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.rubukkit.template.API.Settings;
import org.rubukkit.template.BukkitPluginMain;

public final class BukkitSettings implements Settings
{
	private final BukkitPluginMain plugin;
	public BukkitSettings(BukkitPluginMain plugin)
	{
		this.plugin = plugin;
	}
	@Override
	public void onEnable() throws IOException
	{
		processUpdates();
		// Процедура обновлений закончена, можно смело считывать все параметры
		readSettings();
	}
	private void processUpdates() throws IOException
	{
		FileConfiguration config = plugin.getConfig();
		switch(config.getInt("internal.version", 0))
		{
			case 0:
				// Это может случиться, только если файл config.yml пуст. Удалим его.
				new File(plugin.getDataFolder(), "config.yml").delete();
				// Заменим его на оригинал из тела .jar плагина
				plugin.saveDefaultConfig();
				// Перечитаем его содержимое
				plugin.reloadConfig();
				config = plugin.getConfig();
			case 1:
				// Устаревшая версия: добавляю/удаляю разные поля конфига
				config.set("settings.connection.hostname", "localhost:3306");
				config.set("settings.connection.database", "minecraft");
				config.set("settings.connection.username", "user1");
				config.set("settings.connection.password", "pass1");
				config.set("settings.connection.prefixes", "rubukkit_example_");
				// Без оператора break сразу переходим к следующему блоку обновлений
			case 2:
				// TO DO HERE
				// В конце всех блоков обновления мы установим самую актуальную версию, сохраним и перечитаем
				// файл. Это можно было сделать и ниже, но так мы без необходимости вообще файл не трогаем.
				config.set("internal.version", CONFIG_VERSION);
				plugin.saveConfig();
				plugin.reloadConfig();
			case CONFIG_VERSION:
				// Актуальная версия
				break;
			default:
				// Если нам попалась вообще непонятно какая версия: может быть новее, чем мы знаем, может быть это баг.
				// При желании можно вернуть какую-нибудь ошибку, например:
				throw new IOException("Incorrect config.yml version!");
		}
	}
	private void readSettings()
	{
		final FileConfiguration config = plugin.getConfig();
		// TO DO HERE
	}
}
