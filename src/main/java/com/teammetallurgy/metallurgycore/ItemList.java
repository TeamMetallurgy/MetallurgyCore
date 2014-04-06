package com.teammetallurgy.metallurgycore;

import net.minecraft.item.Item;

import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;
import com.teammetallurgy.metallurgycore.utils.ItemOreFinder;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemList
{
    public static Item oreFinder;

    public static void init()
    {
        String itemName = "metallurgyCore.oreFinder";
        int defaultId = 2560;

        if (ConfigHandler.itemEnabled(itemName))
        {
            int id = ConfigHandler.getItem(itemName, defaultId++);

            ItemList.oreFinder = new ItemOreFinder(id).setUnlocalizedName(itemName);

            ItemList.registerItem(ItemList.oreFinder, itemName);
        }

    }

    public static void registerItem(Item item, String itemName)
    {
        GameRegistry.registerItem(item, itemName);
    }
}
