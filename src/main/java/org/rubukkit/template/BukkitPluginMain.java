package org.rubukkit.template;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.rubukkit.template.Bukkit.BukkitCommands;
import org.rubukkit.template.Bukkit.BukkitListener;
import org.rubukkit.template.Bukkit.BukkitSettings;
import org.rubukkit.template.Bukkit.IntegrateVault;

public final class BukkitPluginMain extends JavaPlugin
{
	public final Logger         logger   = super.getLogger();
	public final BukkitSettings settings = new BukkitSettings(this);
	public final BukkitListener listener = new BukkitListener(this);
	public final BukkitCommands commands = new BukkitCommands(this);
	public final IntegrateVault vaultAPI = new IntegrateVault(this);
	@Override
	public void onLoad()
	{
		// Успешно загружен, что же ещё
		logger.info("Plugin has been loaded.");
	}
	@Override
	public void onEnable()
	{
		// Чтение и проверка настроек на корректность
		try
		{
			settings.onEnable();
		} catch(IOException ex) {
			// С настройками не всё в порядке, показываем ошибку и не включаемся дальше
			logger.log(Level.WARNING, "Cannot process config.yml, disabling!: {0}", ex);
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		// Регистрируем обработчики событий
		listener.onEnable();
		// Заменяем обработчики всех зарегистрированных команд
		commands.onEnable();
		// Успешно включён
		logger.info("Plugin has been successfully enabled.");
	}
	@Override
	public void onDisable()
	{
		// Отмена регистрации всех обработчиков событий
		getServer().getServicesManager().unregisterAll(this);
		// Отмена всех назначенных на будущее блоков кода
		getServer().getScheduler().cancelTasks(this);
		// Успешно выключен
		logger.info("Plugin has been successfully disabled.");
	}
}
