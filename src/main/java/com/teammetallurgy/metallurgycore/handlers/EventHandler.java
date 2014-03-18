package com.teammetallurgy.metallurgycore.handlers;

import java.util.ArrayList;

import cpw.mods.fml.common.Mod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.world.ChunkDataEvent;

public abstract class EventHandler
{

    @Mod.EventHandler
    public void chunkLoad(ChunkDataEvent.Load event)
    {
        int dim = event.world.provider.dimensionId;

        ChunkCoordIntPair loc = event.getChunk().getChunkCoordIntPair();

        if (!event.getData().getCompoundTag(this.getModTag()).hasKey(ConfigHandler.regenKey()) && ConfigHandler.regen())
        {
            LogHandler.log("Worlg gen was never run for chunk at " + loc);

            ArrayList<ChunkLoc> chunks = WorldTicker.chunksToGenerate.get(dim);

            if (chunks == null)
            {
                WorldTicker.chunksToGenerate.put(dim, new ArrayList<ChunkLoc>());
                chunks = WorldTicker.chunksToGenerate.get(dim);
            }

            if (chunks != null)
            {
                chunks.add(new ChunkLoc(loc.chunkXPos, loc.chunkZPos));
                WorldTicker.chunksToGenerate.put(dim, chunks);
            }
        }
    }

    @Mod.EventHandler
    public void chunkSave(ChunkDataEvent.Save event)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        event.getData().setTag(this.getModTag(), tagCompound);
        tagCompound.setBoolean(ConfigHandler.regenKey(), true);
    }

    public abstract String getModTag();
}
