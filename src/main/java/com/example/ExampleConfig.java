package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("tickimprover")
public interface ExampleConfig extends Config
{
	@ConfigItem(
			keyName = "pingEnabled",
			name = "Enable Ping",
			description = "Enable or disable the ping feature"
	)
	default boolean pingEnabled()
	{
		return true; // Default to disabled
	}
}
