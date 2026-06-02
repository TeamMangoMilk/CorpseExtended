package com.teammangomilk.corpseextended.net;

import com.teammangomilk.corpseextended.config.CorpseExtendedClientConfig;
import com.teammangomilk.corpseextended.config.CorpseExtendedClientConfig.MessageOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@OnlyIn(Dist.CLIENT)
public class ClientMessageHandler
{
    public static void handleXpReturned(MessageXpReturned msg, IPayloadContext ctx)
    {
        ctx.enqueueWork(() ->
        {
            MessageOverride override = CorpseExtendedClientConfig.XP_MESSAGE_OVERRIDE.get();

            String mode = override == MessageOverride.SERVER
                    ? msg.serverMode()
                    : override.name();

            if (mode.equals("OFF")) return;

            Component text = Component.translatable("message.corpse_extended.xp_returned", msg.xp());
            var player = Minecraft.getInstance().player;
            if (player == null) return;

            if (mode.equals("CHAT"))
            {
                player.sendSystemMessage(text);
            }
            else
            {
                player.displayClientMessage(text, true);
            }
        });
    }
}
