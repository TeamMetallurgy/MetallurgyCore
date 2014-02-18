package com.teammetallurgy.metallurgycore;

import net.minecraft.creativetab.CreativeTabs;

import com.teammetallurgy.metallurgycore.handlers.PacketHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(name = MetallurgyCore.MODNAME, modid = MetallurgyCore.MODID)
@NetworkMod(channels = { MetallurgyCore.MODID }, packetHandler = PacketHandler.class)
public class MetallurgyCore
{
    public static final String MODID = "MetallurgyCore";
    public static final String MODNAME = "Metallurgy Core";

    @Mod.Instance(MetallurgyCore.MODID)
    public static MetallurgyCore instance;

    public CreativeTabs creativeTabItems = new CreativeTabs(MetallurgyCore.MODID + ".Items");

    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ItemList.init();
    }
}
