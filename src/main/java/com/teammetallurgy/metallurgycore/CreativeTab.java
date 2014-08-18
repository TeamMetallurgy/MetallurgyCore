package com.teammetallurgy.metallurgycore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs
{
    private Item item = Items.apple;
    private ItemStack itemstack;

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
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        if (this.itemstack == null)
        {
            this.itemstack = new ItemStack(this.getTabIconItem(), 1, this.func_151243_f());
        }

        return this.itemstack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return this.item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public void setItem(Block block)
    {
        setItem(Item.getItemFromBlock(block));
    }

    public void setItemStack(ItemStack itemStack)
    {
        this.itemstack = itemStack;
    }
}
