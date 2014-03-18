package com.teammetallurgy.metallurgycore.machines;


public abstract class GUIMetallurgyMachine extends GUIMetallurgy
{
    protected TileEntityMetallurgy tileEntity;

    public GUIMetallurgyMachine(ContainerMetallurgyMachine container, String texture)
    {
        super(container, texture);
        this.tileEntity = container.tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        super.drawGuiContainerBackgroundLayer(f, i, j);

        int i1;
        if (this.tileEntity.isBurning())
        {
            i1 = this.tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 27 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.tileEntity.getCookProgressScaled(21);
        this.drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 33, 176, 14, 12, i1);
    }

    @Override
    protected String getInvName()
    {
        // TODO Auto-generated method stub
        return this.tileEntity.getInventoryName();
    }
}
