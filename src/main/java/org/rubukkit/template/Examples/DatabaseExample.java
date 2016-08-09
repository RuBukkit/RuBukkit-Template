package org.rubukkit.template.Examples;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rubukkit.template.BukkitPluginMain;

public class DatabaseExample
{
	public static void test(BukkitPluginMain plugin, CommandSender sender)
	{
		// Установка соединения
		final DatabaseConnectionMySQL db = new DatabaseConnectionMySQL(plugin);
		if(db.connect())
		{
			final String testTable = db.fullQuotedTableName("dbtest");
			// Создание таблицы
			final String createTable
				= "CREATE TABLE IF NOT EXISTS " + testTable + " (\n"
				+ "  `id`            INT UNSIGNED NOT NULL AUTO_INCREMENT,\n"
				+ "  `timestamp`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
				+ "  `insert_source` VARCHAR(255) NULL,\n"
				+ "  PRIMARY KEY (`id`),\n"
				+ "  UNIQUE INDEX `id_UNIQUE` (`id` ASC))\n"
				+ "ENGINE = InnoDB\n"
				+ "DEFAULT CHARACTER SET = utf8\n"
				+ "COLLATE = utf8_general_ci;";
			db.executeUpdate(createTable);
			// Вставка строки
			final String source = (sender instanceof Player
				? ((Player)sender).getCustomName()
				: "*SERVER*");
			final String insertInto
				= "INSERT INTO " + testTable + " (`insert_source`)\n"
				+ "VALUES ('" + source + "');";
			db.executeUpdate(insertInto);
			// Отключение
			db.disconnect();
		}
	}
}
