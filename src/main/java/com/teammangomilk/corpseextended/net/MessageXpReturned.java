package com.teammangomilk.corpseextended.net;

import com.teammangomilk.corpseextended.CorpseExtended;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MessageXpReturned(int xp, String serverMode) implements CustomPacketPayload
{
    public static final Type<MessageXpReturned> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(CorpseExtended.MOD_ID, "xp_returned")
    );

    public static final StreamCodec<FriendlyByteBuf, MessageXpReturned> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MessageXpReturned::xp,
            ByteBufCodecs.STRING_UTF8, MessageXpReturned::serverMode,
            MessageXpReturned::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
