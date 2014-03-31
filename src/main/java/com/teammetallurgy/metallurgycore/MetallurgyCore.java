package com.teammetallurgy.metallurgycore;

import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = MetallurgyCore.MODNAME, modid = MetallurgyCore.MODID)
public class MetallurgyCore
{
    public static final String MODID = "MetallurgyCore";
    public static final String MODNAME = "Metallurgy Core";

    @Mod.Instance(MetallurgyCore.MODID)
    public static MetallurgyCore instance;

    public CreativeTabs creativeTabItems = new CreativeTab(MetallurgyCore.MODID + ".Items");

    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ConfigHandler.setFile(event.getSuggestedConfigurationFile());
        ItemList.init();
    }
}
