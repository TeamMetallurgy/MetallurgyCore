package com.teammetallurgy.metallurgycore.machines;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public abstract class TileEntityMetallurgy extends TileEntity implements IInventory
{

    protected static final int MAXCOOKTIME = 200;

    protected ItemStack[] itemStacks;
    public int burnTime;
    public int currentItemBurnTime;
    public int cookTime;

    private Random random = new Random();

    protected boolean canAcceptStackRange(int start, int end, ItemStack itemstack)
    {
        Boolean retVal = false;

        for (int i = start; i <= end; i++)
        {
            boolean itemEqual = this.itemStacks[i].isItemEqual(itemstack);

            if (itemEqual)
            {
                int stackSize = this.itemStacks[i].stackSize + itemstack.stackSize;

                retVal |= stackSize <= this.getInventoryStackLimit() && stackSize <= itemstack.getMaxStackSize();
            }
            else
            {
                retVal |= false;
            }
        }

        return retVal;
    }

    protected abstract boolean canProcessItem();

    @Override
    public void closeChest()
    {
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if (this.itemStacks[i] != null)
        {
            ItemStack itemstack;

            if (this.itemStacks[i].stackSize <= j)
            {
                itemstack = this.itemStacks[i];
                this.itemStacks[i] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.itemStacks[i].splitStack(j);

                if (this.itemStacks[i].stackSize == 0)
                {
                    this.itemStacks[i] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public void dropContents()
    {

        World world = this.worldObj;
        for (ItemStack stack : this.itemStacks)
        {
            if (stack != null)
            {
                float f = this.random.nextFloat() * 0.8F + 0.1F;
                float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                EntityItem entityitem;

                for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; stack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                {
                    int k1 = this.random.nextInt(21) + 10;

                    if (k1 > stack.stackSize)
                    {
                        k1 = stack.stackSize;
                    }

                    stack.stackSize -= k1;
                    entityitem = new EntityItem(world, this.xCoord + f, this.yCoord + f1, this.zCoord + f2, new ItemStack(stack.itemID, k1, stack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float) this.random.nextGaussian() * f3;
                    entityitem.motionY = (float) this.random.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) this.random.nextGaussian() * f3;

                    if (stack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                    }
                }
            }
        }

    }

    public int getBurnTimeRemainingScaled(int i)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = TileEntityMetallurgy.MAXCOOKTIME;
        }

        return this.burnTime * i / this.currentItemBurnTime;
    }

    public int getCookProgressScaled(int i)
    {
        return this.cookTime * i / TileEntityMetallurgy.MAXCOOKTIME;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeCustomNBT(compound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, compound);
    }

    @Override
    public abstract int getInventoryStackLimit();

    @Override
    public abstract String getInvName();

    @Override
    public int getSizeInventory()
    {
        return this.itemStacks.length;
    }

    abstract protected ItemStack getSmeltingResult(ItemStack... itemStack);

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return this.itemStacks[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        if (this.itemStacks[i] != null)
        {
            ItemStack itemstack = this.itemStacks[i];
            this.itemStacks[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public abstract boolean isItemValidForSlot(int i, ItemStack itemstack);

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
                : entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        this.readCustomNBT(pkt.data);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void openChest()
    {
    }

    protected abstract void processItem();

    protected void readCustomNBT(NBTTagCompound data)
    {
        this.itemStacks = new ItemStack[this.getSizeInventory()];
        this.readItemListFromNBT(data, "Items", this.itemStacks);

        this.burnTime = data.getShort("BurnTime");
        this.cookTime = data.getShort("CookTime");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]);

    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.readCustomNBT(compound);
    }

    protected void readItemListFromNBT(NBTTagCompound data, String name, ItemStack[] stacks)
    {
        NBTTagList nbttaglist = data.getTagList(name);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.itemStacks.length)
            {
                stacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        this.itemStacks[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
    }

    protected boolean slotsAreEmtpty(int start, int end)
    {
        Boolean retVal = false;
        for (int i = start; i <= end; i++)
        {
            retVal |= this.itemStacks[i] == null;
        }

        return retVal;
    }

    @Override
    public void updateEntity()
    {
        boolean burning = this.burnTime > 0;
        boolean changed = false;

        if (this.burnTime > 0)
        {
            --this.burnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.burnTime == 0 && this.canProcessItem())
            {
                this.currentItemBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]);

                if (this.burnTime > 0)
                {
                    changed = true;

                    if (this.itemStacks[1] != null)
                    {
                        --this.itemStacks[1].stackSize;

                        if (this.itemStacks[1].stackSize == 0)
                        {
                            this.itemStacks[1] = this.itemStacks[1].getItem().getContainerItemStack(this.itemStacks[1]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canProcessItem())
            {
                ++this.cookTime;

                if (this.cookTime == TileEntityMetallurgy.MAXCOOKTIME)
                {
                    this.cookTime = 0;
                    this.processItem();
                    changed = true;
                }
            }
            else
            {
                this.cookTime = 0;
            }

            if (burning != this.burnTime > 0)
            {
                changed = true;
            }
        }

        if (changed)
        {
            this.onInventoryChanged();
        }
    }

    protected void writeCustomNBT(NBTTagCompound compound)
    {
        this.writeItemListToNBT(compound, this.itemStacks, "Items");
        compound.setShort("BurnTime", (short) this.burnTime);
        compound.setShort("CookTime", (short) this.cookTime);

    }

    protected void writeItemListToNBT(NBTTagCompound compound, ItemStack[] stacks, String name)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < stacks.length; ++i)
        {
            if (stacks[i] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                stacks[i].writeToNBT(tagCompound);
                nbttaglist.appendTag(tagCompound);
            }
        }

        compound.setTag(name, nbttaglist);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        this.writeCustomNBT(compound);
    }
}
