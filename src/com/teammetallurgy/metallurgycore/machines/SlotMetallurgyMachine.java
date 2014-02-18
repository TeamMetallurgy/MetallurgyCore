package com.teammetallurgy.metallurgycore.machines;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMetallurgyMachine extends Slot
{

    private int id;
    private TileEntityMetallurgy container;

    public SlotMetallurgyMachine(TileEntityMetallurgy inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
        this.id = id;
        this.container = inventory;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return this.container.isItemValidForSlot(this.id, stack);
    }

}
