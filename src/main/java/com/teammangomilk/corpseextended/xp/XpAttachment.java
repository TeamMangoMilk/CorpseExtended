package com.teammangomilk.corpseextended.xp;

import com.mojang.serialization.Codec;
import com.teammangomilk.corpseextended.CorpseExtended;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class XpAttachment
{
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, CorpseExtended.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> STORED_XP = ATTACHMENTS.register("stored_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Float>> STORED_SCALE = ATTACHMENTS.register("stored_scale", () -> AttachmentType.builder(() -> 1.0f).serialize(Codec.FLOAT).build());

    public static void register(IEventBus modEventBus)
    {
        ATTACHMENTS.register(modEventBus);
    }
}
