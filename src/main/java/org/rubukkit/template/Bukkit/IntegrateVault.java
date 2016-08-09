package org.rubukkit.template.Bukkit;

import java.util.logging.Level;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.rubukkit.template.BukkitPluginMain;

public class IntegrateVault
{
	private final BukkitPluginMain plugin;
	private Chat       chat;
	private Economy    economy;
	private Permission permission;
	public IntegrateVault(BukkitPluginMain plugin)
	{
		this.plugin = plugin;
	}
	public void onDisable()
	{
		this.chat = null;
		this.economy = null;
		this.permission = null;
	}
	/**
	 * Если Vault установлен на сервере и включён, а также имеется какой-нибудь плагин,
	 * предоставляющий нужный интерфейс, он будет возвращён, иначе будет возвращено null.
	 * @return net.milkbowl.vault.chat.Chat
	 */
	public Chat getVaultChat()
	{
		if(chat == null)
		{
			if(plugin.getServer().getPluginManager().isPluginEnabled("Vault"))
			{
				plugin.logger.info("Found Vault! Searching for chat plugin...");
				final ServicesManager servicesManager = plugin.getServer().getServicesManager();
				RegisteredServiceProvider<Chat> provider = servicesManager.getRegistration(Chat.class);
				if(provider != null)
					chat = provider.getProvider();
				if(chat != null && chat.isEnabled())
					plugin.logger.log(Level.INFO, "Using {0} as chat provider.", chat.getName());
			}
		}
		return chat;
	}
	/**
	 * Если Vault установлен на сервере и включён, а также имеется какой-нибудь плагин,
	 * предоставляющий нужный интерфейс, он будет возвращён, иначе будет возвращено null.
	 * @return net.milkbowl.vault.economy.Economy
	 */
	public Economy getVaultEconomy()
	{
		if(economy == null)
		{
			if(plugin.getServer().getPluginManager().isPluginEnabled("Vault"))
			{
				plugin.logger.info("Found Vault! Searching for economy plugin...");
				final ServicesManager servicesManager = plugin.getServer().getServicesManager();
				RegisteredServiceProvider<Economy> provider = servicesManager.getRegistration(Economy.class);
				if(provider != null)
					economy = provider.getProvider();
				if(economy != null && economy.isEnabled())
					plugin.logger.log(Level.INFO, "Using {0} as chat provider.", economy.getName());
			}
		}
		return economy;
	}
	/**
	 * Если Vault установлен на сервере и включён, а также имеется какой-нибудь плагин,
	 * предоставляющий нужный интерфейс, он будет возвращён, иначе будет возвращено null.
	 * @return net.milkbowl.vault.permission.Permission
	 */
	public Permission getVaultPermission()
	{
		if(permission == null)
		{
			if(plugin.getServer().getPluginManager().isPluginEnabled("Vault"))
			{
				plugin.logger.info("Found Vault! Searching for permission plugin...");
				final ServicesManager servicesManager = plugin.getServer().getServicesManager();
				RegisteredServiceProvider<Permission> provider = servicesManager.getRegistration(Permission.class);
				if(provider != null)
					permission = provider.getProvider();
				if(permission != null && permission.isEnabled())
					plugin.logger.log(Level.INFO, "Using {0} as chat provider.", permission.getName());
			}
		}
		return permission;
	}
}
