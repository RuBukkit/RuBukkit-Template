package org.rubukkit.template.Bukkit;

import org.bukkit.event.Listener;
import org.rubukkit.template.BukkitPluginMain;

public class BukkitListener implements Listener
{
	private final BukkitPluginMain plugin;
	public BukkitListener(BukkitPluginMain plugin)
	{
		this.plugin = plugin;
	}
	public void onEnable()
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	// TO DO HERE
}
