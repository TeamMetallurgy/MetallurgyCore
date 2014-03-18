package com.teammetallurgy.metallurgycore.machines;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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

    protected boolean canAcceptStackRange(int[] range, ItemStack itemstack)
    {
        Boolean retVal = false;

        for (int i : range)
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

    @Override
    public void closeInventory()
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
                    entityitem = new EntityItem(world, this.xCoord + f, this.yCoord + f1, this.zCoord + f2, new ItemStack(stack.getItem(), k1, stack.getItemDamage()));
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
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, compound);
    }

    @Override
    public abstract int getInventoryStackLimit();

    @Override
    public abstract String getInventoryName();

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
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i >= getOutputSlots()[0] ? false : i == getFuelSlot() ? TileEntityFurnace.isItemFuel(itemstack) : true;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readCustomNBT(pkt.func_148857_g());
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
    
    @Override
    public void openInventory()
    {
    }

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
        NBTTagList nbttaglist = data.getTagList(name, 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
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

    protected boolean slotsAreEmpty(int[] range)
    {
        Boolean retVal = false;

        for (int i : range)
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
                ItemStack fuelItemStack = getStackInSlot(getFuelSlot());
                this.currentItemBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(fuelItemStack);

                if (this.burnTime > 0)
                {
                    changed = true;

                    if (fuelItemStack != null)
                    {
                        --fuelItemStack.stackSize;

                        if (fuelItemStack.stackSize == 0)
                        {
                            setInventorySlotContents(getFuelSlot(), fuelItemStack.getItem().getContainerItem(fuelItemStack));
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
            this.markDirty();
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

    protected abstract int[] getInputSlots();

    protected abstract int[] getOutputSlots();

    protected boolean useMaterialInSlots(int[] slots)
    {
        boolean flag = false;
        for (int slot : slots)
        {
            ItemStack stack = getStackInSlot(slot);
            if (stack != null)
            {
                --stack.stackSize;

                if (stack.stackSize <= 0)
                {
                    setInventorySlotContents(slot, null);
                }
                flag |= true;
            }
            else
            {
                continue;
            }
        }
        return flag;
    }

    protected void outputItem(ItemStack itemstack)
    {
        for (int slot : getOutputSlots())
        {
            ItemStack stackInSlot = getStackInSlot(slot);
            if (stackInSlot == null)
            {
                setInventorySlotContents(slot, itemstack.copy());
                return;
            }
            else if (stackInSlot.isItemEqual(itemstack))
            {
                stackInSlot.stackSize += itemstack.stackSize;
                return;
            }
        }
    }

    protected abstract int getFuelSlot();

    protected ItemStack[] getStacksInSlots(int[] inputSlots)
    {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();

        for (int slot : inputSlots)
        {
            stacks.add(getStackInSlot(slot));
        }
        return stacks.toArray(new ItemStack[] {});
    }

    protected ItemStack getSmeltingResultFromSlots(int[] slots)
    {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        for (int slot : slots)
        {
            stacks.add(getStackInSlot(slot));
        }

        return getSmeltingResult(stacks.toArray(new ItemStack[] {}));
    }

    protected void processItem()
    {
        if (this.canProcessItem())
        {
            ItemStack[] inputStack = getStacksInSlots(getInputSlots());
            ItemStack outputStack = getSmeltingResult(inputStack);

            outputItem(outputStack);
            useMaterialInSlots(getInputSlots());
        }
    }

    protected boolean hasMaterialAndRoom(ItemStack... itemStacks)
    {
        ItemStack itemstack = getSmeltingResultFromSlots(getInputSlots());

        if (itemStacks.length != 0)
        {
            itemstack = getSmeltingResult(itemStacks);
        }

        if (itemstack == null) { return false; }
        if (slotsAreEmpty(getOutputSlots())) { return true; }
        return canAcceptStackRange(getOutputSlots(), itemstack);
    }

    protected boolean canProcessItem()
    {
        if (!hasInput())
        {
            return false;
        }
        else
        {
            return hasMaterialAndRoom();
        }
    }

    protected boolean hasInput()
    {
        for (int slot : getInputSlots())
        {
            if (getStackInSlot(slot) == null) { return false; }
        }

        return true;
    }
}
