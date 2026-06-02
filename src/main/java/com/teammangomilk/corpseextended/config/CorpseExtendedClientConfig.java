package com.teammangomilk.corpseextended.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CorpseExtendedClientConfig
{
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.EnumValue<MessageOverride> XP_MESSAGE_OVERRIDE;

    public enum MessageOverride { SERVER, OFF, CHAT, ACTION_BAR }

    static
    {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("XP return message").push("xp_message");
        XP_MESSAGE_OVERRIDE = builder
                .comment("Override the server XP message display. SERVER = use server setting, OFF = never show, CHAT = always chat, ACTION_BAR = always action bar.")
                .defineEnum("override", MessageOverride.SERVER);
        builder.pop();

        SPEC = builder.build();
    }
}
