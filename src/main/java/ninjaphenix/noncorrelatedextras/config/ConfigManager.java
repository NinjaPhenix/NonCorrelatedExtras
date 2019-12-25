package ninjaphenix.noncorrelatedextras.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

public class ConfigManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Jankson jankson = new Jankson.Builder().build();

    @SuppressWarnings("ConstantConditions")
    static <T> T loadConfig(Class<T> clazz, File configFile, Supplier<T> fallbackConfigConstructor)
    {
        try
        {
            if (!configFile.exists())
            {
                T instance = clazz.newInstance();
                saveConfig(instance, configFile);
                return instance;
            }
            try
            {
                JsonObject json = jankson.load(configFile);
                T result = jankson.fromJson(json, clazz);
                JsonElement jsonElementNew = jankson.toJson(clazz.newInstance());
                if (jsonElementNew instanceof JsonObject)
                {
                    JsonObject jsonNew = (JsonObject) jsonElementNew;
                    if (json.getDelta(jsonNew).size() >= 0) saveConfig(result, configFile);
                }
                return result;
            }
            catch (IOException e) { LOGGER.warn("Failed to load config File {}: {}", configFile.getName(), e); }
        }
        catch (SyntaxError syntaxError) { LOGGER.warn("Failed to load config File {}: {}", configFile.getName(), syntaxError); }
        catch (IllegalAccessException | InstantiationException e) { LOGGER.warn("Failed to create new config file for {}: {}", configFile.getName(), e); }
        LOGGER.warn("Creating placeholder config for {}...", configFile.getName());
        return fallbackConfigConstructor.get();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void saveConfig(Object object, File configFile)
    {
        JsonElement json = jankson.toJson(object);
        String result = json.toJson(true, true);
        try
        {
            if (!configFile.exists()) configFile.createNewFile();
            FileOutputStream out = new FileOutputStream(configFile, false);
            out.write(result.getBytes());
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            LOGGER.warn("Failed to write to config file {}: {}", configFile.getName(), e);
        }
    }
}
