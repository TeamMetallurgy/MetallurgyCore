package com.teammetallurgy.metallurgycore.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public abstract class GUIMetallurgy extends GuiContainer
{

    public static void bindTexture(ResourceLocation resourceLocation)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
    }

    protected ResourceLocation texture;

    public GUIMetallurgy(Container container, String texture)
    {
        super(container);
        this.texture = new ResourceLocation(texture);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.drawTitle(8, 6);
    }

    protected void drawTitle(int x, int y)
    {
        this.fontRendererObj.drawString(I18n.format(this.getInvName()), x, y, 4210752);
    }

    protected abstract String getInvName();

}
