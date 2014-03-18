package com.teammetallurgy.metallurgycore.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ChatUtils
{

    public static void sendChatToPlayer(EntityPlayer player, String string)
    {
        player.addChatMessage(new ChatComponentText(string));      
    }

}
