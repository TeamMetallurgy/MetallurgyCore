package com.teammetallurgy.metallurgycore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs
{

    public CreativeTab(String lable)
    {
        super(lable);
    }

    @Override
    public Item getTabIconItem()
    {
        return Items.apple;
    }

}
