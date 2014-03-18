package com.teammetallurgy.metallurgycore.machines;

import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntityMetallurgySided extends TileEntityMetallurgy implements ISidedInventory
{

    private static int[] slots_top;
    private static int[] slots_bottom;
    private static int[] slots_sides;

    public TileEntityMetallurgySided(int numberOfItemStacks, int[] slotsTop, int[] slotsSide, int[] slotsBottom)
    {
        this.itemStacks = new ItemStack[numberOfItemStacks];
        TileEntityMetallurgySided.slots_top = slotsTop;
        TileEntityMetallurgySided.slots_bottom = slotsBottom;
        TileEntityMetallurgySided.slots_sides = slotsSide;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return i != 1 || itemstack.equals(Items.bucket);
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return this.isItemValidForSlot(i, itemstack);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return side == 0 ? TileEntityMetallurgySided.slots_bottom : side == 1 ? TileEntityMetallurgySided.slots_top : TileEntityMetallurgySided.slots_sides;
    }

    @Override
    protected void readCustomNBT(NBTTagCompound data)
    {
        super.readCustomNBT(data);
    }

    @Override
    protected void writeCustomNBT(NBTTagCompound compound)
    {
        super.writeCustomNBT(compound);
    }
}
