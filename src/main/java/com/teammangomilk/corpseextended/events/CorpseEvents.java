package com.teammangomilk.corpseextended.events;

import com.teammangomilk.corpseextended.CorpseExtended;
import com.teammangomilk.corpseextended.config.CorpseExtendedConfig;
import com.teammangomilk.corpseextended.net.MessageCorpseScale;
import com.teammangomilk.corpseextended.net.MessageXpReturned;
import com.teammangomilk.corpseextended.xp.XpAttachment;
import de.maxhenkel.corpse.entities.CorpseEntity;
import de.maxhenkel.corpse.gui.CorpseInventoryContainer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CorpseEvents
{
    private static final Map<UUID, Integer> pendingXp = new HashMap<>();
    private static final Map<UUID, Float> pendingScale = new HashMap<>();

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event)
    {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        UUID uuid = player.getUUID();

        if (CorpseExtendedConfig.XP_SAVING_ENABLED.get())
        {
            int xp = player.totalExperience;
            if (xp > 0)
            {
                pendingXp.put(uuid, xp);
                player.totalExperience = 0;
                player.experienceLevel = 0;
                player.experienceProgress = 0.0f;
            }
        }

        float scale = player.getScale();
        if (scale != 1.0f)
        {
            pendingScale.put(uuid, scale);
        }
    }

    @SubscribeEvent
    public void onEntityJoinLevel(EntityJoinLevelEvent event)
    {
        if (!(event.getEntity() instanceof CorpseEntity corpse)) return;
        if (event.getLevel().isClientSide()) return;

        UUID ownerUuid = corpse.getDeath().getPlayerUUID();

        Integer xp = pendingXp.remove(ownerUuid);
        if (xp != null && xp > 0)
        {
            corpse.setData(XpAttachment.STORED_XP, xp);
        }

        Float scale = pendingScale.remove(ownerUuid);
        float resolvedScale = (scale != null) ? scale : 1.0f;
        corpse.setData(XpAttachment.STORED_SCALE, resolvedScale);

        PacketDistributor.sendToPlayersTrackingEntity(corpse, new MessageCorpseScale(corpse.getId(), resolvedScale));
    }

    @SubscribeEvent
    public void onContainerOpen(PlayerContainerEvent.Open event)
    {
        if (!CorpseExtendedConfig.XP_SAVING_ENABLED.get()) return;
        if (!(event.getContainer() instanceof CorpseInventoryContainer container)) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        CorpseEntity corpse = container.getCorpse();
        if (corpse == null) return;

        int storedXp = corpse.getData(XpAttachment.STORED_XP);
        if (storedXp <= 0) return;

        int returnPct = CorpseExtendedConfig.XP_RETURN_PERCENTAGE.get();
        int xpToReturn = storedXp * returnPct / 100;

        corpse.setData(XpAttachment.STORED_XP, 0);

        if (xpToReturn > 0)
        {
            player.giveExperiencePoints(xpToReturn);

            String serverMode = CorpseExtendedConfig.XP_MESSAGE_MODE.get().name();
            if (!serverMode.equals("OFF"))
            {
                PacketDistributor.sendToPlayer(player, new MessageXpReturned(xpToReturn, serverMode));
            }

            CorpseExtended.LOGGER.debug("Returned {} XP ({}%) to player {}", xpToReturn, returnPct, player.getName().getString());
        }
    }
}
