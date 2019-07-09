package ninjaphenix.noncorrelatedextras;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.Logger;

public class Client implements ClientModInitializer
{
    private final Logger LOGGER = Common.LOGGER;

    @Override
    public void onInitializeClient()
    {
        LOGGER.info("Client has been initialized.");
    }
}
