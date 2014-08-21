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
    public boolean canExtractItem(int slot, ItemStack itemstack, int side)
    {
        if (itemstack.equals(Items.bucket))
        {
            return true;
        }

        int[] outputSlots = getOutputSlots();
        for (int i = 0; i < outputSlots.length; i++)
        {
            if(outputSlots[i] == slot)
            {
                return true;
            }
        }

        return false;
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
