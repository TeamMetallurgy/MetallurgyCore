package com.teammetallurgy.metallurgycore.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{

    private static Configuration configuration;

    public static boolean generates(String name)
    {
        boolean b = ConfigHandler.configuration.get("Generators", name, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return b;
    }

    @Deprecated
    public static int getBlock(String blockName, int defaultid)
    {
        return defaultid;
    }

    @Deprecated
    public static int getBlock(String category, String blockName, int defaultid)
    {
        return defaultid;
    }

    private static boolean getBoolean(String categories, String key, boolean defaultValue)
    {
        boolean b = ConfigHandler.configuration.get(categories, key, defaultValue).getBoolean(defaultValue);

        ConfigHandler.saveChanges();

        return b;
    }

    @Deprecated
    public static int getItem(String itemName, int defaultid)
    {
        return defaultid;
    }

    @Deprecated
    public static int getItem(String category, String itemName, Integer defaultid)
    {
        return defaultid;
    }

    private static String getName(String categories, String key, String defaultValue)
    {
        String string = ConfigHandler.configuration.get(categories, key, defaultValue).toString();

        ConfigHandler.saveChanges();

        return string;
    }

    public static boolean itemEnabled(String itemName)
    {
        boolean b = ConfigHandler.configuration.get("Items", itemName, true).getBoolean(true);

        ConfigHandler.saveChanges();
        return b;
    }

    public static boolean regen()
    {
        return ConfigHandler.getBoolean("World_Regen", "regenOres", false);
    }

    public static String regenKey()
    {
        return ConfigHandler.getName("World_Regen", "regen_key", "DEFAULT");
    }

    private static void saveChanges()
    {

        if (ConfigHandler.configuration.hasChanged())
        {
            ConfigHandler.configuration.save();
        }
    }

    public static boolean setEnabled(String setName)
    {
        boolean b = ConfigHandler.configuration.get("Sets", setName, true).getBoolean(true);

        ConfigHandler.saveChanges();

        return b;
    }

    public static void setFile(File file)
    {
        ConfigHandler.configuration = new Configuration(file);

        ConfigHandler.configuration.load();

        ConfigHandler.saveChanges();
    }

}
