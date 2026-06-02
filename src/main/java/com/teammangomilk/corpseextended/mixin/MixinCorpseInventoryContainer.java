package com.teammangomilk.corpseextended.mixin;

import de.maxhenkel.corpse.entities.CorpseEntity;
import de.maxhenkel.corpse.gui.CorpseInventoryContainer;
import de.maxhenkel.corpse.gui.Guis;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = CorpseInventoryContainer.class, remap = false)
public abstract class MixinCorpseInventoryContainer
{
    @Inject(method = "transferItems", at = @At("HEAD"), cancellable = true)
    private void onTransferItems(CallbackInfo ci)
    {
        CorpseInventoryContainer container = (CorpseInventoryContainer) (Object) this;
        if (!container.isEditable()) return;

        CorpseEntity corpse = container.getCorpse();
        if (corpse == null) return;

        ServerPlayer player = (ServerPlayer) ((CorpseContainerBaseAccessor) (Object) this).getPlayerInventory().player;

        // Owner gets the original slot-specific transfer behaviour.
        if (player.getUUID().equals(corpse.getDeath().getPlayerUUID())) return;

        var death = corpse.getDeath();

        List<ItemStack> allItems = new ArrayList<>();
        drainList(death.getMainInventory(), allItems);
        drainList(death.getArmorInventory(), allItems);
        drainList(death.getOffHandInventory(), allItems);
        allItems.addAll(death.getAdditionalItems());
        death.getAdditionalItems().clear();

        NonNullList<ItemStack> leftover = NonNullList.create();
        for (ItemStack stack : allItems)
        {
            if (!player.getInventory().add(stack))
            {
                leftover.add(stack);
            }
        }

        death.getAdditionalItems().addAll(leftover);

        if (!death.getAdditionalItems().isEmpty())
        {
            Guis.openAdditionalItems(player, container);
        }

        ci.cancel();
    }

    private static void drainList(NonNullList<ItemStack> src, List<ItemStack> target)
    {
        for (int i = 0; i < src.size(); i++)
        {
            ItemStack stack = src.get(i);
            if (!stack.isEmpty())
            {
                target.add(stack.copy());
                src.set(i, ItemStack.EMPTY);
            }
        }
    }
}
