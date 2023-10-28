package com.tickimprover;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
		name = "Tick Improver"
)
public class TickImprover extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private TickImproverConfig config;

	private ScheduledExecutorService executorService;


	private int pingInterval = 100;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Tick Improver started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		stopPinging();
		log.info("Tick Improver stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			startPinging();
		}
	}


	@Provides
	TickImproverConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TickImproverConfig.class);
	}

	public void startPinging()
	{
		if (executorService == null || executorService.isShutdown())
		{
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(() -> {
				try
				{
					InetAddress address = InetAddress.getByName(config.gateway());
					boolean reachable = address.isReachable(100);
				}
				catch (IOException e)
				{
					log.error("Ping error to " + config.gateway() + ": " + e.getMessage(), e);
				}
			}, 0, pingInterval, TimeUnit.MILLISECONDS);
		}
	}
	public void stopPinging()
	{
		if (executorService != null && !executorService.isShutdown())
		{
			executorService.shutdown();
			executorService = null;
		}
	}
}
