package com.teammangomilk.corpseextended.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientScaleCache
{
    private static final Map<Integer, Float> scales = new HashMap<>();

    public static void put(int entityId, float scale)
    {
        scales.put(entityId, scale);
    }

    public static float get(int entityId)
    {
        return scales.getOrDefault(entityId, 1.0f);
    }

    public static void remove(int entityId)
    {
        scales.remove(entityId);
    }
}
