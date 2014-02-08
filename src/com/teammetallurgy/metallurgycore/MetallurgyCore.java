package com.teammetallurgy.metallurgycore;

import com.teammetallurgy.metallurgycore.handlers.PacketHandler;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(name = MetallurgyCore.MODNAME, modid = MetallurgyCore.MODID)
@NetworkMod(channels = { MetallurgyCore.MODID }, packetHandler = PacketHandler.class)
public class MetallurgyCore
{
    public static final String MODID = "MetallurgyCore";
    public static final String MODNAME = "Metallurgy Core";

    @Mod.Instance(MODID)
    public static MetallurgyCore instance;

    public CreativeTabs creativeTabItems = new CreativeTabs(MODID + ".Items");

}
