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

        if (ConfigHandler.itemEnabled(itemName))
        {

            ItemList.oreFinder = new ItemOreFinder().setUnlocalizedName(itemName);

            ItemList.registerItem(ItemList.oreFinder, itemName);
        }

    }

    public static void registerItem(Item item, String itemName)
    {
        GameRegistry.registerItem(item, itemName);
    }
}
