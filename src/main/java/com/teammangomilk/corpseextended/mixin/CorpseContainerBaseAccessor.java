package com.teammangomilk.corpseextended.mixin;

import de.maxhenkel.corpse.gui.CorpseContainerBase;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CorpseContainerBase.class, remap = false)
public interface CorpseContainerBaseAccessor
{
    @Accessor("playerInventory")
    Inventory getPlayerInventory();
}
