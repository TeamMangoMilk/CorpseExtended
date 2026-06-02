package com.teammangomilk.corpseextended.net;

import com.teammangomilk.corpseextended.CorpseExtended;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MessageCorpseScale(int entityId, float scale) implements CustomPacketPayload
{
    public static final Type<MessageCorpseScale> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(CorpseExtended.MOD_ID, "corpse_scale")
    );

    public static final StreamCodec<FriendlyByteBuf, MessageCorpseScale> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MessageCorpseScale::entityId,
            ByteBufCodecs.FLOAT, MessageCorpseScale::scale,
            MessageCorpseScale::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
