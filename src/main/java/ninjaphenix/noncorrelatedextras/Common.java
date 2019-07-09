package ninjaphenix.noncorrelatedextras;

import net.fabricmc.api.ModInitializer;
import ninjaphenix.noncorrelatedextras.blocks.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Common implements ModInitializer
{
    public static final String MOD_ID = "noncorrelatedextras";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize()
    {
        LOGGER.info("Common has been initialized.");
        Blocks.INSTANCE.initialize();
    }
}
