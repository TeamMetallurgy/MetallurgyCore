package com.teammetallurgy.metallurgycore.machines;

import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntityMetallurgySided extends TileEntityMetallurgy implements ISidedInventory
{

    private int[] slots_top;
    private int[] slots_bottom;
    private int[] slots_sides;

    public TileEntityMetallurgySided(int numberOfItemStacks, int[] slotsTop, int[] slotsSide, int[] slotsBottom)
    {
        this.itemStacks = new ItemStack[numberOfItemStacks];
        slots_top = slotsTop;
        slots_bottom = slotsBottom;
        slots_sides = slotsSide;
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
        switch (side)
        {
            case 0:
                return slots_bottom;
            case 1:
                return slots_top;
            default:
                return slots_sides;
        }
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
