package com.teammangomilk.corpseextended;

import com.teammangomilk.corpseextended.config.CorpseExtendedClientConfig;
import com.teammangomilk.corpseextended.config.CorpseExtendedConfig;
import com.teammangomilk.corpseextended.events.CorpseEvents;
import com.teammangomilk.corpseextended.net.ClientMessageHandler;
import com.teammangomilk.corpseextended.net.MessageXpReturned;
import com.teammangomilk.corpseextended.xp.XpAttachment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CorpseExtended.MOD_ID)
public class CorpseExtended
{
    public static final String MOD_ID = "corpse_extended";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public CorpseExtended(IEventBus modEventBus, ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.SERVER, CorpseExtendedConfig.SPEC);

        if (FMLEnvironment.dist.isClient())
        {
            modContainer.registerConfig(ModConfig.Type.CLIENT, CorpseExtendedClientConfig.SPEC);
        }

        XpAttachment.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onRegisterPayloads);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        NeoForge.EVENT_BUS.register(new CorpseEvents());
    }

    private void onRegisterPayloads(RegisterPayloadHandlersEvent event)
    {
        PayloadRegistrar registrar = event.registrar(MOD_ID).versioned("1");

        if (FMLEnvironment.dist.isClient())
        {
            registrar.playToClient(
                    MessageXpReturned.TYPE,
                    MessageXpReturned.STREAM_CODEC,
                    ClientMessageHandler::handleXpReturned
            );
        }
        else
        {
            registrar.playToClient(
                    MessageXpReturned.TYPE,
                    MessageXpReturned.STREAM_CODEC,
                    (msg, ctx) -> {}
            );
        }
    }
}
