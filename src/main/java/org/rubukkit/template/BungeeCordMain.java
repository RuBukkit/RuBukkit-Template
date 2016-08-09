package org.rubukkit.template;

import net.md_5.bungee.api.plugin.Plugin;
import org.rubukkit.template.API.Settings;

public class BungeeCordMain extends Plugin
{
	@Override
	public void onEnable()
	{
		this.getLogger().info("[" + Settings.PLUGIN_SHORT_NAME + "] Enabled!");
	}
	@Override
	public void onDisable()
	{
		this.getLogger().info("[" + Settings.PLUGIN_SHORT_NAME + "] Disabled!");
	}
}
