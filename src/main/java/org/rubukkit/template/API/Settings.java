package org.rubukkit.template.API;

import java.io.IOException;

public interface Settings
{
	public static final String PLUGIN_SHORT_NAME = "rbtmplt";
	public static final String CHAT_PREFIX       = "§2[rbtmplt] §a";
	public static final int    CONFIG_VERSION    = 3;

	// СЮДА НЕПЛОХО ЛОЖАТСЯ ВСЯКИЕ КОНСТАНТЫ

	public abstract void onEnable() throws IOException;
}
