package com.teammetallurgy.metallurgycore;

import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;
import com.teammetallurgy.metallurgycore.handlers.LogHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = MetallurgyCore.MODNAME, modid = MetallurgyCore.MODID, version = MetallurgyCore.VERSION)
public class MetallurgyCore
{
    public static final String MODID = "MetallurgyCore";
    public static final String MODNAME = "Metallurgy Core";
    public static final String VERSION = "4.0.5";

    @Instance(MetallurgyCore.MODID)
    public static MetallurgyCore instance;

    public CreativeTab creativeTabItems = new CreativeTab("metallurgyCore" + ".items");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHandler.setLog(event.getModLog());
        ConfigHandler.setFile(event.getSuggestedConfigurationFile());
        ItemList.init();

        if (ItemList.oreFinder != null)
        {
            this.creativeTabItems.setItem(ItemList.oreFinder);
        }
    }
}
