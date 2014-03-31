package com.teammetallurgy.metallurgycore;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs
{
    private Item item = Items.apple;

	public CreativeTab(String label)
    {
        super(label);
    }
	
	public CreativeTab(String label, Item item)
	{
		this(label);
		setItem(item);
	}

    @Override
    public Item getTabIconItem()
    {
        return this.item ;
    }

    public void setItem(Item item)
    {
		this.item = item;
	}

	public void setItem(Block block)
	{
		setItem(Item.getItemFromBlock(block));
	}
}
