package com.teammangomilk.corpseextended.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CorpseExtendedConfig
{
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue XP_SAVING_ENABLED;
    public static final ModConfigSpec.IntValue XP_RETURN_PERCENTAGE;
    public static final ModConfigSpec.EnumValue<MessageMode> XP_MESSAGE_MODE;

    public enum MessageMode { OFF, CHAT, ACTION_BAR }

    static
    {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("XP saving").push("xp_saving");
        XP_SAVING_ENABLED = builder
                .comment("Store the player's XP in their corpse and return it when they open it.")
                .define("enabled", true);
        XP_RETURN_PERCENTAGE = builder
                .comment("Percentage of stored XP returned to the player (0 = none, 100 = all).")
                .defineInRange("return_percentage", 100, 0, 100);
        builder.pop();

        builder.comment("XP return message").push("xp_message");
        XP_MESSAGE_MODE = builder
                .comment("How the XP return message is shown server-wide. OFF, CHAT, or ACTION_BAR. Players can override this client-side.")
                .defineEnum("mode", MessageMode.ACTION_BAR);
        builder.pop();

        SPEC = builder.build();
    }
}
