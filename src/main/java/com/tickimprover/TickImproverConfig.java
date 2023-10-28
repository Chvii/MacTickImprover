package com.tickimprover;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("tick improver")
public interface TickImproverConfig extends Config
{
	@ConfigItem(
			keyName = "gateway",
			name = "Set Gateway",
			description = "Set the gateway currently being used (most commonly 192.168.1.1"
	)
	default String gateway(){
		return "192.168.1.1";
	}
}
