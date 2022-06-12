package com.pibbium.alwaysdurable.mixin;

import com.pibbium.alwaysdurable.config.AlwaysDurableConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
@Environment(EnvType.CLIENT)
public class ItemStackMixin {
    @Inject(method = "getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipContext;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private void injected(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List> cir) {
        if (((ItemStack) (Object) this).isDamaged()) {
            if (!context.isAdvanced()) {
                if (AlwaysDurableConfig.showText) {
                    List list = cir.getReturnValue();
                    list.add(getDurTooltip(true));
                    cir.setReturnValue(list);
                }
            } else {
                List list = cir.getReturnValue();
                int idx = list.indexOf(getDurTooltip(false));
                list.set(idx, getDurTooltip(true));
                cir.setReturnValue(list);
            }
        }
    }

    private MutableText getDurTooltip(Boolean formatted) {
        int maxDmg = ((ItemStack) (Object) this).getMaxDamage();
        int durLeft = maxDmg - ((ItemStack) (Object) this).getDamage();
        TranslatableText text = new TranslatableText("item.durability", durLeft, maxDmg);
        if (formatted) {
            Formatting color = Formatting.byColorIndex(AlwaysDurableConfig.def.ordinal());
            if (AlwaysDurableConfig.changeTextColor) {
                int quarter = maxDmg / 4;
                //i'm so sorry
                if (durLeft < quarter * 4) color = Formatting.byColorIndex(AlwaysDurableConfig.veryhigh.ordinal());
                if (durLeft < quarter * 3) color = Formatting.byColorIndex(AlwaysDurableConfig.high.ordinal());
                if (durLeft < quarter * 2) color = Formatting.byColorIndex(AlwaysDurableConfig.medium.ordinal());
                if (durLeft < quarter) color = Formatting.byColorIndex(AlwaysDurableConfig.low.ordinal());
                if (durLeft < quarter / 2) color = Formatting.byColorIndex(AlwaysDurableConfig.verylow.ordinal());
            }
            return text.formatted(color);
        }
        return text;
    }
}