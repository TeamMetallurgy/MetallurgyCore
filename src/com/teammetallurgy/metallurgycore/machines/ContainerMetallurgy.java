package com.teammetallurgy.metallurgycore.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class ContainerMetallurgy extends Container
{

    protected void addPlayersInventoryToContainer(InventoryPlayer inventoryPlayer, int xStart, int yStart)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, xStart + j * 18, yStart + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(getSlot(inventoryPlayer, xStart + i * 18, yStart + 58, i));
        }
    }

    protected Slot getSlot(InventoryPlayer inventoryPlayer, int x, int y, int id)
    {
        return new Slot(inventoryPlayer, id, x, y);
    }

    protected void readInventory(NBTTagCompound tagCompound, IInventory inventory)
    {
        if (tagCompound == null) { return; }

        NBTTagList nbttaglist = tagCompound.getTagList("items");

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < inventory.getSizeInventory())
            {
                inventory.setInventorySlotContents(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
    }

    protected void saveInventoryToStack(ItemStack stack, IInventory inventory)
    {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null)
        {
            compound = new NBTTagCompound();
        }

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            if (inventory.getStackInSlot(i) != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                inventory.getStackInSlot(i).writeToNBT(tagCompound);
                nbttaglist.appendTag(tagCompound);
            }
        }

        compound.setTag("items", nbttaglist);

        stack.setTagCompound(compound);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        return null;
    }

}
